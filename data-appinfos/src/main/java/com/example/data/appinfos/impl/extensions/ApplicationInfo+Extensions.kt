package com.example.data.appinfos.impl.extensions

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.example.commons.model.AppName
import com.example.commons.model.AppPackageName
import com.example.commons.model.AppVersion
import com.example.data.appinfos.api.model.AppInfo

// MARK: - Methods

internal fun ApplicationInfo.toAppInfo(packageManager: PackageManager): AppInfo {

    val appName = packageManager.getApplicationLabel(this).toString()
    val appPackageName = this.packageName

    val appVersion = packageManager.getPackageInfo(appPackageName, PackageManager.GET_META_DATA)
        .versionName
        .toString()

    val appInfo = AppInfo(
        name = AppName(appName),
        packageName = AppPackageName(appPackageName),
        version = AppVersion(appVersion),
    )

    return appInfo
}
