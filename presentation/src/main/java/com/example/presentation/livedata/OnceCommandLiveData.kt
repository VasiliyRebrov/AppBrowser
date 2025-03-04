package com.example.presentation.livedata

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.presentation.livedata.OnceCommandLiveData.DataHolder

internal abstract class OnceCommandLiveData<T>: LiveData<DataHolder<T>> {

// MARK: - Construction

    constructor(): super()

    constructor(value: T): super(DataHolder(value))

// MARK: - Methods

    @MainThread
    fun observe(owner: LifecycleOwner, action: (T) -> Unit) {
        this.value = null

        observe(owner, Observer { command ->
            command?.sendIfNotHandled(action)
        })
    }

// MARK: - Inner Types

    data class DataHolder<out T>(private val data: T) {

        fun sendIfNotHandled(action: (T) -> Unit) {
            if (_alreadyHandled.add(action.hashCode())) {
                action(this.data)
            }
        }

        private val _alreadyHandled = mutableSetOf<Int>()
    }
}
