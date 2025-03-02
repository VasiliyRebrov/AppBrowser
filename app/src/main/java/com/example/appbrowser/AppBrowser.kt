package com.example.appbrowser

import android.app.Application
import com.example.appbrowser.di.ApplicationKoinContainer
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppBrowser: Application() {

// MARK: - Methods

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

// MARK: - Private Methods

    private fun initKoin() {
        startKoin {
            androidContext(this@AppBrowser)
        }
        ApplicationKoinContainer.loadModules()
    }
}
