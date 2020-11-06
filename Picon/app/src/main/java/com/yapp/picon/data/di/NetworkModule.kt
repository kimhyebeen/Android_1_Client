package com.yapp.picon.data.di

import com.yapp.picon.data.api.NaverApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {
    private const val NAVER_URL = "https://openapi.naver.com"

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor {
            it.proceed(
                it.request().newBuilder()
                    .header("X-Naver-Client-Id", "MB0QmA3dWk5pHT2Mbz7_")
                    .header("X-Naver-Client-Secret", "jgCRJfYDDT")
                    .build()
            )
        }
        .build()

    val naverApi: NaverApi = Retrofit.Builder()
        .baseUrl(NAVER_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(NaverApi::class.java)
}