package com.example.data.appinfos.api.repo

import com.example.commons.model.AppPackageName
import com.example.commons.result.Result
import com.example.data.appinfos.api.model.AppInfo
import kotlinx.coroutines.flow.StateFlow

interface AppInfosRepo {

// MARK: - Methods

    fun getAppInfosPublisher(): StateFlow<Result<Map<AppPackageName, AppInfo>>>

    fun updateAppInfo(appPackageName: AppPackageName)

    fun updateAppInfos()
}
