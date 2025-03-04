package com.example.presentation.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.viewbinding.ViewBinding
import com.example.presentation.alias.ActionBlock
import com.example.presentation.alias.Inflate
import com.example.presentation.livedata.OnceCommandLiveData
import com.google.android.material.snackbar.Snackbar

internal abstract class AbstractFragment<TBinding: ViewBinding>(inflate: Inflate<TBinding>): Fragment() {

// MARK: - Properties

    protected val binding: TBinding
        get() = requireNotNull(_binding) { "binding is null" }

// MARK: - Methods

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        super.onCreateView(inflater, container, savedInstanceState)

        _binding = _inflate.invoke(inflater, container, false)
        return binding.root
    }

    protected fun <T> OnceCommandLiveData<T>.observeEvent(action: (T) -> Unit) {
        observe(_lifecycleOwner, action)
    }

    protected fun <T> LiveData<T>.observeState(action: (T) -> Unit) {
        observe(_lifecycleOwner, action)
    }

    protected fun showDialog(
        @StringRes titleInfo: Int,
        @StringRes actionInfo: Int = 0,
        duration: Int,
        action: ActionBlock? = null,
    ) {

        val snackbar = Snackbar.make(requireView(), titleInfo, duration)

        if (actionInfo != 0 && action != null) {
            snackbar.setAction(actionInfo) { action.invoke() }
        }

        snackbar.show()
    }

// MARK: - Variables

    private val _inflate: Inflate<TBinding> = inflate

    private inline val _lifecycleOwner: LifecycleOwner
        get() = this.viewLifecycleOwner

    private var _binding: TBinding? = null
}
