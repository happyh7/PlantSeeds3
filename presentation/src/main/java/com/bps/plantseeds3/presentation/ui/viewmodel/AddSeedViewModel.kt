package com.bps.plantseeds3.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bps.plantseeds3.common.model.Resource
import com.bps.plantseeds3.domain.model.Seed
import com.bps.plantseeds3.domain.model.Plant
import com.bps.plantseeds3.domain.use_case.AddSeedUseCase
import com.bps.plantseeds3.domain.use_case.AddPlantUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
    private val addSeedUseCase: AddSeedUseCase,
    private val addPlantUseCase: AddPlantUseCase
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

    fun onPlantingInstructionsChange(plantingInstructions: String) {
        _uiState.value = _uiState.value.copy(plantingInstructions = plantingInstructions)
    }

    fun onDaysToGerminationChange(daysToGermination: Int) {
        _uiState.value = _uiState.value.copy(daysToGermination = daysToGermination)
    }

    fun onDaysToHarvestChange(daysToHarvest: Int) {
        _uiState.value = _uiState.value.copy(daysToHarvest = daysToHarvest)
    }

    fun onLightNeedsChange(lightNeeds: String) {
        _uiState.value = _uiState.value.copy(lightNeeds = lightNeeds)
    }

    fun onWaterNeedsChange(waterNeeds: String) {
        _uiState.value = _uiState.value.copy(waterNeeds = waterNeeds)
    }

    fun onSoilTypeChange(soilType: String) {
        _uiState.value = _uiState.value.copy(soilType = soilType)
    }

    fun onTemperatureChange(temperature: String) {
        _uiState.value = _uiState.value.copy(temperature = temperature)
    }

    fun onSpacingChange(spacing: String) {
        _uiState.value = _uiState.value.copy(spacing = spacing)
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

                when (val plantResult = addPlantUseCase(plant)) {
                    is Resource.Success<Unit> -> {
                        Log.d(TAG, "saveSeed: Successfully inserted plant")
                        
                        val seed = Seed(
                            id = UUID.randomUUID().toString(),
                            plantId = plantId,
                            name = _uiState.value.name,
                            species = _uiState.value.species.ifBlank { null },
                            description = _uiState.value.description.ifBlank { null },
                            plantingInstructions = _uiState.value.plantingInstructions.ifBlank { null },
                            daysToGermination = _uiState.value.daysToGermination,
                            daysToHarvest = _uiState.value.daysToHarvest,
                            lightNeeds = _uiState.value.lightNeeds.ifBlank { null },
                            waterNeeds = _uiState.value.waterNeeds.ifBlank { null },
                            soilType = _uiState.value.soilType.ifBlank { null },
                            temperature = _uiState.value.temperature.ifBlank { null },
                            spacing = _uiState.value.spacing.ifBlank { null },
                            companionPlants = null,
                            avoidPlants = null,
                            imageUrl = null,
                            createdAt = now,
                            updatedAt = now,
                            isSynced = false
                        )

                        Log.d(TAG, "saveSeed: Created seed = $seed")

                        when (val result = addSeedUseCase(seed)) {
                            is Resource.Success<Unit> -> {
                                Log.d(TAG, "saveSeed: Successfully inserted seed")
                                // Vänta längre för att säkerställa att databasen har slutfört operationen
                                delay(500)
                                _uiState.value = _uiState.value.copy(isLoading = false)
                                onSuccess()
                            }
                            is Resource.Error<Unit> -> {
                                Log.e(TAG, "saveSeed: Failed to insert seed", Exception(result.message))
                                // Försök ta bort planten om seed misslyckas
                                try {
                                    // TODO: Implementera DeletePlantUseCase och använd den här
                                    _uiState.value = _uiState.value.copy(
                                        isLoading = false,
                                        error = result.message
                                    )
                                } catch (e: Exception) {
                                    Log.e(TAG, "saveSeed: Failed to delete plant after seed error", e)
                                    _uiState.value = _uiState.value.copy(
                                        isLoading = false,
                                        error = result.message
                                    )
                                }
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
    val plantingInstructions: String = "",
    val daysToGermination: Int? = null,
    val daysToHarvest: Int? = null,
    val lightNeeds: String = "",
    val waterNeeds: String = "",
    val soilType: String = "",
    val temperature: String = "",
    val spacing: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
) 