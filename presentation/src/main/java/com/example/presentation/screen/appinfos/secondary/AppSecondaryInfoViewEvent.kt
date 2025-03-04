@file:Suppress("NOTHING_TO_INLINE")

package com.example.presentation.screen.appinfos.secondary

import com.example.presentation.screen.ViewEvent

internal sealed interface AppSecondaryInfoViewEvent: ViewEvent {

// MARK: - Inner Types

    data object LaunchApp: AppSecondaryInfoViewEvent

    data object ShowApkHashCalculationErrorDialog: AppSecondaryInfoViewEvent

    data object ShowAppLaunchUnavailableDialog: AppSecondaryInfoViewEvent

    data object ShowAppNotFoundDialog: AppSecondaryInfoViewEvent

// MARK: - Companion

    companion object {

        inline fun launchApp(): LaunchApp {
            return LaunchApp
        }

        inline fun showApkHashCalculationErrorDialog(): ShowApkHashCalculationErrorDialog {
            return ShowApkHashCalculationErrorDialog
        }

        inline fun showAppLaunchUnavailableDialog(): ShowAppLaunchUnavailableDialog {
            return ShowAppLaunchUnavailableDialog
        }

        inline fun showAppNotFoundDialog(): ShowAppNotFoundDialog {
            return ShowAppNotFoundDialog
        }
    }
}
