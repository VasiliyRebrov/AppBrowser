package com.example.presentation.screen.appinfos.primary

import androidx.recyclerview.widget.DiffUtil

internal class AppPrimaryInfosDiffCallback: DiffUtil.ItemCallback<AppPrimaryInfoItem>() {

// MARK: - Methods

    override fun areItemsTheSame(oldItem: AppPrimaryInfoItem, newItem: AppPrimaryInfoItem): Boolean {
        return (oldItem.packageName == newItem.packageName)
    }

    override fun areContentsTheSame(oldItem: AppPrimaryInfoItem, newItem: AppPrimaryInfoItem): Boolean {
        return (oldItem.name == newItem.name)
    }
}
