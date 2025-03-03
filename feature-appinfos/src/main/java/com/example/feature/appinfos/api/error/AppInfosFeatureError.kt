package com.example.feature.appinfos.api.error

sealed class AppInfosFeatureError: Error() {

// MARK: - Inner Types

    data object InternalError: AppInfosFeatureError()

    data object NotFound: AppInfosFeatureError()

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
