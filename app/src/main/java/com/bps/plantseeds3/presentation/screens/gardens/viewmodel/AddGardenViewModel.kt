package com.bps.plantseeds3.presentation.screens.gardens.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bps.plantseeds3.domain.model.Garden
import com.bps.plantseeds3.domain.repository.GardenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class AddGardenViewModel @Inject constructor(
    private val gardenRepository: GardenRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AddGardenUiState>(AddGardenUiState.Initial)
    val uiState: StateFlow<AddGardenUiState> = _uiState

    fun addGarden(name: String, description: String, location: String) {
        viewModelScope.launch {
            try {
                _uiState.value = AddGardenUiState.Loading
                
                val garden = Garden(
                    id = "", // ID kommer att genereras av databasen
                    name = name,
                    description = description,
                    location = location,
                    createdAt = Instant.now(),
                    updatedAt = Instant.now()
                )
                
                gardenRepository.insertGarden(garden)
                _uiState.value = AddGardenUiState.Success
            } catch (e: Exception) {
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