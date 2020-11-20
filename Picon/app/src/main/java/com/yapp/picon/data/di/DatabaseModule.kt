package com.yapp.picon.data.di

import androidx.room.Room
import com.yapp.picon.data.source.searched.local.SearchedDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            SearchedDatabase::class.java,
            "searched.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}