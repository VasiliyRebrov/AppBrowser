package com.example.appbrowser.di

import com.example.commons.di.AbstractKoinContainer
import com.example.commons.di.KoinModuleBundle
import com.example.commons.di.KoinModuleLoader
import com.example.feature.appinfos.api.di.container.FeatureAppInfosKoinContainer

object ApplicationKoinContainer: AbstractKoinContainer() {

// MARK: - Properties

    override val koinModuleBundles: List<KoinModuleBundle> = listOf()

    override val koinModuleLoaders: List<KoinModuleLoader> = listOf(
        FeatureAppInfosKoinContainer,
    )
}
