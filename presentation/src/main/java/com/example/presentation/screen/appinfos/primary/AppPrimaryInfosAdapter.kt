package com.example.presentation.screen.appinfos.primary

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.presentation.alias.OnItemClicked
import com.example.presentation.databinding.ItemAppPrimaryInfoBinding
import com.example.presentation.utils.createBinding

internal class AppPrimaryInfosAdapter(onItemClicked: OnItemClicked):
    ListAdapter<AppPrimaryInfoItem, AppPrimaryInfoViewHolder>(AppPrimaryInfosDiffCallback()) {

// MARK: - Methods

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppPrimaryInfoViewHolder {
        val binding = createBinding(parent, ItemAppPrimaryInfoBinding::inflate)
        return AppPrimaryInfoViewHolder(binding, _onItemClicked)
    }

    override fun onBindViewHolder(holder: AppPrimaryInfoViewHolder, position: Int) {
        val appPrimaryInfoItem = getItem(position)
        holder.bind(appPrimaryInfoItem)
    }

// MARK: - Variables

    private val _onItemClicked: OnItemClicked = onItemClicked
}
