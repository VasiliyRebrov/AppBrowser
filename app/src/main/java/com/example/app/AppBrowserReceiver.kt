package com.example.app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import com.example.commons.model.AppPackageName

internal class AppBrowserReceiver(
    onAppAdded: (AppPackageName) -> Unit,
    onAppRemoved: (AppPackageName) -> Unit,
): BroadcastReceiver() {

// MARK: - Methods

    override fun onReceive(context: Context, intent: Intent) {

        val action = intent.action

        val appPackageName = intent.data
            ?.encodedSchemeSpecificPart
            ?.let { AppPackageName(it) }

        if (action != null && appPackageName != null) {
            when (action) {
                Intent.ACTION_PACKAGE_ADDED -> handleAppAdded(appPackageName)
                Intent.ACTION_PACKAGE_REMOVED -> handleAppRemoved(appPackageName, intent)
            }
        }
    }

    fun register(context: Context) {

        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_PACKAGE_ADDED)
            addAction(Intent.ACTION_PACKAGE_REMOVED)
            addDataScheme(DataScheme.PACKAGE)
        }

        context.registerReceiver(this, filter)
    }

    fun unregister(context: Context) {
        context.unregisterReceiver(this)
    }

// MARK: - Private Methods

    private fun handleAppAdded(appPackageName: AppPackageName) {
        Log.d(TAG, "${appPackageName} was added.")
        _onAppAdded.invoke(appPackageName)
    }

    private fun handleAppRemoved(appPackageName: AppPackageName, intent: Intent) {
        val replacing = intent.getBooleanExtra(Intent.EXTRA_REPLACING, false)

        if (!replacing) {
            Log.d(TAG, "${appPackageName} was removed.")
            _onAppRemoved.invoke(appPackageName)
        }
    }

// MARK: - Constants

    private object DataScheme {
        const val PACKAGE = "package"
    }

// MARK: - Companion

    companion object {
        private val TAG = AppBrowserReceiver::class.java.simpleName
    }

// MARK: - Variables

    private val _onAppAdded: (AppPackageName) -> Unit = onAppAdded
    private val _onAppRemoved: (AppPackageName) -> Unit = onAppRemoved
}
