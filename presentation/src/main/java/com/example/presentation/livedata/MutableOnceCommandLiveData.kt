package com.example.presentation.livedata

internal class MutableOnceCommandLiveData<T>: OnceCommandLiveData<T> {

// MARK: - Construction

    constructor(): super()

    constructor(value: T): super(value)

// MARK: - Methods

    fun postCommand(value: T) {
        postValue(DataHolder(value))
    }

    fun sendCommand(value: T) {
        setValue(DataHolder(value))
    }
}
