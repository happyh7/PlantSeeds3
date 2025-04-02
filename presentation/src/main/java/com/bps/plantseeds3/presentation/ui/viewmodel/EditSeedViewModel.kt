package com.bps.plantseeds3.presentation.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bps.plantseeds3.common.model.Resource
import com.bps.plantseeds3.domain.model.Seed
import com.bps.plantseeds3.domain.repository.SeedRepository
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
    private val seedRepository: SeedRepository,
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
            when (val result = seedRepository.getSeedById(id)) {
                is Resource.Success -> {
                    result.data?.let { seed ->
                        _uiState.value = _uiState.value.copy(
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
                            avoidPlants = seed.avoidPlants,
                            isLoading = false,
                            error = null
                        )
                    }
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.message ?: "Ett fel uppstod vid laddning av fröet"
                    )
                }
                is Resource.Loading -> {
                    _uiState.value = _uiState.value.copy(isLoading = true)
                }
            }
        }
    }

    fun onSaveSeed(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val seed = Seed(
                id = seedId ?: "",
                name = uiState.value.name,
                species = uiState.value.species,
                description = uiState.value.description,
                plantingInstructions = uiState.value.plantingInstructions,
                daysToGermination = uiState.value.daysToGermination,
                daysToHarvest = uiState.value.daysToHarvest,
                lightNeeds = uiState.value.lightNeeds,
                waterNeeds = uiState.value.waterNeeds,
                soilType = uiState.value.soilType,
                temperature = uiState.value.temperature,
                spacing = uiState.value.spacing,
                companionPlants = uiState.value.companionPlants,
                avoidPlants = uiState.value.avoidPlants,
                plantId = "",
                imageUrl = null,
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )

            when (val result = seedRepository.updateSeed(seed)) {
                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = null
                    )
                    onSuccess()
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.message ?: "Ett fel uppstod vid sparande av fröet"
                    )
                }
                is Resource.Loading -> {
                    _uiState.value = _uiState.value.copy(isLoading = true)
                }
            }
        }
    }

    fun onNameChange(value: String) {
        _uiState.value = _uiState.value.copy(name = value)
    }

    fun onSpeciesChange(value: String) {
        _uiState.value = _uiState.value.copy(species = value)
    }

    fun onDescriptionChange(value: String) {
        _uiState.value = _uiState.value.copy(description = value)
    }

    fun onPlantingInstructionsChange(value: String) {
        _uiState.value = _uiState.value.copy(plantingInstructions = value)
    }

    fun onDaysToGerminationChange(value: Int) {
        _uiState.value = _uiState.value.copy(daysToGermination = value)
    }

    fun onDaysToHarvestChange(value: Int) {
        _uiState.value = _uiState.value.copy(daysToHarvest = value)
    }

    fun onLightNeedsChange(value: String) {
        _uiState.value = _uiState.value.copy(lightNeeds = value)
    }

    fun onWaterNeedsChange(value: String) {
        _uiState.value = _uiState.value.copy(waterNeeds = value)
    }

    fun onSoilTypeChange(value: String) {
        _uiState.value = _uiState.value.copy(soilType = value)
    }

    fun onTemperatureChange(value: String) {
        _uiState.value = _uiState.value.copy(temperature = value)
    }

    fun onSpacingChange(value: String) {
        _uiState.value = _uiState.value.copy(spacing = value)
    }

    fun onCompanionPlantsChange(value: List<String>) {
        _uiState.value = _uiState.value.copy(companionPlants = value)
    }

    fun onAvoidPlantsChange(value: List<String>) {
        _uiState.value = _uiState.value.copy(avoidPlants = value)
    }
} 