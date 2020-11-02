package com.yapp.picon.presentation.map

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import com.naver.maps.map.NaverMap
import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.databinding.MapActivityBinding
import com.yapp.picon.presentation.base.BaseMapActivity

class MapActivity : BaseMapActivity<MapActivityBinding, MapViewModel>
    (
    R.layout.map_activity,
    R.id.mapFrame
) {

    override val vm: MapViewModel by viewModels()

    private lateinit var map: NaverMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
        setToolBar()
        setOnClickListeners()
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
            vm.requestPost()
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

    }
}