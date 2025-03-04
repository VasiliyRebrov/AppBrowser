package com.example.presentation.di

import com.example.commons.di.AbstractKoinContainer
import com.example.commons.di.KoinModuleBundle
import com.example.commons.di.KoinModuleLoader
import com.example.feature.appinfos.api.di.FeatureAppInfosKoinContainer

object PresentationKoinContainer: AbstractKoinContainer() {

// MARK: - Properties

    override val koinModuleBundles: List<KoinModuleBundle> = listOf(
        ProviderBundle,
        ViewModelBundle,
    )

    override val koinModuleLoaders: List<KoinModuleLoader> = listOf(
        FeatureAppInfosKoinContainer,
    )
}
