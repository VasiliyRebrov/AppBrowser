package com.example.commons.kotlin

// MARK: - Methods

inline fun <T: CharSequence> requireNotBlank(value: T?, lazyMessage: () -> Any): T {
    if (value.isNullOrBlank()) {
        val message = lazyMessage()
        throw IllegalArgumentException(message.toString())
    }
    else {
        return value
    }
}
