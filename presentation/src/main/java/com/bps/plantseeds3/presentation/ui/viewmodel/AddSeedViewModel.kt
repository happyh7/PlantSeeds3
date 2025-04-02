package com.bps.plantseeds3.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bps.plantseeds3.common.model.Resource
import com.bps.plantseeds3.domain.model.Seed
import com.bps.plantseeds3.domain.model.Plant
import com.bps.plantseeds3.domain.repository.SeedRepository
import com.bps.plantseeds3.domain.repository.PlantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.UUID
import javax.inject.Inject

private const val TAG = "AddSeedViewModel"

@HiltViewModel
class AddSeedViewModel @Inject constructor(
    private val seedRepository: SeedRepository,
    private val plantRepository: PlantRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddSeedUiState())
    val uiState: StateFlow<AddSeedUiState> = _uiState.asStateFlow()

    fun onNameChange(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    fun onSpeciesChange(species: String) {
        _uiState.value = _uiState.value.copy(species = species)
    }

    fun onDescriptionChange(description: String) {
        _uiState.value = _uiState.value.copy(description = description)
    }

    fun saveSeed(onSuccess: () -> Unit) {
        viewModelScope.launch {
            Log.d(TAG, "saveSeed: Starting to save seed")
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val now = Instant.now()
                val plantId = UUID.randomUUID().toString()
                
                val plant = Plant(
                    id = plantId,
                    name = _uiState.value.name,
                    species = _uiState.value.species.ifBlank { "Okänd" },
                    description = _uiState.value.description.ifBlank { null },
                    gardenId = null,
                    lastWatered = null,
                    nextWatering = null,
                    createdAt = now,
                    updatedAt = now
                )

                Log.d(TAG, "saveSeed: Created plant = $plant")

                when (val plantResult = plantRepository.insertPlant(plant)) {
                    is Resource.Success<Unit> -> {
                        Log.d(TAG, "saveSeed: Successfully inserted plant")
                        
                        val seed = Seed(
                            id = UUID.randomUUID().toString(),
                            plantId = plantId,
                            name = _uiState.value.name,
                            species = _uiState.value.species.ifBlank { null },
                            description = _uiState.value.description.ifBlank { null },
                            plantingInstructions = null,
                            daysToGermination = null,
                            daysToHarvest = null,
                            lightNeeds = null,
                            waterNeeds = null,
                            soilType = null,
                            temperature = null,
                            spacing = null,
                            companionPlants = null,
                            avoidPlants = null,
                            imageUrl = null,
                            createdAt = now,
                            updatedAt = now
                        )

                        Log.d(TAG, "saveSeed: Created seed = $seed")

                        when (val seedResult = seedRepository.insertSeed(seed)) {
                            is Resource.Success<Unit> -> {
                                Log.d(TAG, "saveSeed: Successfully inserted seed")
                                _uiState.value = _uiState.value.copy(isLoading = false)
                                onSuccess()
                            }
                            is Resource.Error<Unit> -> {
                                Log.e(TAG, "saveSeed: Failed to insert seed", Exception(seedResult.message))
                                _uiState.value = _uiState.value.copy(
                                    isLoading = false,
                                    error = seedResult.message
                                )
                            }
                            is Resource.Loading<Unit> -> {
                                Log.d(TAG, "saveSeed: Loading state when inserting seed")
                                _uiState.value = _uiState.value.copy(isLoading = true)
                            }
                        }
                    }
                    is Resource.Error<Unit> -> {
                        Log.e(TAG, "saveSeed: Failed to insert plant", Exception(plantResult.message))
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = plantResult.message
                        )
                    }
                    is Resource.Loading<Unit> -> {
                        Log.d(TAG, "saveSeed: Loading state when inserting plant")
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "saveSeed: Exception occurred", e)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Kunde inte spara frö: ${e.message}"
                )
            }
        }
    }
}

data class AddSeedUiState(
    val name: String = "",
    val species: String = "",
    val description: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
) 