package com.example.feature.appinfos.api

import com.example.feature.appinfos.api.model.AppPrimaryInfo
import com.example.feature.commons.api.Feature

interface GetAppPrimaryInfosFeature: Feature<Unit, List<AppPrimaryInfo>>
