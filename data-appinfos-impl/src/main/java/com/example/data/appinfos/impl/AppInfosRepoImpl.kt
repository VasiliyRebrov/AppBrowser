package com.example.data.appinfos.impl

import com.example.commons.model.AppPackageName
import com.example.commons.result.Result
import com.example.data.appinfos.api.alias.AppInfos
import com.example.data.appinfos.api.model.AppInfo
import com.example.data.appinfos.api.repo.AppInfosRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

internal class AppInfosRepoImpl(appInfosDataSource: AppInfosDataSource): AppInfosRepo {

// MARK: - Construction

    init {
        updateAppInfos()
    }

// MARK: - Methods

    override fun getAppInfosPublisher(): StateFlow<Result<AppInfos>> {
        return _appInfoPublisher.asStateFlow()
    }

    override fun updateAppInfo(appPackageName: AppPackageName) {
        _ioScope.launch {
            _mutex.withLock {

                _appInfoPublisher.value = Result.loading()

                try {
                    val appInfo = _appInfosDataSource.getAppInfo(appPackageName)
                    when (appInfo) {
                        null -> _appInfosMap.remove(appPackageName)
                        else -> _appInfosMap[appPackageName] = appInfo
                    }

                    _appInfoPublisher.value = Result.success(_appInfos)
                }
                catch (ex: Exception) {
                    _appInfoPublisher.value = Result.error(ex)
                }
            }
        }
    }

    override fun updateAppInfos() {
        _ioScope.launch {
            _mutex.withLock {

                _appInfoPublisher.value = Result.loading()

                try {
                    val appInfos = _appInfosDataSource.getAppInfos()

                    _appInfosMap.clear()
                    _appInfosMap.putAll(appInfos)

                    _appInfoPublisher.value = Result.success(_appInfos)
                }
                catch (ex: Exception) {
                    _appInfoPublisher.value = Result.error(ex)
                }
            }
        }
    }

// MARK: - Variables

    private val _appInfosDataSource: AppInfosDataSource = appInfosDataSource

    private val _mutex: Mutex = Mutex()

    private val _ioScope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    private val _appInfosMap: MutableMap<AppPackageName, AppInfo> = mutableMapOf()

    private val _appInfoPublisher: MutableStateFlow<Result<AppInfos>> by lazy {
        MutableStateFlow(value = Result.loading())
    }

    private inline val _appInfos: AppInfos
        get() = _appInfosMap.values
}
