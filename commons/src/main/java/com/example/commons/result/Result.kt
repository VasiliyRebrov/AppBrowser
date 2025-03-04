@file:Suppress("NOTHING_TO_INLINE")

package com.example.commons.result

sealed class Result<out To> {

// MARK: - Properties

    val successData: To?
        get() = (this as? Success)?.data

// MARK: - Methods

    inline fun <R> fold(
        onSuccess: (value: To) -> R,
        onError: (throwable: Throwable) -> R,
        onLoading: () -> R,
    ): R {

        return when (this) {
            is Success -> onSuccess(this.data)
            is Error -> onError(this.throwable)
            is Loading -> onLoading()
        }
    }

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=${this.data}]"
            is Error -> "Error[throwable=${this.throwable}]"
            is Loading -> "Loading"
        }
    }

// MARK: - Inner Types

    data class Success<out To>(val data: To): Result<To>()

    class Error(val throwable: Throwable): Result<Nothing>()

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
