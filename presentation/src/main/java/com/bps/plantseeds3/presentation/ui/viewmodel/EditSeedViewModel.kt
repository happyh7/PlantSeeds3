package com.bps.plantseeds3.presentation.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bps.plantseeds3.common.model.Resource
import com.bps.plantseeds3.domain.model.Seed
import com.bps.plantseeds3.domain.use_case.GetSeedUseCase
import com.bps.plantseeds3.domain.use_case.UpdateSeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

data class EditSeedUiState(
    val isLoading: Boolean = false,
    val name: String = "",
    val species: String? = null,
    val description: String? = null,
    val plantingInstructions: String? = null,
    val daysToGermination: Int? = null,
    val daysToHarvest: Int? = null,
    val lightNeeds: String? = null,
    val waterNeeds: String? = null,
    val soilType: String? = null,
    val temperature: String? = null,
    val spacing: String? = null,
    val companionPlants: List<String>? = null,
    val avoidPlants: List<String>? = null,
    val error: String? = null
)

@HiltViewModel
class EditSeedViewModel @Inject constructor(
    private val getSeedUseCase: GetSeedUseCase,
    private val updateSeedUseCase: UpdateSeedUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditSeedUiState())
    val uiState: StateFlow<EditSeedUiState> = _uiState.asStateFlow()

    private var seedId: String? = null

    init {
        savedStateHandle.get<String>("seedId")?.let { id ->
            seedId = id
            loadSeed(id)
        }
    }

    private fun loadSeed(id: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val result = getSeedUseCase(id)) {
                is Resource.Success -> {
                    val seed = result.data
                    _uiState.value = EditSeedUiState(
                        name = seed.name,
                        species = seed.species,
                        description = seed.description,
                        plantingInstructions = seed.plantingInstructions,
                        daysToGermination = seed.daysToGermination,
                        daysToHarvest = seed.daysToHarvest,
                        lightNeeds = seed.lightNeeds,
                        waterNeeds = seed.waterNeeds,
                        soilType = seed.soilType,
                        temperature = seed.temperature,
                        spacing = seed.spacing,
                        companionPlants = seed.companionPlants,
                        avoidPlants = seed.avoidPlants
                    )
                }
                is Resource.Error -> {
                    val errorMessage = if (result.message != null) {
                        result.message
                    } else {
                        "Kunde inte ladda fröet"
                    }
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = errorMessage
                    )
                }
                is Resource.Loading -> {
                    _uiState.value = _uiState.value.copy(isLoading = true)
                }
            }
        }
    }

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

    fun onSaveSeed(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val currentState = _uiState.value
            _uiState.value = currentState.copy(isLoading = true)
            
            if (seedId == null) {
                _uiState.value = currentState.copy(
                    isLoading = false,
                    error = "Inget frö-ID hittades"
                )
                return@launch
            }
            
            try {
                // Hämta det befintliga fröet för att få rätt tidsstämplar och plantId
                val existingSeed = when (val result = getSeedUseCase(seedId!!)) {
                    is Resource.Success -> result.data
                    else -> {
                        _uiState.value = currentState.copy(
                            isLoading = false,
                            error = "Kunde inte hitta det befintliga fröet"
                        )
                        return@launch
                    }
                }

                val seed = Seed(
                    id = seedId!!,
                    name = currentState.name,
                    species = currentState.species,
                    description = currentState.description,
                    plantingInstructions = currentState.plantingInstructions,
                    daysToGermination = currentState.daysToGermination,
                    daysToHarvest = currentState.daysToHarvest,
                    lightNeeds = currentState.lightNeeds,
                    waterNeeds = currentState.waterNeeds,
                    soilType = currentState.soilType,
                    temperature = currentState.temperature,
                    spacing = currentState.spacing,
                    companionPlants = currentState.companionPlants,
                    avoidPlants = currentState.avoidPlants,
                    plantId = existingSeed.plantId,
                    imageUrl = existingSeed.imageUrl,
                    createdAt = existingSeed.createdAt,
                    updatedAt = Instant.now()
                )

                when (val result = updateSeedUseCase(seed)) {
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = null
                        )
                        onSuccess()
                    }
                    is Resource.Error -> {
                        val errorMessage = if (result.message != null) {
                            result.message
                        } else {
                            "Ett fel uppstod vid sparande av fröet"
                        }
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = errorMessage
                        )
                    }
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                }
            } catch (e: Exception) {
                _uiState.value = currentState.copy(
                    isLoading = false,
                    error = "Ett oväntat fel uppstod: ${e.message}"
                )
            }
        }
    }
} 