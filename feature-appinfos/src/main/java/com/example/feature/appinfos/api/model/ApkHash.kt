package com.example.feature.appinfos.api.model

import com.example.commons.utils.requireNotBlank

data class ApkHash(
    val rawValue: String,
    val algorithm: Algorithm,
) {

// MARK: - Construction

    init {
        requireNotBlank(this.rawValue) { "rawValue is blank" }
    }
}
