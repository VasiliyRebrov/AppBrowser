package com.example.data.appinfos.api.repo

import com.example.commons.model.AppPackageName
import com.example.commons.result.Result
import com.example.data.appinfos.api.alias.AppInfos
import com.example.data.appinfos.api.model.AppInfo
import kotlinx.coroutines.flow.Flow

interface AppInfosRepo {

// MARK: - Methods

    fun getAppInfoPublisher(appPackageName: AppPackageName): Flow<Result<AppInfo>>

    fun getAppInfosPublisher(): Flow<Result<AppInfos>>

    fun ensureAppInfosAsync()

    fun updateAppInfoAsync(appPackageName: AppPackageName)

    fun removeAppInfoAsync(appPackageName: AppPackageName)
}
