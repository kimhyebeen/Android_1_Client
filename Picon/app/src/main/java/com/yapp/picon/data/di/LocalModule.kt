package com.yapp.picon.data.di

import androidx.datastore.preferences.createDataStore
import com.yapp.picon.data.source.searched.DataStoreDataSource
import com.yapp.picon.data.source.searched.SearchedDataSource
import com.yapp.picon.data.source.searched.local.SearchedDatabase
import com.yapp.picon.data.source.searched.local.SearchedLocalDataSource
import com.yapp.picon.data.source.searched.remote.DataStoreLocalDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localModule = module {
    single { get<SearchedDatabase>().searchedDao() }
    single<SearchedDataSource> { SearchedLocalDataSource(get()) }

    single { androidContext().createDataStore("data_store") }
    single<DataStoreDataSource> { DataStoreLocalDataSource(get()) }
}