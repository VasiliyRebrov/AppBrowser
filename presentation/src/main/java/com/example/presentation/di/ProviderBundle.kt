package com.example.presentation.di

import android.content.Context
import com.example.commons.di.KoinModuleBundle
import com.example.presentation.provider.DrawableProvider
import com.example.presentation.provider.DrawableProviderImpl
import org.koin.core.module.Module
import org.koin.dsl.module

internal object ProviderBundle: KoinModuleBundle {

// MARK: - Properties

    override val module: Module = module {
        single<DrawableProvider> {
            DrawableProviderImpl(
                get<Context>(),
            )
        }
    }
}
