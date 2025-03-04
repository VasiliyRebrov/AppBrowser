package com.example.presentation.screen.appinfos

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.commons.model.AppName
import com.example.presentation.R
import com.example.presentation.databinding.ActivityAppInfosBinding

class AppInfosActivity: AppCompatActivity() {

// MARK: - Methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(_binding.root)
        initView()
    }

    override fun onSupportNavigateUp(): Boolean {
        return _navController.navigateUp() || super.onSupportNavigateUp()
    }

// MARK: - Private Methods

    private fun initView() {
        setSupportActionBar(_binding.toolbar)

        _navController.addOnDestinationChangedListener { _, destination, arguments ->
            when (destination.id) {
                R.id.appPrimaryInfosFragment -> handlePrimaryDestination()
                R.id.appSecondaryInfoFragment -> handleSecondaryDestination(arguments)
            }
        }
    }

    private fun handlePrimaryDestination() {
        _supportActionBar?.let { supportActionBar ->
            supportActionBar.title = getString(R.string.text_app_browser)
            supportActionBar.setDisplayHomeAsUpEnabled(false)
        }
    }

    private fun handleSecondaryDestination(arguments: Bundle?) {
        _supportActionBar?.let { supportActionBar ->

            val appName = arguments?.getParcelable<AppName>(Key.APP_NAME)
            appName?.let { supportActionBar.title = appName.rawValue }

            supportActionBar.setDisplayHomeAsUpEnabled(true)
        }
    }

// MARK: - Constants

    private object Key {
        const val APP_NAME = "appName"
    }

// MARK: - Variables

    private val _binding: ActivityAppInfosBinding by lazy {
        ActivityAppInfosBinding.inflate(_layoutInflater)
    }

    private val _navController: NavController by lazy {
        val navHostFragment = _supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navHostFragment.navController
    }

    private inline val _layoutInflater: LayoutInflater
        get() = this.layoutInflater

    private inline val _supportActionBar: ActionBar?
        get() = this.supportActionBar

    private inline val _supportFragmentManager: FragmentManager
        get() = this.supportFragmentManager
}
