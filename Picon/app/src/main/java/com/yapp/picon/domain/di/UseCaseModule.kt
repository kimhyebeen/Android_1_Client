package com.yapp.picon.domain.di

import com.yapp.picon.domain.usecase.*
import org.koin.dsl.module

val useCaseModule = module {
    single {
        SimpleJoinUseCase(get())
    }
    single {
        GetLocalUseCase(get())
    }
    single {
        GetAllSearedUseCase(get())
    }
    single {
        InsertSearedUseCase(get())
    }
    single {
        DeleteSearedUseCase(get())
    }
}