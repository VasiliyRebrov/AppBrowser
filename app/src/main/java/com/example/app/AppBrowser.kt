package com.example.app

import android.app.Application
import com.example.data.appinfos.api.repo.AppInfosRepo
import com.example.presentation.di.PresentationKoinContainer
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

internal class AppBrowser: Application() {

// MARK: - Methods

    override fun onCreate() {
        super.onCreate()

        initKoin()
        _appBrowserReceiver.register(this)
    }

// MARK: - Private Methods

    private fun initKoin() {
        startKoin { androidContext(this@AppBrowser) }
        PresentationKoinContainer.loadModules()
    }

// MARK: - Variables

    private val _appInfosRepo: AppInfosRepo by inject<AppInfosRepo>()

    private val _appBrowserReceiver: AppBrowserReceiver by lazy {
        AppBrowserReceiver(
            onAppAdded = _appInfosRepo::updateAppInfoAsync,
            onAppRemoved = _appInfosRepo::removeAppInfoAsync,
        )
    }
}
