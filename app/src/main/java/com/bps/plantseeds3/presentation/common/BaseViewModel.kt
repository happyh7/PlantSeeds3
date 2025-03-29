package com.bps.plantseeds3.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<State, Event> : ViewModel() {
    private val _state = MutableStateFlow<State>(createInitialState())
    val state: StateFlow<State> = _state.asStateFlow()

    protected abstract fun createInitialState(): State

    protected fun updateState(update: (State) -> State) {
        _state.value = update(_state.value)
    }

    protected fun launchWithStateUpdate(
        block: suspend () -> Unit,
        onError: (Exception) -> Unit = { e -> handleError(e) }
    ) {
        viewModelScope.launch {
            try {
                block()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    protected open fun handleError(error: Exception) {
        // Implementeras av underklasser
    }

    abstract fun onEvent(event: Event)
} 