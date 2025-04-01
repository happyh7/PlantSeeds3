package com.bps.plantseeds3.presentation.viewmodel.plants

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bps.plantseeds3.common.model.Resource
import com.bps.plantseeds3.domain.model.Plant
import com.bps.plantseeds3.domain.repository.PlantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

private const val TAG = "AddPlantViewModel"

sealed class AddPlantUiState {
    object Loading : AddPlantUiState()
    object Success : AddPlantUiState()
    data class Error(val message: String) : AddPlantUiState()
}

@HiltViewModel
class AddPlantViewModel @Inject constructor(
    private val plantRepository: PlantRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AddPlantUiState>(AddPlantUiState.Loading)
    val uiState: StateFlow<AddPlantUiState> = _uiState.asStateFlow()

    private var _gardenId: String? = null
    val gardenId: String? get() = _gardenId

    fun setGardenId(gardenId: String) {
        Log.d(TAG, "setGardenId: $gardenId")
        _gardenId = gardenId
    }

    fun addPlant(
        name: String,
        species: String,
        description: String,
        nextWateringDays: Int
    ) {
        viewModelScope.launch {
            try {
                Log.d(TAG, "addPlant: Starting to add plant for garden ${_gardenId}")
                val gardenId = _gardenId ?: throw IllegalStateException("Ingen trädgård vald")
                
                val plant = Plant(
                    id = Instant.now().toString(), // Temporärt ID tills vi har riktig ID-generering
                    name = name,
                    species = species,
                    description = description,
                    gardenId = gardenId,
                    lastWatered = Instant.now(),
                    nextWatering = Instant.now().plusSeconds(60L * 60L * 24L * nextWateringDays),
                    createdAt = Instant.now(),
                    updatedAt = Instant.now()
                )

                when (val result = plantRepository.insertPlant(plant)) {
                    is Resource.Success -> {
                        Log.d(TAG, "addPlant: Successfully added plant")
                        _uiState.value = AddPlantUiState.Success
                    }
                    is Resource.Error -> {
                        Log.e(TAG, "addPlant: Error adding plant: ${result.message}")
                        _uiState.value = AddPlantUiState.Error(result.message ?: "Kunde inte lägga till växten")
                    }
                    else -> {
                        Log.e(TAG, "addPlant: Unexpected result type")
                        _uiState.value = AddPlantUiState.Error("Oväntat fel uppstod")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "addPlant: Exception", e)
                _uiState.value = AddPlantUiState.Error(e.message ?: "Ett oväntat fel uppstod")
            }
        }
    }
} 