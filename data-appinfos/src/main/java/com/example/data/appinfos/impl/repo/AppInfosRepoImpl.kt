package com.example.data.appinfos.impl.repo

import com.example.commons.model.AppPackageName
import com.example.commons.result.Result
import com.example.data.appinfos.api.alias.AppInfos
import com.example.data.appinfos.api.error.AppInfosDataError
import com.example.data.appinfos.api.model.AppInfo
import com.example.data.appinfos.api.repo.AppInfosRepo
import com.example.data.appinfos.impl.datasource.AppInfosDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

internal class AppInfosRepoImpl(appInfosDataSource: AppInfosDataSource): AppInfosRepo {

// MARK: - Methods

    override fun getAppInfoPublisher(appPackageName: AppPackageName): Flow<Result<AppInfo>> {
        return _publisher
            .map { result ->
                when (result) {
                    is Result.Success -> {
                        val appInfo = result.data[appPackageName]
                        when (appInfo) {
                            null -> Result.error(throwable = AppInfosDataError.notFound())
                            else -> Result.success(data = appInfo)
                        }
                    }
                    is Result.Error -> {
                        result
                    }
                    is Result.Loading -> {
                        result
                    }
                }
            }
            .distinctUntilChanged { oldResult, newResult ->
                when (newResult) {
                    is Result.Success -> (oldResult.successData == newResult.data)
                    is Result.Error -> false
                    is Result.Loading -> (oldResult is Result.Loading)
                }
            }

    }

    override fun getAppInfosPublisher(): Flow<Result<AppInfos>> {
        return _publisher.map { result ->
            when (result) {
                is Result.Success -> {
                    val appInfos = result.data.values
                    Result.success(data = appInfos)
                }
                is Result.Error -> {
                    result
                }
                is Result.Loading -> {
                    result
                }
            }
        }
    }

    override fun ensureAppInfosAsync() {
        _ioScope.launch {
            _mutex.withLock {

                _publisher.value = Result.loading()

                val appInfos = _dataSource.getAppInfos()
                when (appInfos.isNotEmpty()) {
                    true -> updateAppInfos(appInfos)
                    else -> clearAppInfos()
                }
            }
        }
    }

    override fun updateAppInfoAsync(appPackageName: AppPackageName) {
        _ioScope.launch {
            _mutex.withLock {

                if (_isCacheEnsured) {
                    val appInfo = _dataSource.getAppInfo(appPackageName)
                    when (appInfo) {
                        null -> removeAppInfo(appPackageName)
                        else -> updateAppInfo(appPackageName, appInfo)
                    }
                }
            }
        }
    }

    override fun removeAppInfoAsync(appPackageName: AppPackageName) {
        _ioScope.launch {
            _mutex.withLock {
                if (_isCacheEnsured) {
                    removeAppInfo(appPackageName)
                }
            }
        }
    }

// MARK: - Private Methods

    private fun updateAppInfo(appPackageName: AppPackageName, appInfo: AppInfo) {
        _cache[appPackageName] = appInfo
        _publisher.value = Result.success(data = _cache.toMap())
    }

    private fun updateAppInfos(appInfos: Map<AppPackageName, AppInfo>) {
        _cache.clear()
        _cache.putAll(appInfos)

        _publisher.value = Result.success(data = _cache.toMap())
    }

    private fun removeAppInfo(appPackageName: AppPackageName) {
        _cache.remove(appPackageName)?.let {
            _publisher.value = Result.success(data = _cache.toMap())
        }
    }

    private fun clearAppInfos() {
        _cache.clear()
        _publisher.value = Result.error(throwable = AppInfosDataError.internal())
    }

// MARK: - Variables

    private val _dataSource: AppInfosDataSource = appInfosDataSource

    private val _cache: MutableMap<AppPackageName, AppInfo> = mutableMapOf()

    private val _mutex: Mutex = Mutex()

    private val _ioScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val _publisher: MutableStateFlow<Result<Map<AppPackageName, AppInfo>>> by lazy {
        ensureAppInfosAsync()
        MutableStateFlow(value = Result.loading())
    }

    private inline val _isCacheEnsured: Boolean
        get() = _cache.isNotEmpty()
}
