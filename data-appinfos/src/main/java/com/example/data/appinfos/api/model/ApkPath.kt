package com.example.data.appinfos.api.model

import com.example.commons.utils.requireNotBlank

@JvmInline
value class ApkPath(val rawValue: String) {

// MARK: - Construction

    init {
        requireNotBlank(this.rawValue) { "rawValue is blank" }
    }

// MARK: - Methods

    override fun toString(): String {
        return this.rawValue
    }
}
