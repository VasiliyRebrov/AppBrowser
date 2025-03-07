package com.example.presentation.screen.appinfos.secondary

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.commons.model.AppPackageName
import com.example.commons.model.AppVersion
import com.example.feature.appinfos.api.model.ApkHash
import com.example.presentation.R
import com.example.presentation.databinding.FragmentAppSecondaryInfoBinding
import com.example.presentation.provider.DrawableProvider
import com.example.presentation.screen.AbstractFragment
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

internal class AppSecondaryInfoFragment:
    AbstractFragment<FragmentAppSecondaryInfoBinding>(FragmentAppSecondaryInfoBinding::inflate) {

// MARK: - Methods

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        observeData()

        _viewModel.onViewCreated()
    }

// MARK: - Private Methods

    private fun initView() {

        val drawable = _drawableProvider.provide(_appPackageName)
        _binding.imageIcon.setImageDrawable(drawable)

        _binding.buttonLaunch.setOnClickListener { _viewModel.onLaunchAppClicked() }
    }

    private fun observeData() {
        _viewModel.viewEvent.observeEvent(this::onViewEventReceived)
        _viewModel.viewState.observeState(this::onViewStateChanged)
    }

    private fun onViewEventReceived(viewEvent: AppSecondaryInfoViewEvent) {
        when (viewEvent) {
            is AppSecondaryInfoViewEvent.LaunchApp -> launchApp()
            is AppSecondaryInfoViewEvent.ShowApkHashCalculationErrorDialog -> showApkHashCalculationErrorDialog()
            is AppSecondaryInfoViewEvent.ShowAppLaunchUnavailableDialog -> showAppLaunchUnavailableDialog()
            is AppSecondaryInfoViewEvent.ShowAppNotFoundDialog -> showAppNotFoundDialog()
        }
    }

    private fun onViewStateChanged(viewState: AppSecondaryInfoViewState) {
        when (viewState) {
            is AppSecondaryInfoViewState.Content -> handleContentState(viewState)
            is AppSecondaryInfoViewState.Dismiss -> handleDismissState()
        }
    }

    private fun launchApp() {
        try {
            val intent = requireContext().packageManager
                .getLaunchIntentForPackage(_appPackageName.rawValue)

            when (intent) {
                null -> _viewModel.onLaunchAppFailed()
                else -> startActivity(intent)
            }
        }
        catch (ex: Exception) {
            _viewModel.onLaunchAppFailed()
        }
    }

    private fun showApkHashCalculationErrorDialog() {
        showDialog(
            titleInfo = R.string.text_error_of_apk_control_sum_calculating,
            duration = Snackbar.LENGTH_SHORT,
        )
    }

    private fun showAppLaunchUnavailableDialog() {
        showDialog(
            titleInfo = R.string.text_app_cannot_be_launched,
            duration = Snackbar.LENGTH_SHORT,
        )
    }

    private fun showAppNotFoundDialog() {
        showDialog(
            titleInfo = R.string.text_app_not_found,
            duration = Snackbar.LENGTH_SHORT,
        )
    }

    private fun handleContentState(viewState: AppSecondaryInfoViewState.Content) {
        val (_, appPackageName, appVersion, apkHash) = viewState.appSecondaryInfo
        val ignoreApkHash = viewState.ignoreApkHash

        handleAppVersionContentState(appVersion)
        handleAppPackageNameContentState(appPackageName)
        handleApkHashContentState(apkHash, ignoreApkHash)
    }

    private fun handleDismissState() {
        findNavController().navigateUp()
    }

    private fun handleAppVersionContentState(appVersion: AppVersion) {
        _binding.textVersionValue.text = appVersion.rawValue
    }

    private fun handleAppPackageNameContentState(appPackageName: AppPackageName) {
        _binding.textPackageNameValue.text = appPackageName.rawValue
    }

    private fun handleApkHashContentState(apkHash: ApkHash?, ignoreApkHash: Boolean) {
        if (ignoreApkHash) {
            _binding.textApkHash.visibility = View.INVISIBLE
            _binding.textApkHashValue.visibility = View.GONE
            _binding.progressApkHash.visibility = View.GONE
        }
        else if (apkHash == null) {
            _binding.textApkHash.visibility = View.INVISIBLE
            _binding.textApkHashValue.visibility = View.GONE
            _binding.progressApkHash.visibility = View.VISIBLE
        }
        else {
            _binding.textApkHash.visibility = View.VISIBLE
            _binding.textApkHashValue.visibility = View.VISIBLE
            _binding.progressApkHash.visibility = View.GONE

            val algorithmValue = apkHash.algorithm.rawValue
            val apkHashValue = apkHash.rawValue

            _binding.textApkHash.text = getString(R.string.text_apk_control_sum, algorithmValue)
            _binding.textApkHashValue.text = apkHashValue
        }
    }

// MARK: - Variables

    private val _arguments: AppSecondaryInfoFragmentArgs by navArgs()

    private val _drawableProvider: DrawableProvider by inject<DrawableProvider>()

    private val _viewModel: AppSecondaryInfoViewModel by viewModel {
        parametersOf(_appPackageName)
    }

    private inline val _appPackageName: AppPackageName
        get() = _arguments.appPackageName

    private inline val _binding: FragmentAppSecondaryInfoBinding
        get() = this.binding
}
