package com.example.presentation.screen.appinfos.primary

import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.alias.OnItemClicked
import com.example.presentation.databinding.ItemAppPrimaryInfoBinding

internal class AppPrimaryInfoViewHolder(
    binding: ItemAppPrimaryInfoBinding,
    onItemClicked: OnItemClicked,
): RecyclerView.ViewHolder(binding.root) {

// MARK: - Methods

    fun bind(appPrimaryInfoItem: AppPrimaryInfoItem) {
        val (appName, _, appDrawable) = appPrimaryInfoItem

        _binding.textName.text = appName.rawValue
        _binding.imageIcon.setImageDrawable(appDrawable)

        _binding.root.setOnClickListener {
            _onItemClicked.invoke(appPrimaryInfoItem)
        }
    }

// MARK: - Variables

    private val _binding: ItemAppPrimaryInfoBinding = binding
    private val _onItemClicked: OnItemClicked = onItemClicked
}
