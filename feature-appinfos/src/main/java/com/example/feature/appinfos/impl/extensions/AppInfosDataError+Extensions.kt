package com.example.feature.appinfos.impl.extensions

import com.example.data.appinfos.api.error.AppInfosDataError
import com.example.feature.appinfos.api.error.AppInfosFeatureError

// MARK: - Methods

internal fun AppInfosDataError.toAppInfosFeatureError(): AppInfosFeatureError {
    return when (this) {
        is AppInfosDataError.Internal -> AppInfosFeatureError.internal()
        is AppInfosDataError.NotFound -> AppInfosFeatureError.notFound()
    }
}
