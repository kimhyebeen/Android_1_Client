package com.yapp.picon.data.source.revgeo.remote

import com.yapp.picon.data.api.RevGeoApi
import com.yapp.picon.data.model.RevGeoResult
import com.yapp.picon.data.source.revgeo.RevGeoDataSource

class RevGeoRemoteDataSource(
    private val retrofitService: RevGeoApi
) : RevGeoDataSource {

    override suspend fun requestRevGeo(
        coords: String,
        orders: String,
        output: String
    ): RevGeoResult =
        retrofitService.requestRevGeo(coords, orders, output)

}