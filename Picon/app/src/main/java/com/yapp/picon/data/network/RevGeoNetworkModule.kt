package com.yapp.picon.data.network

import com.yapp.picon.SecretKeySet
import com.yapp.picon.data.api.RevGeoApi
import com.yapp.picon.data.model.RevGeoResult
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RevGeoNetworkModule {
    private const val REVERSE_GEO_URL = "https://naveropenapi.apigw.ntruss.com/"

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor {
            it.proceed(
                it.request().newBuilder()
                    .header("X-NCP-APIGW-API-KEY-ID", SecretKeySet.CLIENT_ID)
                    .header("X-NCP-APIGW-API-KEY", SecretKeySet.CLIENT_SECRET)
                    .build()
            )
        }
        .build()

    private val REVERSE_GEO_API: RevGeoApi = Retrofit.Builder()
        .baseUrl(REVERSE_GEO_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(RevGeoApi::class.java)

    suspend fun getRevGeo(coords: String, orders: String): RevGeoResult {
        return REVERSE_GEO_API.requestRevGeo(coords, orders, "json")
    }
}