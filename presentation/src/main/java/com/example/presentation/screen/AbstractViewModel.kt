package com.example.presentation.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.presentation.livedata.MutableOnceCommandLiveData
import com.example.presentation.livedata.OnceCommandLiveData

internal abstract class AbstractViewModel<TState: ViewState, TEvent: ViewEvent>: ViewModel() {

// MARK: - Properties

    val viewEvent: OnceCommandLiveData<TEvent>
        get() = _viewEvent

    val viewState: LiveData<TState>
        get() = _viewState


// MARK: - Methods

    protected fun getViewEvent(): MutableOnceCommandLiveData<TEvent> {
        return _viewEvent
    }

    protected fun getViewState(): MutableLiveData<TState> {
        return _viewState
    }

    protected fun post(event: TEvent) {
        _viewEvent.postCommand(event)
    }

    protected fun switchTo(state: TState) {
        _viewState.postValue(state)
    }

// MARK: - Variables

    private val _viewEvent = MutableOnceCommandLiveData<TEvent>()

    private val _viewState = MutableLiveData<TState>()
}
