package com.bps.plantseeds3.presentation.plants.add_edit

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bps.plantseeds3.domain.model.Plant
import com.bps.plantseeds3.domain.model.PlantCategory
import com.bps.plantseeds3.domain.model.PlantStatus
import com.bps.plantseeds3.domain.repository.GardenRepository
import com.bps.plantseeds3.domain.repository.PlantRepository
import com.bps.plantseeds3.domain.use_case.plant.AddPlantUseCase
import com.bps.plantseeds3.domain.use_case.plant.GetPlantUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject
import java.util.UUID

@HiltViewModel
class AddEditPlantViewModel @Inject constructor(
    private val plantRepository: PlantRepository,
    private val gardenRepository: GardenRepository,
    private val addPlantUseCase: AddPlantUseCase,
    private val getPlantUseCase: GetPlantUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(AddEditPlantState())
    val state: StateFlow<AddEditPlantState> = _state.asStateFlow()

    init {
        savedStateHandle.get<String>("plantId")?.let { plantId ->
            if (plantId.isNotEmpty()) {
                viewModelScope.launch {
                    getPlantUseCase(plantId).collect { plant ->
                        plant?.let {
                            _state.update { state ->
                                state.copy(
                                    name = it.name,
                                    scientificName = it.scientificName,
                                    species = it.species,
                                    variety = it.variety,
                                    description = it.description,
                                    category = it.category,
                                    status = it.status,
                                    plantingDate = it.plantingDate,
                                    expectedHarvestDate = it.expectedHarvestDate,
                                    actualHarvestDate = it.actualHarvestDate,
                                    sowingDepth = it.sowingDepth,
                                    spacing = it.spacing,
                                    daysToGermination = it.daysToGermination,
                                    daysToMaturity = it.daysToMaturity,
                                    sunRequirement = it.sunRequirement,
                                    waterRequirement = it.waterRequirement,
                                    soilRequirement = it.soilRequirement,
                                    soilPh = it.soilPh,
                                    hardiness = it.hardiness,
                                    sowingInstructions = it.sowingInstructions,
                                    growingInstructions = it.growingInstructions,
                                    harvestInstructions = it.harvestInstructions,
                                    storageInstructions = it.storageInstructions,
                                    companionPlants = it.companionPlants,
                                    avoidPlants = it.avoidPlants,
                                    height = it.height,
                                    spread = it.spread,
                                    yield = it.yield,
                                    culinaryUses = it.culinaryUses,
                                    medicinalUses = it.medicinalUses,
                                    tags = it.tags,
                                    notes = it.notes,
                                    gardenId = it.gardenId
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditPlantEvent) {
        when (event) {
            is AddEditPlantEvent.OnNameChange -> {
                _state.update { it.copy(name = event.name) }
            }
            is AddEditPlantEvent.OnScientificNameChange -> {
                _state.update { it.copy(scientificName = event.name) }
            }
            is AddEditPlantEvent.OnSpeciesChange -> {
                _state.update { it.copy(species = event.species) }
            }
            is AddEditPlantEvent.OnVarietyChange -> {
                _state.update { it.copy(variety = event.variety) }
            }
            is AddEditPlantEvent.OnDescriptionChange -> {
                _state.update { it.copy(description = event.description) }
            }
            is AddEditPlantEvent.OnCategoryChange -> {
                _state.update { it.copy(category = event.category) }
            }
            is AddEditPlantEvent.OnStatusChange -> {
                _state.update { it.copy(status = event.status) }
            }
            is AddEditPlantEvent.OnPlantingDateChange -> {
                _state.update { it.copy(plantingDate = event.date) }
            }
            is AddEditPlantEvent.OnExpectedHarvestDateChange -> {
                _state.update { it.copy(expectedHarvestDate = event.date) }
            }
            is AddEditPlantEvent.OnActualHarvestDateChange -> {
                _state.update { it.copy(actualHarvestDate = event.date) }
            }
            is AddEditPlantEvent.OnSowingDepthChange -> {
                _state.update { it.copy(sowingDepth = event.depth?.toFloatOrNull()) }
            }
            is AddEditPlantEvent.OnSpacingChange -> {
                _state.update { it.copy(spacing = event.spacing?.toFloatOrNull()) }
            }
            is AddEditPlantEvent.OnDaysToGerminationChange -> {
                _state.update { it.copy(daysToGermination = event.days?.toIntOrNull()) }
            }
            is AddEditPlantEvent.OnDaysToMaturityChange -> {
                _state.update { it.copy(daysToMaturity = event.days?.toIntOrNull()) }
            }
            is AddEditPlantEvent.OnSunRequirementChange -> {
                _state.update { it.copy(sunRequirement = event.requirement) }
            }
            is AddEditPlantEvent.OnWaterRequirementChange -> {
                _state.update { it.copy(waterRequirement = event.requirement) }
            }
            is AddEditPlantEvent.OnSoilRequirementChange -> {
                _state.update { it.copy(soilRequirement = event.requirement) }
            }
            is AddEditPlantEvent.OnSoilPhChange -> {
                _state.update { it.copy(soilPh = event.ph?.toFloatOrNull()) }
            }
            is AddEditPlantEvent.OnHardinessChange -> {
                _state.update { it.copy(hardiness = event.hardiness) }
            }
            is AddEditPlantEvent.OnSowingInstructionsChange -> {
                _state.update { it.copy(sowingInstructions = event.instructions) }
            }
            is AddEditPlantEvent.OnGrowingInstructionsChange -> {
                _state.update { it.copy(growingInstructions = event.instructions) }
            }
            is AddEditPlantEvent.OnHarvestInstructionsChange -> {
                _state.update { it.copy(harvestInstructions = event.instructions) }
            }
            is AddEditPlantEvent.OnStorageInstructionsChange -> {
                _state.update { it.copy(storageInstructions = event.instructions) }
            }
            is AddEditPlantEvent.OnCompanionPlantsChange -> {
                _state.update { it.copy(companionPlants = event.plants.split(",").map { it.trim() }) }
            }
            is AddEditPlantEvent.OnAvoidPlantsChange -> {
                _state.update { it.copy(avoidPlants = event.plants.split(",").map { it.trim() }) }
            }
            is AddEditPlantEvent.OnHeightChange -> {
                _state.update { it.copy(height = event.height?.toFloatOrNull()) }
            }
            is AddEditPlantEvent.OnSpreadChange -> {
                _state.update { it.copy(spread = event.spread?.toFloatOrNull()) }
            }
            is AddEditPlantEvent.OnYieldChange -> {
                _state.update { it.copy(yield = event.yield) }
            }
            is AddEditPlantEvent.OnCulinaryUsesChange -> {
                _state.update { it.copy(culinaryUses = event.uses.split(",").map { it.trim() }) }
            }
            is AddEditPlantEvent.OnMedicinalUsesChange -> {
                _state.update { it.copy(medicinalUses = event.uses.split(",").map { it.trim() }) }
            }
            is AddEditPlantEvent.OnTagsChange -> {
                _state.update { it.copy(tags = event.tags.split(",").map { it.trim() }) }
            }
            is AddEditPlantEvent.OnNotesChange -> {
                _state.update { it.copy(notes = event.notes.split(",").map { it.trim() }) }
            }
            is AddEditPlantEvent.OnGardenIdChange -> {
                _state.update { it.copy(gardenId = event.id) }
            }
            is AddEditPlantEvent.OnShowCategoryDialog -> {
                _state.update { it.copy(showCategoryDialog = true) }
            }
            is AddEditPlantEvent.OnHideCategoryDialog -> {
                _state.update { it.copy(showCategoryDialog = false) }
            }
            is AddEditPlantEvent.OnShowStatusDialog -> {
                _state.update { it.copy(showStatusDialog = true) }
            }
            is AddEditPlantEvent.OnHideStatusDialog -> {
                _state.update { it.copy(showStatusDialog = false) }
            }
            is AddEditPlantEvent.OnSaveClick -> {
                viewModelScope.launch {
                    try {
                        val currentState = state.value
                        if (currentState.gardenId == null) {
                            _state.update { it.copy(error = "Garden ID is required") }
                            return@launch
                        }

                        val plant = Plant(
                            name = currentState.name,
                            scientificName = currentState.scientificName,
                            species = currentState.species,
                            variety = currentState.variety,
                            description = currentState.description,
                            category = currentState.category,
                            status = currentState.status,
                            plantingDate = currentState.plantingDate,
                            expectedHarvestDate = currentState.expectedHarvestDate,
                            actualHarvestDate = currentState.actualHarvestDate,
                            sowingDepth = currentState.sowingDepth,
                            spacing = currentState.spacing,
                            daysToGermination = currentState.daysToGermination,
                            daysToMaturity = currentState.daysToMaturity,
                            sunRequirement = currentState.sunRequirement,
                            waterRequirement = currentState.waterRequirement,
                            soilRequirement = currentState.soilRequirement,
                            soilPh = currentState.soilPh,
                            hardiness = currentState.hardiness,
                            sowingInstructions = currentState.sowingInstructions,
                            growingInstructions = currentState.growingInstructions,
                            harvestInstructions = currentState.harvestInstructions,
                            storageInstructions = currentState.storageInstructions,
                            companionPlants = currentState.companionPlants,
                            avoidPlants = currentState.avoidPlants,
                            height = currentState.height,
                            spread = currentState.spread,
                            yield = currentState.yield,
                            culinaryUses = currentState.culinaryUses,
                            medicinalUses = currentState.medicinalUses,
                            tags = currentState.tags,
                            notes = currentState.notes,
                            gardenId = currentState.gardenId,
                            createdAt = LocalDateTime.now(),
                            updatedAt = LocalDateTime.now()
                        )

                        addPlantUseCase(plant)
                        _state.update { it.copy(isSaved = true) }
                    } catch (e: Exception) {
                        _state.update { it.copy(error = e.message) }
                    }
                }
            }
        }
    }
} 