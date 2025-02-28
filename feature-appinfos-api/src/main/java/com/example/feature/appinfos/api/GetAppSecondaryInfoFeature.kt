package com.example.feature.appinfos.api

import com.example.commons.model.AppPackageName
import com.example.feature.appinfos.api.model.AppSecondaryInfo
import com.example.feature.commons.api.FlowFeature

interface GetAppSecondaryInfoFeature: FlowFeature<AppPackageName, AppSecondaryInfo>
