package com.example.commons.di

import org.koin.core.context.loadKoinModules

abstract class AbstractKoinContainer: KoinModuleLoader {

// MARK: - Properties

    protected abstract val koinModuleBundles: List<KoinModuleBundle>

    protected open val koinModuleLoaders: List<KoinModuleLoader> = emptyList()

// MARK: - Methods

    override fun loadModules() {

        this.koinModuleLoaders.forEach { loader ->
            loader.loadModules()
        }

        this.koinModuleBundles.forEach { bundle ->
            loadKoinModules(bundle.module)
        }
    }
}
