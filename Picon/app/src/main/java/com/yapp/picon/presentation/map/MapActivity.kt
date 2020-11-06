package com.yapp.picon.presentation.map

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import com.google.android.material.navigation.NavigationView
import com.naver.maps.geometry.Tm128
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.NaverMap
import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.databinding.MapActivityBinding
import com.yapp.picon.helper.RequestCodeSet
import com.yapp.picon.presentation.base.BaseMapActivity
import com.yapp.picon.presentation.nav.NavActivity
import com.yapp.picon.presentation.nav.NavTypeStringSet
import com.yapp.picon.presentation.search.SearchActivity

class MapActivity : BaseMapActivity<MapActivityBinding, MapViewModel>
    (
    R.layout.map_activity,
    R.id.map_frame
), NavigationView.OnNavigationItemSelectedListener {

    override val vm: MapViewModel by viewModels()

    private lateinit var map: NaverMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
        setToolBar()
        setOnClickListeners()

        binding.navView.setNavigationItemSelectedListener(this)
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun initViewModel() {
        binding.setVariable(BR.mapVM, vm)

        val toastMsgObserver = Observer<String> {
            showToast(it)
        }

        vm.toastMsg.observe(this, toastMsgObserver)
    }

    private fun setToolBar() {
        setSupportActionBar(binding.mapToolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setOnClickListeners() {
        binding.mapIbMenu.setOnClickListener { binding.mapDrawerLayout.openDrawer(GravityCompat.START) }

        binding.mapIbSearch.setOnClickListener {
            vm.toggleButtonShown()
            startActivityForResult(
                Intent(this, SearchActivity::class.java),
                RequestCodeSet.SEARCH_REQUEST_CODE.code
            )
        }

        binding.mapIbAdd.setOnClickListener {
            vm.createPost()
        }
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.map = naverMap

        this.map.setOnMapClickListener { _, _ ->
            vm.toggleButtonShown()
        }

        settingOptionToMap()
    }

    private fun settingOptionToMap() {
        map.apply {
            mapType = NaverMap.MapType.Navi
            isNightModeEnabled = true
            uiSettings.isZoomControlEnabled = false
            uiSettings.isZoomGesturesEnabled = true
            setLayerGroupEnabled("LAYER_GROUP_BUILDING", true)
        }
    }

    private fun startNavActivity(type: String) {
        startActivity(
            Intent(this, NavActivity::class.java)
                .putExtra("type", type)
        )
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.map_nav_customize_emotion_name -> startNavActivity(NavTypeStringSet.CustomEmotion.type)
            R.id.map_nav_setting -> startNavActivity(NavTypeStringSet.Setting.type)
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                RequestCodeSet.SEARCH_REQUEST_CODE.code -> {
                    vm.toggleButtonShown()
                    data?.let {
                        val mapX = it.getDoubleExtra("mapX", 0.0)
                        val mapY = it.getDoubleExtra("mapY", 0.0)
                        val tm128 = Tm128(mapX, mapY)
                        val latLng = tm128.toLatLng()
                        val cameraUpdate = CameraUpdate.scrollTo(latLng)
                        map.moveCamera(cameraUpdate)
                    }
                }
            }
        }
    }
}