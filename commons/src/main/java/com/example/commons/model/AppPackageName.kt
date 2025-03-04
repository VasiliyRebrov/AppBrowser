package com.example.commons.model

import android.os.Parcelable
import com.example.commons.utils.requireNotBlank
import kotlinx.parcelize.Parcelize

@JvmInline
@Parcelize
value class AppPackageName(val rawValue: String): Parcelable {

// MARK: - Construction

    init {
        requireNotBlank(this.rawValue) { "rawValue is blank" }
    }

// MARK: - Methods

    override fun toString(): String {
        return this.rawValue
    }
}
