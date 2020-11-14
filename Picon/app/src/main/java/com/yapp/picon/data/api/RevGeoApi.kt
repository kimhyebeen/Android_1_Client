package com.yapp.picon.data.api

import com.yapp.picon.data.model.RevGeoResult
import retrofit2.http.GET
import retrofit2.http.Query

interface RevGeoApi {
    @GET("map-reversegeocode/v2/gc")
    suspend fun requestRevGeo(
        @Query("coords") coords: String,
        @Query("orders") orders: String,
        @Query("output") output: String
    ): RevGeoResult
}