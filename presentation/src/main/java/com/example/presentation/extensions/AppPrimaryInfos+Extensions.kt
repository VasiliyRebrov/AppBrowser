package com.example.presentation.extensions

import com.example.feature.appinfos.api.alias.AppPrimaryInfos
import com.example.presentation.alias.AppPrimaryInfoItems
import com.example.presentation.provider.DrawableProvider
import java.text.Collator

// MARK: - Methods

internal fun AppPrimaryInfos.toAppPrimaryInfoItems(
    collator: Collator,
    drawableProvider: DrawableProvider,
): AppPrimaryInfoItems {

    return this
        .map { appPrimaryInfo ->
            appPrimaryInfo.toAppPrimaryInfoItem(drawableProvider)
        }
        .sortedWith { first, second ->
            collator.compare(first.name.rawValue, second.name.rawValue)
        }
}
