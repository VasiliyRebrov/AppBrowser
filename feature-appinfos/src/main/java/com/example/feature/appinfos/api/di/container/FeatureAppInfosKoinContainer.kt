package com.example.feature.appinfos.api.di.container

import com.example.commons.di.AbstractKoinContainer
import com.example.commons.di.KoinModuleBundle
import com.example.commons.di.KoinModuleLoader
import com.example.data.appinfos.api.di.container.DataAppInfosKoinContainer
import com.example.feature.appinfos.impl.di.bundle.FeatureBundle

object FeatureAppInfosKoinContainer: AbstractKoinContainer() {

// MARK: - Properties

    override val koinModuleBundles: List<KoinModuleBundle> = listOf(
        FeatureBundle,
    )

    override val koinModuleLoaders: List<KoinModuleLoader> = listOf(
        DataAppInfosKoinContainer,
    )
}
