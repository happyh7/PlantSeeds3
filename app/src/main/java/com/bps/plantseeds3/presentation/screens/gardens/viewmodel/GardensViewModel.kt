package com.bps.plantseeds3.presentation.screens.gardens.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bps.plantseeds3.garden.domain.model.Garden
import com.bps.plantseeds3.garden.domain.repository.GardenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GardensViewModel @Inject constructor(
    private val gardenRepository: GardenRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<GardensUiState>(GardensUiState.Loading)
    val uiState: StateFlow<GardensUiState> = _uiState

    init {
        observeGardens()
    }

    private fun observeGardens() {
        gardenRepository.getGardens()
            .onEach { gardens ->
                _uiState.value = GardensUiState.Success(gardens)
            }
            .catch { error ->
                Log.e("GardensViewModel", "Error observing gardens", error)
                _uiState.value = GardensUiState.Error(error.message ?: "Ett fel uppstod")
            }
            .launchIn(viewModelScope)
    }
}

sealed class GardensUiState {
    object Loading : GardensUiState()
    data class Success(val gardens: List<Garden>) : GardensUiState()
    data class Error(val message: String) : GardensUiState()
} 