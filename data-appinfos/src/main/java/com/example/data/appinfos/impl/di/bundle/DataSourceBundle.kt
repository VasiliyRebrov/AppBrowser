package com.example.data.appinfos.impl.di.bundle

import android.content.Context
import com.example.commons.di.KoinModuleBundle
import com.example.data.appinfos.impl.datasource.AppInfosDataSource
import com.example.data.appinfos.impl.datasource.AppInfosDataSourceImpl
import org.koin.core.module.Module
import org.koin.dsl.module

internal object DataSourceBundle: KoinModuleBundle {

// MARK: - Properties

    override val module: Module = module {
        single<AppInfosDataSource> {
            AppInfosDataSourceImpl(
                get<Context>().packageManager,
            )
        }
    }
}
