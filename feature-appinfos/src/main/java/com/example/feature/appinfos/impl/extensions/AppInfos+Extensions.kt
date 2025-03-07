package com.example.feature.appinfos.impl.extensions

import com.example.data.appinfos.api.alias.AppInfos
import com.example.feature.appinfos.api.alias.AppPrimaryInfos

// MARK: - Methods

internal fun AppInfos.toAppPrimaryInfos(): AppPrimaryInfos {
    return this.map { it.toAppPrimaryInfo() }
}
