package com.yapp.picon

import android.app.Application
import com.yapp.picon.data.di.*
import com.yapp.picon.domain.di.useCaseModule
import com.yapp.picon.presentation.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(
                listOf(
                    networkModule,
                    databaseModule,
                    repositoryModule,
                    localModule,
                    remoteModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}