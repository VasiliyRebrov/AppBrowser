package com.example.presentation.provider

import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.example.commons.model.AppPackageName
import com.example.presentation.R

internal class DrawableProviderImpl(context: Context): DrawableProvider {

// MARK: - Methods

    override fun provide(appPackageName: AppPackageName): Drawable {

        val drawable = try {
            _packageManager.getApplicationIcon(appPackageName.rawValue)
        }
        catch (ex: NameNotFoundException) {
            ContextCompat.getDrawable(_context, R.drawable.ic_launcher_foreground)
        }

        requireNotNull(drawable) { "drawable is null" }
        return drawable
    }

// MARK: - Variables

    private val _context: Context = context
    private val _packageManager: PackageManager = context.packageManager
}
