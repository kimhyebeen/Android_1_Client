package com.yapp.picon.data.di

import com.yapp.picon.data.api.NaverApi
import com.yapp.picon.data.source.searched.NaverDataSource
import com.yapp.picon.data.source.searched.remote.NaverRemoteDataSource
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import retrofit2.Retrofit

val remoteModule = module {
    single<NaverApi> { get<Retrofit> { parametersOf("https://openapi.naver.com") }.create(NaverApi::class.java) }
    single<NaverDataSource> { NaverRemoteDataSource(get()) }
}