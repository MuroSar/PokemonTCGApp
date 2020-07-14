package com.globant.pokemontcgapp

import android.app.Application
import com.facebook.stetho.Stetho
import com.globant.di.databaseModule
import com.globant.di.serviceModule
import com.globant.di.useCaseModule
import com.globant.pokemontcgapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)

        startKoin {
            androidContext(this@SampleApplication)
            modules(listOf(viewModelModule, useCaseModule, serviceModule, databaseModule))
        }
    }
}
