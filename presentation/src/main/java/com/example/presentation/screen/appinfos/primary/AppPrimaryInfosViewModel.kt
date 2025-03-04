package com.example.presentation.screen.appinfos.primary

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.commons.result.Result
import com.example.feature.appinfos.api.GetAppPrimaryInfosFeature
import com.example.feature.appinfos.api.UpdateAppInfosFeature
import com.example.feature.appinfos.api.alias.AppPrimaryInfos
import com.example.presentation.extensions.toAppPrimaryInfoItems
import com.example.presentation.provider.DrawableProvider
import com.example.presentation.screen.AbstractViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.Collator
import java.util.Locale

internal class AppPrimaryInfosViewModel(
    getAppPrimaryInfosFeature: GetAppPrimaryInfosFeature,
    updateAppInfosFeature: UpdateAppInfosFeature,
    drawableProvider: DrawableProvider,
): AbstractViewModel<AppPrimaryInfosViewState, AppPrimaryInfosViewEvent>() {

// MARK: - Methods

    fun onItemClicked(appPrimaryInfoItem: AppPrimaryInfoItem) {
        val event = AppPrimaryInfosViewEvent.showAppSecondaryInfoScreen(appPrimaryInfoItem)
        post(event)
    }

    fun onRetryClicked() {
        updateAppInfosFeature()
    }

    fun onViewCreated() {
        launchGetAppPrimaryInfosJob()
    }

// MARK: - Private Methods

    private fun launchGetAppPrimaryInfosJob() {
        val jobActive = (_getAppPrimaryInfosJob?.isActive ?: false)

        if (!jobActive) {
            _getAppPrimaryInfosJob = _viewModelScope.launch {
                _getAppPrimaryInfosFeature.invoke(params = Unit).collect { result ->
                    handleGetAppPrimaryInfosResult(result)
                }
            }
        }
    }

    private fun updateAppInfosFeature() {
        _viewModelScope.launch {
            _updateAppInfosFeature.invoke(params = Unit)
        }
    }

    private suspend fun handleGetAppPrimaryInfosResult(result: Result<AppPrimaryInfos>) {
        result.fold(
            onSuccess = { appPrimaryInfos ->
                handleGetAppPrimaryInfosSuccess(appPrimaryInfos)
            },
            onError = { throwable ->
                Log.w(TAG, throwable)
                handleGetAppPrimaryInfosError()
            },
            onLoading = {
                handleGetAppPrimaryInfosLoading()
            },
        )
    }

    private suspend fun handleGetAppPrimaryInfosSuccess(appPrimaryInfos: AppPrimaryInfos) {

        val appPrimaryInfoItems = withContext(Dispatchers.IO) {
            appPrimaryInfos.toAppPrimaryInfoItems(_collator, _drawableProvider)
        }

        val state = AppPrimaryInfosViewState.content(appPrimaryInfoItems)
        switchTo(state)
    }

    private fun handleGetAppPrimaryInfosError() {
        val state = AppPrimaryInfosViewState.forceRefreshDialog()
        switchTo(state)
    }

    private fun handleGetAppPrimaryInfosLoading() {
        val state = AppPrimaryInfosViewState.loading()
        switchTo(state)
    }

// MARK: - Constants

    private object LocaleAttr {
        const val COUNTRY = "RU"
        const val LANGUAGE = "ru"
    }

// MARK: - Companion

    companion object {
        private val TAG = AppPrimaryInfosViewModel::class.java.simpleName
    }

// MARK: - Variables

    private val _getAppPrimaryInfosFeature: GetAppPrimaryInfosFeature = getAppPrimaryInfosFeature
    private val _updateAppInfosFeature: UpdateAppInfosFeature = updateAppInfosFeature
    private val _drawableProvider: DrawableProvider = drawableProvider

    private val _collator: Collator by lazy {
        val locale = Locale(LocaleAttr.LANGUAGE, LocaleAttr.COUNTRY)
        Collator.getInstance(locale)
    }

    private inline val _viewModelScope: CoroutineScope
        get() = this.viewModelScope

    private var _getAppPrimaryInfosJob: Job? = null
}
