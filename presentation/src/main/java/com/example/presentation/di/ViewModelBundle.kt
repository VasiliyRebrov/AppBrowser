package com.example.presentation.di

import com.example.commons.di.KoinModuleBundle
import com.example.commons.model.AppPackageName
import com.example.feature.appinfos.api.GetAppPrimaryInfosFeature
import com.example.feature.appinfos.api.GetAppSecondaryInfoFeature
import com.example.feature.appinfos.api.UpdateAppInfosFeature
import com.example.presentation.provider.DrawableProvider
import com.example.presentation.screen.appinfos.primary.AppPrimaryInfosViewModel
import com.example.presentation.screen.appinfos.secondary.AppSecondaryInfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

internal object ViewModelBundle: KoinModuleBundle {

// MARK: - Properties

    override val module: Module = module {

        viewModel {
            AppPrimaryInfosViewModel(
                get<GetAppPrimaryInfosFeature>(),
                get<UpdateAppInfosFeature>(),
                get<DrawableProvider>(),
            )
        }

        viewModel { (packageName: AppPackageName) ->
            AppSecondaryInfoViewModel(
                packageName,
                get<GetAppSecondaryInfoFeature>(),
            )
        }
    }
}
