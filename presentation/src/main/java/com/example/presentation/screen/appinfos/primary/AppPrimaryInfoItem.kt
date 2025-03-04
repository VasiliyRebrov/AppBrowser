package com.example.presentation.screen.appinfos.primary

import android.graphics.drawable.Drawable
import com.example.commons.model.AppName
import com.example.commons.model.AppPackageName

internal data class AppPrimaryInfoItem(
    val name: AppName,
    val packageName: AppPackageName,
    val drawable: Drawable,
)
