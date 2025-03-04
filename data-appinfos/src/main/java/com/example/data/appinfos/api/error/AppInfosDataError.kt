@file:Suppress("NOTHING_TO_INLINE")

package com.example.data.appinfos.api.error

sealed class AppInfosDataError: Error() {

// MARK: - Inner Types

    data object Internal: AppInfosDataError()

    data object NotFound: AppInfosDataError()

// MARK: - Companion

    companion object {

        inline fun internal(): Internal {
            return Internal
        }

        inline fun notFound(): NotFound {
            return NotFound
        }
    }
}
