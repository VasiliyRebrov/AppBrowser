package com.example.commons.di

import org.koin.core.context.loadKoinModules
import java.util.concurrent.atomic.AtomicBoolean

abstract class AbstractKoinContainer: KoinModuleLoader {

// MARK: - Properties

    protected abstract val koinModuleBundles: List<KoinModuleBundle>

    protected open val koinModuleLoaders: List<KoinModuleLoader> = emptyList()

// MARK: - Methods

    override fun loadModules() {
        if (_bootScopeLoaded.compareAndSet(false, true)) {

            this.koinModuleLoaders.forEach { loader ->
                loader.loadModules()
            }

            this.koinModuleBundles.forEach { bundle ->
                loadKoinModules(bundle.module)
            }
        }
    }

// MARK: - Variables

    private val _bootScopeLoaded: AtomicBoolean = AtomicBoolean(false)
}
