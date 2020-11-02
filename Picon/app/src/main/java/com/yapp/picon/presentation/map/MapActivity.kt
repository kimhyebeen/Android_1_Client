package com.yapp.picon.presentation.map

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.naver.maps.map.NaverMap
import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.databinding.MapActivityBinding
import com.yapp.picon.presentation.base.BaseMapActivity
import com.yapp.picon.presentation.nav.NavActivity
import com.yapp.picon.presentation.nav.NavTypeStringSet

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

    private fun initViewModel() {
        binding.setVariable(BR.mapVM, vm)
    }

    private fun setToolBar() {
        setSupportActionBar(binding.mapToolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setOnClickListeners() {
        binding.mapIbMenu.setOnClickListener { binding.mapDrawerLayout.openDrawer(GravityCompat.START) }
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
}