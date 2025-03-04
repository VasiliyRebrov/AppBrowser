package com.example.presentation.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.presentation.alias.Inflate

// MARK: - Methods

internal fun <VB> createBinding(viewGroup: ViewGroup, inflate: Inflate<VB>): VB {
    return inflate.invoke(LayoutInflater.from(viewGroup.context), viewGroup, false)
}
