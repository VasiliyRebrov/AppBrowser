@file:Suppress("NOTHING_TO_INLINE")

package com.example.feature.appinfos.api.error

sealed class AppInfosFeatureError: Error() {

// MARK: - Inner Types

    data object ApkHashCalculation: AppInfosFeatureError()

    data object Internal: AppInfosFeatureError()

    data object NotFound: AppInfosFeatureError()

// MARK: - Companion

    companion object {

        inline fun apkHashCalculation(): ApkHashCalculation {
            return ApkHashCalculation
        }

        inline fun internal(): Internal {
            return Internal
        }

        inline fun notFound(): NotFound {
            return NotFound
        }
    }
}
