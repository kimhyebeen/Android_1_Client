package com.yapp.picon.data.di

import com.yapp.picon.data.repository.post.PostRepository
import com.yapp.picon.data.repository.post.PostRepositoryImpl
import com.yapp.picon.data.repository.search.SearchRepositoryImpl
import com.yapp.picon.data.repository.user.UserRepository
import com.yapp.picon.data.repository.user.UserRepositoryImpl
import com.yapp.picon.data.source.searched.SearchRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<SearchRepository> {
        SearchRepositoryImpl(
            get(),
            get(),
            get()
        )
    }

    single<UserRepository> {
        UserRepositoryImpl(
            get(),
            get()
        )
    }

    single<PostRepository> {
        PostRepositoryImpl(
            get()
        )
    }

}