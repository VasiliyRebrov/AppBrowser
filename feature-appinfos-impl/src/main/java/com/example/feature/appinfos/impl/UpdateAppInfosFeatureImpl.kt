package com.example.feature.appinfos.impl

import com.example.data.appinfos.api.repo.AppInfosRepo
import com.example.feature.appinfos.api.UpdateAppInfosFeature
import com.example.feature.commons.impl.CoroutineUseCaseImpl

internal class UpdateAppInfosFeatureImpl(
    appInfosRepo: AppInfosRepo,
): CoroutineUseCaseImpl<Unit, Unit>(),
   UpdateAppInfosFeature {

// MARK: - Methods

    override suspend fun execute(params: Unit) {
        _appInfosRepo.updateAppInfos()
    }

// MARK: - Variables

    private val _appInfosRepo: AppInfosRepo = appInfosRepo
}
