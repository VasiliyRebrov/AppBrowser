@file:Suppress("NOTHING_TO_INLINE")

package com.example.presentation.screen.appinfos.primary

import com.example.presentation.screen.ViewEvent

internal sealed interface AppPrimaryInfosViewEvent: ViewEvent {

// MARK: - Inner Types

    data class ShowAppSecondaryInfoScreen(val appPrimaryInfoItem: AppPrimaryInfoItem): AppPrimaryInfosViewEvent

// MARK: - Companion

    companion object {

        inline fun showAppSecondaryInfoScreen(appPrimaryInfoItem: AppPrimaryInfoItem): ShowAppSecondaryInfoScreen {
            return ShowAppSecondaryInfoScreen(appPrimaryInfoItem)
        }
    }
}
