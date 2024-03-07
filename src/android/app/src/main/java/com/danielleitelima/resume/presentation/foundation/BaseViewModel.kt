package com.danielleitelima.resume.presentation.foundation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielleitelima.resume.domain.foundation.CFlow
import com.danielleitelima.resume.domain.foundation.CMutableStateFlow
import com.danielleitelima.resume.domain.foundation.CStateFlow
import com.danielleitelima.resume.domain.foundation.wrap
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

interface ViewEvent
interface ViewState
interface ViewSideEffect

abstract class BaseViewModel<Event : ViewEvent, UiState : ViewState, Effect : ViewSideEffect> :
    ViewModel() {
    private val initialState: UiState by lazy { setInitialState() }
    abstract fun setInitialState(): UiState

    private val _state: CMutableStateFlow<UiState> by lazy { CMutableStateFlow(initialState) }
    val state: CStateFlow<UiState> by lazy { _state }

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()

    private val _effect: Channel<Effect> = Channel()
    val effect: CFlow<Effect> = _effect.receiveAsFlow().wrap()

    init {
        subscribeToEvents()
    }

    fun setEvent(event: Event) {
        viewModelScope.launch { _event.emit(event) }
    }

    protected fun setState(reducer: UiState.() -> UiState) {
        val newState = state.value.reducer()
        _state.value = newState
    }

    private fun subscribeToEvents() {
        viewModelScope.launch {
            _event.collect {
                handleEvents(it)
            }
        }
    }

    protected abstract fun handleEvents(event: Event)

    protected fun setEffect(effect: Effect) {
        viewModelScope.launch { _effect.send(effect) }
    }
}
