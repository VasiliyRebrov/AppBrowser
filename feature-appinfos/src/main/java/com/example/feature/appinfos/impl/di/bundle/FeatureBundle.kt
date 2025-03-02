package com.example.feature.appinfos.impl.di.bundle

import com.example.commons.di.KoinModuleBundle
import com.example.data.appinfos.api.repo.AppInfosRepo
import com.example.feature.appinfos.api.GetAppPrimaryInfosFeature
import com.example.feature.appinfos.api.GetAppSecondaryInfoFeature
import com.example.feature.appinfos.api.UpdateAppInfosFeature
import com.example.feature.appinfos.impl.GetAppPrimaryInfosFeatureImpl
import com.example.feature.appinfos.impl.GetAppSecondaryInfoFeatureImpl
import com.example.feature.appinfos.impl.UpdateAppInfosFeatureImpl
import org.koin.core.module.Module
import org.koin.dsl.module

internal object FeatureBundle: KoinModuleBundle {

// MARK: - Properties

    override val module: Module = module {

        single<GetAppPrimaryInfosFeature> {
            GetAppPrimaryInfosFeatureImpl(
                get<AppInfosRepo>(),
            )
        }

        single<GetAppSecondaryInfoFeature> {
            GetAppSecondaryInfoFeatureImpl(
                get<AppInfosRepo>(),
            )
        }

        single<UpdateAppInfosFeature> {
            UpdateAppInfosFeatureImpl(
                get<AppInfosRepo>(),
            )
        }
    }
}
