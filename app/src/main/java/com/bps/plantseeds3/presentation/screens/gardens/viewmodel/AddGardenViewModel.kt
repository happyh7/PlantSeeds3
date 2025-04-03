package com.bps.plantseeds3.presentation.screens.gardens.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bps.plantseeds3.garden.domain.model.Garden
import com.bps.plantseeds3.garden.domain.repository.GardenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddGardenViewModel @Inject constructor(
    private val gardenRepository: GardenRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AddGardenUiState>(AddGardenUiState.Initial)
    val uiState: StateFlow<AddGardenUiState> = _uiState

    fun addGarden(name: String, location: String, description: String? = null, size: String? = null, soilType: String? = null) {
        viewModelScope.launch {
            _uiState.value = AddGardenUiState.Loading
            try {
                val garden = Garden(
                    name = name,
                    location = location,
                    description = description,
                    size = size,
                    soilType = soilType
                )
                gardenRepository.insertGarden(garden)
                _uiState.value = AddGardenUiState.Success
            } catch (e: Exception) {
                Log.e("AddGardenViewModel", "Error adding garden", e)
                _uiState.value = AddGardenUiState.Error(e.message ?: "Ett fel uppstod")
            }
        }
    }
}

sealed class AddGardenUiState {
    object Initial : AddGardenUiState()
    object Loading : AddGardenUiState()
    object Success : AddGardenUiState()
    data class Error(val message: String) : AddGardenUiState()
} 