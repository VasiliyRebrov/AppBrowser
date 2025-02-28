@file:Suppress("NOTHING_TO_INLINE")

package com.example.commons.result

sealed class Result<out To> {

// MARK: - Methods

    inline fun <R> fold(
        onSuccess: (value: To) -> Result<R>,
        onError: (throwable: Throwable) -> Result<R> = ::error,
        onLoading: () -> Result<R> = ::loading,
    ): Result<R> {

        return when (this) {
            is Success -> onSuccess(this.data)
            is Error -> onError(this.throwable)
            is Loading -> onLoading()
        }
    }

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[throwable=$throwable]"
            is Loading -> "Loading"
        }
    }

// MARK: - Inner Types

    data class Success<out To>(val data: To): Result<To>()

    data class Error(val throwable: Throwable): Result<Nothing>()

    object Loading: Result<Nothing>()

// MARK: - Companion

    companion object {

        inline fun <T> success(data: T): Success<T> {
            return Success(data)
        }

        inline fun error(throwable: Throwable): Error {
            return Error(throwable)
        }

        inline fun loading(): Loading {
            return Loading
        }
    }
}
