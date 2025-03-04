@file:Suppress("NOTHING_TO_INLINE")

package com.example.presentation.screen.appinfos.primary

import com.example.presentation.alias.AppPrimaryInfoItems
import com.example.presentation.screen.ViewState

internal sealed interface AppPrimaryInfosViewState: ViewState {

// MARK: - Inner Types

    data class Content(val appPrimaryInfoItems: AppPrimaryInfoItems): AppPrimaryInfosViewState

    data object ForceRefreshDialog: AppPrimaryInfosViewState

    data object Loading: AppPrimaryInfosViewState

// MARK: - Companion

    companion object {

        inline fun content(appPrimaryInfoItems: AppPrimaryInfoItems): Content {
            return Content(appPrimaryInfoItems)
        }

        inline fun forceRefreshDialog(): ForceRefreshDialog {
            return ForceRefreshDialog
        }

        inline fun loading(): Loading {
            return Loading
        }
    }
}
