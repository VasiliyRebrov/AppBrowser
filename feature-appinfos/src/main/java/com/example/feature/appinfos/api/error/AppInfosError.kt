package com.example.feature.appinfos.api.error

import com.example.feature.commons.api.error.FeatureError

sealed class AppInfosError: FeatureError() {

// MARK: - Inner Types

    data object InternalError: AppInfosError()

    data object NotFound: AppInfosError()

// MARK: - Companion

    companion object {

        fun internalError(): InternalError {
            return InternalError
        }

        fun notFound(): NotFound {
            return NotFound
        }
    }
}
