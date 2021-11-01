package com.example.corona.application

import android.app.Application
import androidx.core.content.FileProvider
import com.example.corona.application.di.applicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(applicationModule)
        }
    }
}