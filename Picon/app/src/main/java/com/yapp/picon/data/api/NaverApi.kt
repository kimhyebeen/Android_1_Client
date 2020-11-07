package com.yapp.picon.data.api

import com.yapp.picon.data.model.LocalResult
import retrofit2.http.GET
import retrofit2.http.Query

interface NaverApi {
    @GET("v1/search/local.json")
    suspend fun requestLocal(
        @Query("query") searchWord: String,
        @Query("display") display: Int = 3
    ): LocalResult
}