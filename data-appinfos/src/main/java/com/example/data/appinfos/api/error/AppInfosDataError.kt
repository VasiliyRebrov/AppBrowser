package com.example.data.appinfos.api.error


sealed class AppInfosDataError: Error() {

// MARK: - Inner Types

    data object InternalError: AppInfosDataError()

    data object NotFound: AppInfosDataError()

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
