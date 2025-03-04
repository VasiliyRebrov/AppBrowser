package com.example.presentation.screen.appinfos.primary

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.presentation.R
import com.example.presentation.databinding.FragmentAppPrimaryInfosBinding
import com.example.presentation.screen.AbstractFragment
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class AppPrimaryInfosFragment:
    AbstractFragment<FragmentAppPrimaryInfosBinding>(FragmentAppPrimaryInfosBinding::inflate) {

// MARK: - Methods

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        observeData()

        _viewModel.onViewCreated()
    }

// MARK: - Private Methods

    private fun initView() {
        _binding.recycler.adapter = _adapter
        _binding.recycler.setHasFixedSize(true)
    }

    private fun observeData() {
        _viewModel.viewEvent.observeEvent(this::onViewEventReceived)
        _viewModel.viewState.observeState(this::onViewStateChanged)
    }

    private fun onViewEventReceived(viewEvent: AppPrimaryInfosViewEvent) {
        when (viewEvent) {
            is AppPrimaryInfosViewEvent.ShowAppSecondaryInfoScreen -> showAppSecondaryInfoScreen(viewEvent)
        }
    }

    private fun onViewStateChanged(viewState: AppPrimaryInfosViewState) {
        when (viewState) {
            is AppPrimaryInfosViewState.Content -> handleContentState(viewState)
            is AppPrimaryInfosViewState.ForceRefreshDialog -> handleForceRefreshDialogState()
            is AppPrimaryInfosViewState.Loading -> handleLoadingState()
        }
    }

    private fun showAppSecondaryInfoScreen(viewEvent: AppPrimaryInfosViewEvent.ShowAppSecondaryInfoScreen) {
        val (appName, appPackageName) = viewEvent.appPrimaryInfoItem

        val directions = AppPrimaryInfosFragmentDirections
            .actionAppPrimaryInfosFragmentToAppSecondaryInfoFragment(appName, appPackageName)

        findNavController().navigate(directions)
    }

    private fun handleContentState(viewState: AppPrimaryInfosViewState.Content) {
        _binding.progress.visibility = View.GONE

        val appPrimaryInfoItems = viewState.appPrimaryInfoItems
        _adapter.submitList(appPrimaryInfoItems)
    }

    private fun handleForceRefreshDialogState() {
        _binding.progress.visibility = View.GONE
        _adapter.submitList(emptyList())

        showForceRefreshDialog()
    }

    private fun handleLoadingState() {
        _binding.progress.visibility = View.VISIBLE
    }

    private fun showForceRefreshDialog() {
        showDialog(
            titleInfo = R.string.text_internal_error_occurred,
            actionInfo = R.string.text_retry,
            duration = Snackbar.LENGTH_INDEFINITE,
            action = _viewModel::onRetryClicked,
        )
    }

// MARK: - Variables

    private val _viewModel: AppPrimaryInfosViewModel by viewModel()

    private val _adapter: AppPrimaryInfosAdapter by lazy {
        AppPrimaryInfosAdapter(_viewModel::onItemClicked)
    }

    private inline val _binding: FragmentAppPrimaryInfosBinding
        get() = this.binding
}
