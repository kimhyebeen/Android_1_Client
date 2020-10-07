package com.yapp.picon

import com.naver.maps.map.*

class MainActivity : BaseMapActivity() {
    private lateinit var map: NaverMap

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
}