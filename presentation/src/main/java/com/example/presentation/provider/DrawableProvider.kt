package com.example.presentation.provider

import android.graphics.drawable.Drawable
import com.example.commons.model.AppPackageName

internal interface DrawableProvider {

// MARK: - Methods

    fun provide(appPackageName: AppPackageName): Drawable
}
