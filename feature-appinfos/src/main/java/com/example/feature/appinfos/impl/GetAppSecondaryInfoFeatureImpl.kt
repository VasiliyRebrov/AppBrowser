package com.example.feature.appinfos.impl

import android.util.Log
import com.example.commons.model.AppPackageName
import com.example.commons.result.Result
import com.example.data.appinfos.api.error.AppInfosDataError
import com.example.data.appinfos.api.model.ApkPath
import com.example.data.appinfos.api.model.AppInfo
import com.example.data.appinfos.api.repo.AppInfosRepo
import com.example.feature.appinfos.api.GetAppSecondaryInfoFeature
import com.example.feature.appinfos.api.error.AppInfosFeatureError
import com.example.feature.appinfos.api.model.Algorithm
import com.example.feature.appinfos.api.model.ApkHash
import com.example.feature.appinfos.api.model.AppSecondaryInfo
import com.example.feature.appinfos.impl.extensions.toAppInfosFeatureError
import com.example.feature.appinfos.impl.extensions.toAppSecondaryInfo
import com.example.feature.appinfos.impl.util.HashUtil
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
                _apkPath = appInfo.apkPath
                Result.success(data = appInfo.toAppSecondaryInfo())
            },
            onError = { throwable ->
                Log.w(TAG, throwable)
                Result.error(throwable = mapToAppInfosFeatureError(throwable))
            },
            onLoading = {
                Result.loading()
            },
        )
    }

    private suspend fun FlowCollector<Result<AppSecondaryInfo>>.publish(result: Result<AppSecondaryInfo>) {
        emit(result)

        if (result is Result.Success) {
            val apkHash = calculateApkHash()

            val newResult = if (apkHash != null) {
                val newData = result.data.copy(apkHash = apkHash)
                Result.success(newData)
            }
            else {
                Result.error(throwable = AppInfosFeatureError.apkHashCalculation())
            }

            emit(newResult)
        }
    }

    private fun mapToAppInfosFeatureError(throwable: Throwable): AppInfosFeatureError {
        return when (throwable) {
            is AppInfosDataError -> throwable.toAppInfosFeatureError()
            else -> AppInfosFeatureError.internal()
        }
    }

    private fun calculateApkHash(): ApkHash? {

        val apkPath = requireNotNull(_apkPath) { "apkPath is null" }
        val hashString = HashUtil.sha256HashString(path = apkPath.rawValue)

        val apkHash = hashString?.let {
            ApkHash(rawValue = hashString, algorithm = Algorithm.SHA_256)
        }

        return apkHash
    }

// MARK: - Companion

    companion object {
        private val TAG = GetAppSecondaryInfoFeatureImpl::class.java.simpleName
    }

// MARK: - Variables

    private val _appInfosRepo: AppInfosRepo = appInfosRepo

    private var _apkPath: ApkPath? = null
}
