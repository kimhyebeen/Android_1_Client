package com.yapp.picon.data.network

import com.yapp.picon.data.api.YappApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {
    private const val YAPP_URL = "http://www.yappandone17.shop"

    private val yappOkHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor {
            it.proceed(
                it.request().newBuilder()
                    .header("Content-Type", "application/json;charset=utf-8")
                    .build()
            )
        }
        .build()

    val yappApi: YappApi = Retrofit.Builder()
        .baseUrl(YAPP_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(yappOkHttpClient)
        .build()
        .create(YappApi::class.java)
}