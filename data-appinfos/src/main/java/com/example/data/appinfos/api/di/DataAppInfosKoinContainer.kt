package com.example.data.appinfos.api.di

import com.example.commons.di.AbstractKoinContainer
import com.example.commons.di.KoinModuleBundle
import com.example.data.appinfos.impl.di.DataSourceBundle
import com.example.data.appinfos.impl.di.RepoBundle

object DataAppInfosKoinContainer: AbstractKoinContainer() {

// MARK: - Properties

    override val koinModuleBundles: List<KoinModuleBundle> = listOf(
        DataSourceBundle,
        RepoBundle,
    )
}
