package com.yapp.picon.data.di

import com.yapp.picon.data.api.NaverApi
import com.yapp.picon.data.api.RevGeoApi
import com.yapp.picon.data.api.YappApi
import com.yapp.picon.data.source.revgeo.RevGeoDataSource
import com.yapp.picon.data.source.revgeo.remote.RevGeoRemoteDataSource
import com.yapp.picon.data.source.searched.NaverDataSource
import com.yapp.picon.data.source.searched.remote.NaverRemoteDataSource
import com.yapp.picon.data.source.yapp.YappDataSource
import com.yapp.picon.data.source.yapp.remote.YappRemoteDataSource
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import retrofit2.Retrofit

val remoteModule = module {
    single<NaverApi> { get<Retrofit> { parametersOf("https://openapi.naver.com") }.create(NaverApi::class.java) }
    single<NaverDataSource> { NaverRemoteDataSource(get()) }
    single<YappApi> { get<Retrofit> { parametersOf("http://www.yappandone17.shop") }.create(YappApi::class.java) }
    single<YappDataSource> { YappRemoteDataSource(get()) }
    single<RevGeoApi> { get<Retrofit> { parametersOf("https://naveropenapi.apigw.ntruss.com") }.create(RevGeoApi::class.java) }
    single<RevGeoDataSource> { RevGeoRemoteDataSource(get()) }
}