package com.bps.plantseeds3.presentation.screens.plants.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bps.plantseeds3.domain.model.Plant
import com.bps.plantseeds3.domain.repository.PlantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class AddPlantViewModel @Inject constructor(
    private val plantRepository: PlantRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AddPlantUiState>(AddPlantUiState.Initial)
    val uiState: StateFlow<AddPlantUiState> = _uiState

    fun addPlant(name: String, species: String, description: String) {
        viewModelScope.launch {
            try {
                _uiState.value = AddPlantUiState.Loading
                
                val plant = Plant(
                    id = "", // ID kommer att genereras av databasen
                    name = name,
                    species = species,
                    description = description,
                    gardenId = null,
                    lastWatered = null,
                    nextWatering = null,
                    createdAt = Instant.now(),
                    updatedAt = Instant.now()
                )
                
                plantRepository.insertPlant(plant)
                _uiState.value = AddPlantUiState.Success
            } catch (e: Exception) {
                _uiState.value = AddPlantUiState.Error(e.message ?: "Ett fel uppstod")
            }
        }
    }
}

sealed class AddPlantUiState {
    object Initial : AddPlantUiState()
    object Loading : AddPlantUiState()
    object Success : AddPlantUiState()
    data class Error(val message: String) : AddPlantUiState()
} 