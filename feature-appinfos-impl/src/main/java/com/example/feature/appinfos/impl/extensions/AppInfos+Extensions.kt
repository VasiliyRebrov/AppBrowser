package com.example.feature.appinfos.impl.extensions

import com.example.feature.appinfos.impl.AppPrimaryInfos
import com.example.feature.appinfos.impl.alias.AppInfos

// MARK: - Methods

internal fun AppInfos.toAppPrimaryInfos(): AppPrimaryInfos {
    return this
        .map { it.toAppPrimaryInfo() }
        .sortedBy { it.name.rawValue }
}
