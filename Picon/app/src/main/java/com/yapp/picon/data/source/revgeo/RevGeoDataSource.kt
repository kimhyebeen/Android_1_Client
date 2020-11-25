package com.yapp.picon.data.source.revgeo

import com.yapp.picon.data.model.RevGeoResult

interface RevGeoDataSource {
    suspend fun requestRevGeo(coords: String, orders: String, output: String): RevGeoResult
}