package com.yapp.picon.presentation.model

import com.naver.maps.geometry.LatLng
import ted.gun0912.clustering.clustering.TedClusterItem
import ted.gun0912.clustering.geometry.TedLatLng


data class PostMarker(
    val id: Int?,
    val coordinate: Coordinate,
    val imageUrls: List<String>?,
    val address: Address,
    val emotion: Emotion?,
    val memo: String?,
    var position: LatLng = LatLng(coordinate.lat.toDouble(), coordinate.lng.toDouble())
) : TedClusterItem {

    override fun getTedLatLng(): TedLatLng {
        return TedLatLng(position.latitude, position.longitude)
    }

}