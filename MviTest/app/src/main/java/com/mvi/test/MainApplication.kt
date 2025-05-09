package com.mvi.test

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // https://www.youtube.com/watch?v=Yg7WNdYo8_c
        // No more Manual Koin Initialization - New Context Binding is Awesome! 🍻
        startKoin {
            androidContext(this@MainApplication)
            androidLogger()
            modules(appModule)
        }
    }
}