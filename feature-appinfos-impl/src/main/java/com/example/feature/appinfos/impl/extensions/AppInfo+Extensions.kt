package com.example.feature.appinfos.impl.extensions

import com.example.data.appinfos.api.model.AppInfo
import com.example.feature.appinfos.api.model.AppPrimaryInfo
import com.example.feature.appinfos.api.model.AppSecondaryInfo

// MARK: - Methods

internal fun AppInfo.toAppPrimaryInfo(): AppPrimaryInfo {
    return AppPrimaryInfo(
        name = this.name,
        packageName = this.packageName,
        icon = this.icon,
    )
}

internal fun AppInfo.toAppSecondaryInfo(): AppSecondaryInfo {
    return AppSecondaryInfo(
        name = this.name,
        packageName = this.packageName,
        version = this.version,
        icon = this.icon,
    )
}
