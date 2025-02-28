package com.example.feature.appinfos.impl

import android.util.Log
import com.example.commons.model.AppPackageName
import com.example.commons.result.Result
import com.example.data.appinfos.api.model.AppInfo
import com.example.data.appinfos.api.repo.AppInfosRepo
import com.example.feature.appinfos.api.GetAppSecondaryInfoFeature
import com.example.feature.appinfos.api.error.AppInfosError
import com.example.feature.appinfos.api.model.ApkSum
import com.example.feature.appinfos.api.model.AppSecondaryInfo
import com.example.feature.appinfos.impl.alias.AppInfos
import com.example.feature.appinfos.impl.extensions.toAppSecondaryInfo
import com.example.feature.commons.impl.FlowFeatureImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform

internal class GetAppSecondaryInfoFeatureImpl(
    appInfosRepo: AppInfosRepo,
): FlowFeatureImpl<AppPackageName, AppSecondaryInfo>(),
   GetAppSecondaryInfoFeature {

// MARK: - Methods

    override fun execute(params: AppPackageName): Flow<Result<AppSecondaryInfo>> {
        _appPackageName = params

        return _appInfosRepo.getAppInfosPublisher()
            .map(this::transform)
            .distinctUntilChanged(this::areEquivalent)
            .transform { result -> publish(result) }
    }

// MARK: - Private Methods

    private fun transform(result: Result<AppInfos>): Result<AppSecondaryInfo> {
        return result.fold(
            onSuccess = this::handleSuccess,
            onError = this::handleError,
        )
    }

    private fun areEquivalent(
        oldResult: Result<AppSecondaryInfo>,
        newResult: Result<AppSecondaryInfo>,
    ): Boolean {

        return when (newResult) {
            is Result.Success -> (newResult.data == oldResult.successData)
            is Result.Error -> false
            is Result.Loading -> (oldResult is Result.Loading)
        }
    }

    private suspend fun FlowCollector<Result<AppSecondaryInfo>>.publish(result: Result<AppSecondaryInfo>) {
        emit(result)

        if (result is Result.Success) {
            val data = result.data.copy(apkSum = calculateApkSum())
            val newResult = Result.success(data)

            emit(newResult)
        }
    }

    private fun handleSuccess(appInfos: AppInfos): Result<AppSecondaryInfo> {
        val appPackageName = requireNotNull(_appPackageName) { "appPackageName is null" }

        return when (appInfos.isNotEmpty()) {
            true -> handleSuccess(appInfo = appInfos.firstOrNull { it.packageName == appPackageName })
            else -> Result.error(throwable = AppInfosError.internalError())
        }
    }

    private fun handleSuccess(appInfo: AppInfo?): Result<AppSecondaryInfo> {
        return when (appInfo) {
            null -> Result.error(throwable = AppInfosError.notFound())
            else -> Result.success(data = appInfo.toAppSecondaryInfo())
        }
    }

    private fun handleError(throwable: Throwable): Result<AppSecondaryInfo> {
        Log.w(TAG, throwable)
        return Result.error(throwable = AppInfosError.internalError())
    }

    private fun calculateApkSum(): ApkSum {
        TODO("Not yet implemented")
    }

// MARK: - Companion

    companion object {
        private val TAG = GetAppSecondaryInfoFeatureImpl::class.java.simpleName
    }

// MARK: - Variables

    private val _appInfosRepo: AppInfosRepo = appInfosRepo

    private var _appPackageName: AppPackageName? = null
}
