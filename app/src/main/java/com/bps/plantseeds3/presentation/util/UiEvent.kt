package com.bps.plantseeds3.presentation.util

sealed class UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent()
    object NavigateUp : UiEvent()
    object SaveSeed : UiEvent()
} 