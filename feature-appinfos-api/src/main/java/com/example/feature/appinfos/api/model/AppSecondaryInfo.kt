package com.example.feature.appinfos.api.model

import com.example.commons.model.AppIcon
import com.example.commons.model.AppName
import com.example.commons.model.AppPackageName
import com.example.commons.model.AppVersion

data class AppSecondaryInfo(
    val name: AppName,
    val packageName: AppPackageName,
    val version: AppVersion,
    val icon: AppIcon,
    val apkSum: ApkSum? = null,
)
