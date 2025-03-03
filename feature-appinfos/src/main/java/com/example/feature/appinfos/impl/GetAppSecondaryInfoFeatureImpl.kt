package com.example.feature.appinfos.impl

import android.util.Log
import com.example.commons.model.AppPackageName
import com.example.commons.result.Result
import com.example.data.appinfos.api.error.AppInfosDataError
import com.example.data.appinfos.api.model.AppInfo
import com.example.data.appinfos.api.repo.AppInfosRepo
import com.example.feature.appinfos.api.GetAppSecondaryInfoFeature
import com.example.feature.appinfos.api.error.AppInfosFeatureError
import com.example.feature.appinfos.api.model.ApkSum
import com.example.feature.appinfos.api.model.AppSecondaryInfo
import com.example.feature.appinfos.impl.extensions.toAppInfosFeatureError
import com.example.feature.appinfos.impl.extensions.toAppSecondaryInfo
import com.example.feature.commons.impl.FlowFeatureImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform

internal class GetAppSecondaryInfoFeatureImpl(
    appInfosRepo: AppInfosRepo,
): FlowFeatureImpl<AppPackageName, AppSecondaryInfo>(),
   GetAppSecondaryInfoFeature {

// MARK: - Methods

    override fun execute(params: AppPackageName): Flow<Result<AppSecondaryInfo>> {
        return _appInfosRepo.getAppInfoPublisher(appPackageName = params)
            .map(this::transform)
            .transform { result -> publish(result) }
    }

// MARK: - Private Methods

    private fun transform(result: Result<AppInfo>): Result<AppSecondaryInfo> {
        return result.fold(
            onSuccess = { appInfo ->
                Result.success(data = appInfo.toAppSecondaryInfo())
            },
            onError = { th ->
                Log.w(TAG, th)
                Result.error(th = mapToAppInfosFeatureError(th))
            },
        )
    }

    private suspend fun FlowCollector<Result<AppSecondaryInfo>>.publish(result: Result<AppSecondaryInfo>) {
        emit(result)

        if (result is Result.Success) {
            val newData = result.data.copy(apkSum = calculateApkSum())
            val newResult = Result.success(newData)

            emit(newResult)
        }
    }

    private fun mapToAppInfosFeatureError(th: Throwable): AppInfosFeatureError {
        return when (th) {
            is AppInfosDataError -> th.toAppInfosFeatureError()
            else -> AppInfosFeatureError.internalError()
        }
    }

    private fun calculateApkSum(): ApkSum {
        TODO()
    }

// MARK: - Companion

    companion object {
        private val TAG = GetAppSecondaryInfoFeatureImpl::class.java.simpleName
    }

// MARK: - Variables

    private val _appInfosRepo: AppInfosRepo = appInfosRepo
}
