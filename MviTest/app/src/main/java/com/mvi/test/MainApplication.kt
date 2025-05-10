package com.mvi.test

import android.app.Application
import com.mvi.test.di.initializeKoin
import org.koin.android.ext.koin.androidContext

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeKoin {
            androidContext(this@MainApplication)
        }
    }
}