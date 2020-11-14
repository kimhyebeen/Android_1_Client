package com.yapp.picon.data.api

import com.yapp.picon.data.model.LocalResult
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NaverApi {
    @Headers(
        "X-Naver-Client-Id: MB0QmA3dWk5pHT2Mbz7_",
        "X-Naver-Client-Secret: jgCRJfYDDT"
    )
    @GET("v1/search/local.json")
    suspend fun requestLocal(
        @Query("query") searchWord: String,
        @Query("display") display: Int = 3
    ): LocalResult
}