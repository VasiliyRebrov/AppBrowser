package com.example.feature.appinfos.impl

import com.example.data.appinfos.api.repo.AppInfosRepo
import com.example.feature.appinfos.api.UpdateAppInfosFeature
import com.example.feature.commons.impl.CoroutineFeatureImpl

internal class UpdateAppInfosFeatureImpl(
    appInfosRepo: AppInfosRepo,
): CoroutineFeatureImpl<Unit, Unit>(),
   UpdateAppInfosFeature {

// MARK: - Methods

    override suspend fun execute(params: Unit) {
        _appInfosRepo.ensureAppInfosAsync()
    }

// MARK: - Variables

    private val _appInfosRepo: AppInfosRepo = appInfosRepo
}
