package com.bps.plantseeds3.presentation.screens.gardens.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bps.plantseeds3.common.model.Resource
import com.bps.plantseeds3.domain.model.Garden
import com.bps.plantseeds3.domain.repository.GardenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GardensViewModel @Inject constructor(
    private val gardenRepository: GardenRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<GardensUiState>(GardensUiState.Loading)
    val uiState: StateFlow<GardensUiState> = _uiState.asStateFlow()

    init {
        loadGardens()
    }

    private fun loadGardens() {
        viewModelScope.launch {
            gardenRepository.getGardens().collect { result ->
                when (result) {
                    is Resource.Success -> _uiState.value = GardensUiState.Success(result.data)
                    is Resource.Error -> _uiState.value = GardensUiState.Error(result.message)
                    is Resource.Loading -> _uiState.value = GardensUiState.Loading
                }
            }
        }
    }
}

sealed class GardensUiState {
    object Loading : GardensUiState()
    data class Success(val gardens: List<Garden>) : GardensUiState()
    data class Error(val message: String) : GardensUiState()
} 