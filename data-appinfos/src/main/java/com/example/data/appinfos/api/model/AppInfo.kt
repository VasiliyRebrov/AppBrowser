package com.example.data.appinfos.api.model

import com.example.commons.model.AppName
import com.example.commons.model.AppPackageName
import com.example.commons.model.AppVersion

data class AppInfo(
    val name: AppName,
    val packageName: AppPackageName,
    val version: AppVersion,
)
