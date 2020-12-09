package com.yapp.picon.presentation.util

import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition

object NaverCamera {
    const val DEFAULT_ZOOM = 14.0
    const val DEFAULT_ANIMATION_TIME = 1000L
    const val START_ANIMATION_TIME = 300L
    const val REFRESH_ANIMATION_TIME = 300L

    private const val START_LAT = 36.11169235719229
    private const val START_LNG = 127.78033862000188
    private const val START_ZOOM = 5.936183340920128

    val START_POSITION = CameraPosition(LatLng(START_LAT, START_LNG), START_ZOOM)

//     검은색이 아예 안보이는 지도 제한
//    val LIMIT_SOUTH_WEST = LatLng(34.6798769469451, 124.4720971184604)
//    val LIMIT_NORTH_EAST = LatLng(37.91474643488357, 131.23059791550036)

//    제주도와 독도를 포함한 지도 제한
    val LIMIT_SOUTH_WEST = LatLng(32.21823043575047, 122.70049148183602)
    val LIMIT_NORTH_EAST = LatLng(40.52297359745907, 132.78655047478333)

}