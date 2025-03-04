package com.example.data.appinfos.impl.di

import com.example.commons.di.KoinModuleBundle
import com.example.data.appinfos.api.repo.AppInfosRepo
import com.example.data.appinfos.impl.datasource.AppInfosDataSource
import com.example.data.appinfos.impl.repo.AppInfosRepoImpl
import org.koin.core.module.Module
import org.koin.dsl.module

internal object RepoBundle: KoinModuleBundle {

// MARK: - Properties

    override val module: Module = module {
        single<AppInfosRepo> {
            AppInfosRepoImpl(
                get<AppInfosDataSource>(),
            )
        }
    }
}
