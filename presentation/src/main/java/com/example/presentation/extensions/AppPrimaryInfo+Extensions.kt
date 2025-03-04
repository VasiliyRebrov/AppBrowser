package com.example.presentation.extensions

import com.example.feature.appinfos.api.model.AppPrimaryInfo
import com.example.presentation.provider.DrawableProvider
import com.example.presentation.screen.appinfos.primary.AppPrimaryInfoItem

// MARK: - Methods

internal fun AppPrimaryInfo.toAppPrimaryInfoItem(drawableProvider: DrawableProvider): AppPrimaryInfoItem {
    return AppPrimaryInfoItem(
        name = this.name,
        packageName = this.packageName,
        drawable = drawableProvider.provide(this.packageName),
    )
}
