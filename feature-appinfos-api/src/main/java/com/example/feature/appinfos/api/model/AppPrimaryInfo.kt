package com.example.feature.appinfos.api.model

import com.example.commons.model.AppIcon
import com.example.commons.model.AppName
import com.example.commons.model.AppPackageName

data class AppPrimaryInfo(
    val name: AppName,
    val packageName: AppPackageName,
    val icon: AppIcon,
)
