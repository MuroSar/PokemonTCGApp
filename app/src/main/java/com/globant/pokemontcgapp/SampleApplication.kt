package com.globant.pokemontcgapp

import android.app.Application
import com.globant.di.serviceModule
import com.globant.di.useCaseModule
import com.globant.pokemontcgapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@SampleApplication)
            modules(listOf(viewModelModule, useCaseModule, serviceModule))
        }
    }
}
