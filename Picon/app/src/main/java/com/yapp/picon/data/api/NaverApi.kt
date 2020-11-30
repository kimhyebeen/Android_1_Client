package com.yapp.picon.data.api

import com.yapp.picon.SecretKeySet
import com.yapp.picon.data.model.LocalResult
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NaverApi {
    @Headers(
        "X-Naver-Client-Id: ${SecretKeySet.X_Naver_Client_Id}",
        "X-Naver-Client-Secret: ${SecretKeySet.X_Naver_Client_Secret}"
    )
    @GET("v1/search/local.json")
    suspend fun requestLocal(
        @Query("query") searchWord: String,
        @Query("display") display: Int = 5
    ): LocalResult
}