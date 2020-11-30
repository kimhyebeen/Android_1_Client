package com.yapp.picon.presentation.util

import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition

object NaverCamera {
    private val startLatLng = LatLng(36.11169235719229, 127.78033862000188)
    private const val startZoom = 5.936183340920128

    val startPosition = CameraPosition(startLatLng, startZoom)
    const val defaultZoom = 14.0
    const val defaultAnimateTime = 1000L
}