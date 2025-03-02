package com.example.data.appinfos.impl.datasource

import com.example.commons.model.AppPackageName
import com.example.data.appinfos.api.model.AppInfo

internal interface AppInfosDataSource {

// MARK: - Methods

    fun getAppInfo(appPackageName: AppPackageName): AppInfo?

    fun getAppInfos(): Map<AppPackageName, AppInfo>
}
