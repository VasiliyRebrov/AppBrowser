package com.example.commons.model

import com.example.commons.kotlin.requireNotBlank

@JvmInline
value class AppIcon(val rawValue: String) {

// MARK: - Construction

    init {
        requireNotBlank(this.rawValue) { "rawValue is blank" }
    }

// MARK: - Methods

    override fun toString(): String {
        return this.rawValue
    }
}
