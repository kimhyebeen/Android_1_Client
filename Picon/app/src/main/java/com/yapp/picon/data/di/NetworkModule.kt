package com.yapp.picon.data.di

import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    factory { (baseUrl: String) ->
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(get())
            .build()
    }

    single {
        GsonConverterFactory.create() as Converter.Factory
    }
}