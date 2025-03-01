package com.example.feature.appinfos.impl

import android.util.Log
import com.example.commons.result.Result
import com.example.data.appinfos.api.alias.AppInfos
import com.example.data.appinfos.api.repo.AppInfosRepo
import com.example.feature.appinfos.api.GetAppPrimaryInfosFeature
import com.example.feature.appinfos.api.error.AppInfosError
import com.example.feature.appinfos.impl.alias.AppPrimaryInfos
import com.example.feature.appinfos.impl.extensions.toAppPrimaryInfos
import com.example.feature.commons.impl.FlowFeatureImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class GetAppPrimaryInfosFeatureImpl(
    appInfosRepo: AppInfosRepo,
): FlowFeatureImpl<Unit, AppPrimaryInfos>(),
   GetAppPrimaryInfosFeature {

// MARK: - Methods

    override fun execute(params: Unit): Flow<Result<AppPrimaryInfos>> {
        return _appInfosRepo.getAppInfosPublisher().map(this::transform)
    }

// MARK: - Private Methods

    private fun transform(result: Result<AppInfos>): Result<AppPrimaryInfos> {
        return result.fold(
            onSuccess = this::handleSuccess,
            onError = this::handleError,
        )
    }

    private fun handleSuccess(appInfos: AppInfos): Result<AppPrimaryInfos> {
        return when (appInfos.isNotEmpty()) {
            true -> Result.success(data = appInfos.toAppPrimaryInfos())
            else -> Result.error(throwable = AppInfosError.internalError())
        }
    }

    private fun handleError(throwable: Throwable): Result<AppPrimaryInfos> {
        Log.w(TAG, throwable)
        return Result.error(throwable = AppInfosError.internalError())
    }

// MARK: - Companion

    companion object {
        private val TAG = GetAppPrimaryInfosFeatureImpl::class.java.simpleName
    }

// MARK: - Variables

    private val _appInfosRepo: AppInfosRepo = appInfosRepo
}
