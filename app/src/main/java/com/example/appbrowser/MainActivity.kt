package com.example.appbrowser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appbrowser.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity() {

// MARK: - Methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(_binding.root)
    }

// MARK: - Variables

    private val _binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(this.layoutInflater)
    }
}
