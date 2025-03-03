@file:Suppress("NOTHING_TO_INLINE")

package com.example.commons.result

sealed class Result<out To> {

// MARK: - Properties

    val successData: To?
        get() = (this as? Success)?.data

// MARK: - Methods

    inline fun <R> fold(
        onSuccess: (value: To) -> Result<R>,
        onError: (th: Throwable) -> Result<R> = ::error,
        onLoading: () -> Result<R> = ::loading,
    ): Result<R> {

        return when (this) {
            is Success -> onSuccess(this.data)
            is Error -> onError(this.th)
            is Loading -> onLoading()
        }
    }

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=${this.data}]"
            is Error -> "Error[th=${this.th}]"
            is Loading -> "Loading"
        }
    }

// MARK: - Inner Types

    data class Success<out To>(val data: To): Result<To>()

    class Error(val th: Throwable): Result<Nothing>()

    object Loading: Result<Nothing>()

// MARK: - Companion

    companion object {

        inline fun <T> success(data: T): Success<T> {
            return Success(data)
        }

        inline fun error(th: Throwable): Error {
            return Error(th)
        }

        inline fun loading(): Loading {
            return Loading
        }
    }
}
