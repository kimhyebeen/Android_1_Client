package com.yapp.picon.presentation.di

import com.yapp.picon.presentation.login.LoginViewModel
import com.yapp.picon.presentation.map.MapViewModel
import com.yapp.picon.presentation.search.SearchViewModel
import com.yapp.picon.presentation.simplejoin.SimpleJoinViewModel
import com.yapp.picon.presentation.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SimpleJoinViewModel(get()) }
    viewModel { SearchViewModel(get(), get(), get(), get()) }
    viewModel { LoginViewModel(get(), get(), get()) }
    viewModel { MapViewModel() }
    viewModel { SplashViewModel(get(), get(), get()) }
}