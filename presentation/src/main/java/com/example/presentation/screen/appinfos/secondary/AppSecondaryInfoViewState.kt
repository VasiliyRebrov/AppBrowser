@file:Suppress("NOTHING_TO_INLINE")

package com.example.presentation.screen.appinfos.secondary

import com.example.feature.appinfos.api.model.AppSecondaryInfo
import com.example.presentation.screen.ViewState

internal sealed interface AppSecondaryInfoViewState: ViewState {

// MARK: - Inner Types

    data class Content(
        val appSecondaryInfo: AppSecondaryInfo,
        val ignoreApkHash: Boolean,
    ): AppSecondaryInfoViewState

    data object Dismiss: AppSecondaryInfoViewState

// MARK: - Companion

    companion object {

        inline fun content(appSecondaryInfo: AppSecondaryInfo, ignoreApkHash: Boolean = false): Content {
            return Content(appSecondaryInfo, ignoreApkHash)
        }

        inline fun dismiss(): Dismiss {
            return Dismiss
        }
    }
}
