package com.example.presentation.screen.appinfos.secondary

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.commons.model.AppPackageName
import com.example.commons.result.Result
import com.example.feature.appinfos.api.GetAppSecondaryInfoFeature
import com.example.feature.appinfos.api.error.AppInfosFeatureError
import com.example.feature.appinfos.api.model.AppSecondaryInfo
import com.example.presentation.screen.AbstractViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class AppSecondaryInfoViewModel(
    appPackageName: AppPackageName,
    getAppSecondaryInfoFeature: GetAppSecondaryInfoFeature,
): AbstractViewModel<AppSecondaryInfoViewState, AppSecondaryInfoViewEvent>() {

// MARK: - Methods

    fun onLaunchAppClicked() {
        val event = AppSecondaryInfoViewEvent.launchApp()
        post(event)
    }

    fun onLaunchAppFailed() {
        val event = AppSecondaryInfoViewEvent.showAppLaunchUnavailableDialog()
        post(event)
    }

    fun onViewCreated() {
        launchGetAppSecondaryInfoJob()
    }

// MARK: - Private Methods

    private fun launchGetAppSecondaryInfoJob() {
        val jobActive = (_getAppSecondaryInfoJob?.isActive ?: false)

        if (!jobActive) {
            _getAppSecondaryInfoJob = _viewModelScope.launch {
                _getAppSecondaryInfoFeature.invoke(params = _appPackageName).collect { result ->
                    handleGetAppSecondaryInfoResult(result)
                }
            }
        }
    }

    private fun handleGetAppSecondaryInfoResult(result: Result<AppSecondaryInfo>) {
        result.fold(
            onSuccess = { appSecondaryInfo ->
                handleGetAppSecondaryInfoSuccess(appSecondaryInfo)
            },
            onError = { throwable ->
                Log.w(TAG, throwable)
                handleGetAppSecondaryInfoError(throwable)
            },
            onLoading = {
                // Do nothing
            },
        )
    }

    private fun handleGetAppSecondaryInfoSuccess(appSecondaryInfo: AppSecondaryInfo) {
        _appSecondaryInfo = appSecondaryInfo

        val state = AppSecondaryInfoViewState.content(appSecondaryInfo)
        switchTo(state)
    }

    private fun handleGetAppSecondaryInfoError(throwable: Throwable) {
        when (throwable) {
            AppInfosFeatureError.ApkHashCalculation -> handleApkHashCalculationError()
            AppInfosFeatureError.NotFound -> handleNotFoundError()
            else -> handleInternalError()
        }
    }

    private fun handleApkHashCalculationError() {

        val event = AppSecondaryInfoViewEvent.showApkHashCalculationErrorDialog()
        post(event)

        _appSecondaryInfo?.let { appSecondaryInfo ->
            val state = AppSecondaryInfoViewState.content(appSecondaryInfo, ignoreApkHash = true)
            switchTo(state)
        }
    }

    private fun handleNotFoundError() {

        val event = AppSecondaryInfoViewEvent.showAppNotFoundDialog()
        post(event)

        val state = AppSecondaryInfoViewState.dismiss()
        switchTo(state)
    }

    private fun handleInternalError() {
        val state = AppSecondaryInfoViewState.dismiss()
        switchTo(state)
    }

// MARK: - Companion

    companion object {
        private val TAG = AppSecondaryInfoViewModel::class.java.simpleName
    }

// MARK: - Variables

    private val _appPackageName: AppPackageName = appPackageName
    private val _getAppSecondaryInfoFeature: GetAppSecondaryInfoFeature = getAppSecondaryInfoFeature

    private inline val _viewModelScope: CoroutineScope
        get() = this.viewModelScope

    private var _appSecondaryInfo: AppSecondaryInfo? = null

    private var _getAppSecondaryInfoJob: Job? = null
}
