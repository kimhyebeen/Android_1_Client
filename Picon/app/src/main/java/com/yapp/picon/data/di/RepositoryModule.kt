package com.yapp.picon.data.di

import com.yapp.picon.data.source.searched.NaverRepository
import com.yapp.picon.data.source.searched.NaverRepositoryImpl
import com.yapp.picon.data.source.searched.SearchedRepository
import com.yapp.picon.data.source.searched.SearchedRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<NaverRepository> {
        NaverRepositoryImpl(
            get()
        )
    }

    single<SearchedRepository> {
        SearchedRepositoryImpl(
            get()
        )
    }
}