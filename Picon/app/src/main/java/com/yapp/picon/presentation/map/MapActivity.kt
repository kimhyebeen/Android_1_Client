package com.yapp.picon.presentation.map

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import com.naver.maps.map.*
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

        setToolBar()
        setOnClickListeners()
    }

    private fun setToolBar() {
        setSupportActionBar(binding.mapToolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setOnClickListeners() {
        binding.mapBtnMenu.setOnClickListener { binding.mapDrawerLayout.openDrawer(GravityCompat.START) }
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.map = naverMap
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