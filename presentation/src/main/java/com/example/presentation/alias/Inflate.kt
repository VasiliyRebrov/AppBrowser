package com.example.presentation.alias

import android.view.LayoutInflater
import android.view.ViewGroup

internal typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T
