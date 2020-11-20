package com.yapp.picon.data.di

import com.yapp.picon.data.source.searched.SearchedDataSource
import com.yapp.picon.data.source.searched.local.SearchedDatabase
import com.yapp.picon.data.source.searched.local.SearchedLocalDataSource
import org.koin.dsl.module

val localModule = module {
    single { get<SearchedDatabase>().searchedDao() }
    single<SearchedDataSource> { SearchedLocalDataSource(get()) }
}