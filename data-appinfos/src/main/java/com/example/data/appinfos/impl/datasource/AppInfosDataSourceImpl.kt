package com.example.data.appinfos.impl.datasource

import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.util.Log
import com.example.commons.model.AppPackageName
import com.example.data.appinfos.api.model.AppInfo
import com.example.data.appinfos.impl.extensions.toAppInfo

internal class AppInfosDataSourceImpl(packageManager: PackageManager): AppInfosDataSource {

// MARK: - Methods

    override fun getAppInfo(appPackageName: AppPackageName): AppInfo? {
        return _packageManager.getLaunchIntentForPackage(appPackageName.rawValue)?.let {
            try {
                _packageManager
                    .getApplicationInfo(appPackageName.rawValue, PackageManager.GET_META_DATA)
                    .toAppInfo(_packageManager)
            }
            catch (ex: NameNotFoundException) {
                Log.w(TAG, ex)
                null
            }
        }
    }

    override fun getAppInfos(): Map<AppPackageName, AppInfo> {
        return buildMap {
            _packageManager.getInstalledApplications(PackageManager.GET_META_DATA).forEach { applicationInfo ->
                _packageManager.getLaunchIntentForPackage(applicationInfo.packageName)?.let {
                    try {
                        val appInfo = applicationInfo.toAppInfo(_packageManager)
                        this[appInfo.packageName] = appInfo
                    }
                    catch (ex: NameNotFoundException) {
                        Log.w(TAG, ex)
                    }
                }
            }
        }
    }

// MARK: - Companion

    companion object {
        private val TAG = AppInfosDataSourceImpl::class.java.simpleName
    }

// MARK: - Variables

    private val _packageManager: PackageManager = packageManager
}
