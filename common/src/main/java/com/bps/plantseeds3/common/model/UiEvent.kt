package com.bps.plantseeds3.common.model

sealed class UiEvent {
    data class ShowSnackbar(
        val message: String,
        val action: String? = null
    ) : UiEvent()
    
    data class Navigate(val route: String) : UiEvent()
    
    object NavigateUp : UiEvent()
    
    data class ShowDialog(
        val title: String,
        val message: String,
        val confirmText: String = "OK",
        val dismissText: String? = "Avbryt",
        val onConfirm: () -> Unit,
        val onDismiss: (() -> Unit)? = null
    ) : UiEvent()
    
    data class ShowLoading(val isLoading: Boolean) : UiEvent()
} 