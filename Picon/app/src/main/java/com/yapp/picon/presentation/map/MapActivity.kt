package com.yapp.picon.presentation.map

import androidx.activity.viewModels
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