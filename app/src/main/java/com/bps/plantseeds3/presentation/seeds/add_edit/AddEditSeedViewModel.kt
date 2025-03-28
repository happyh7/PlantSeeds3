package com.bps.plantseeds3.presentation.seeds.add_edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bps.plantseeds3.data.local.entity.Seed
import com.bps.plantseeds3.data.local.entity.SeedFormData
import com.bps.plantseeds3.domain.repository.SeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddEditSeedViewModel @Inject constructor(
    private val repository: SeedRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(AddEditSeedState())
    val state: State<AddEditSeedState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentSeedId: String? = null

    init {
        savedStateHandle.get<String>("seedId")?.let { seedId ->
            if (seedId.isNotEmpty()) {
                currentSeedId = seedId
                loadSeed(seedId)
            }
        }
    }

    fun onEvent(event: AddEditSeedEvent) {
        when (event) {
            is AddEditSeedEvent.EnteredName -> {
                _state.value = _state.value.copy(name = event.value)
            }
            is AddEditSeedEvent.EnteredScientificName -> {
                _state.value = _state.value.copy(scientificName = event.value)
            }
            is AddEditSeedEvent.EnteredSpecies -> {
                _state.value = _state.value.copy(species = event.value)
            }
            is AddEditSeedEvent.EnteredVariety -> {
                _state.value = _state.value.copy(variety = event.value)
            }
            is AddEditSeedEvent.EnteredCategory -> {
                _state.value = _state.value.copy(category = event.value)
            }
            is AddEditSeedEvent.EnteredDescription -> {
                _state.value = _state.value.copy(description = event.value)
            }
            is AddEditSeedEvent.EnteredPlantingDepth -> {
                _state.value = _state.value.copy(plantingDepth = event.value)
            }
            is AddEditSeedEvent.EnteredPlantingDistance -> {
                _state.value = _state.value.copy(plantingDistance = event.value)
            }
            is AddEditSeedEvent.EnteredPlantingDates -> {
                _state.value = _state.value.copy(plantingDates = event.value)
            }
            is AddEditSeedEvent.EnteredSunRequirement -> {
                _state.value = _state.value.copy(sunRequirement = event.value)
            }
            is AddEditSeedEvent.EnteredWaterRequirement -> {
                _state.value = _state.value.copy(waterRequirement = event.value)
            }
            is AddEditSeedEvent.EnteredSoilRequirement -> {
                _state.value = _state.value.copy(soilRequirement = event.value)
            }
            is AddEditSeedEvent.EnteredHardiness -> {
                _state.value = _state.value.copy(hardiness = event.value)
            }
            is AddEditSeedEvent.EnteredSowingInstructions -> {
                _state.value = _state.value.copy(sowingInstructions = event.value)
            }
            is AddEditSeedEvent.EnteredGrowingInstructions -> {
                _state.value = _state.value.copy(growingInstructions = event.value)
            }
            is AddEditSeedEvent.EnteredHarvestInstructions -> {
                _state.value = _state.value.copy(harvestInstructions = event.value)
            }
            is AddEditSeedEvent.EnteredDaysToGermination -> {
                _state.value = _state.value.copy(daysToGermination = event.value)
            }
            is AddEditSeedEvent.EnteredDaysToHarvest -> {
                _state.value = _state.value.copy(daysToHarvest = event.value)
            }
            is AddEditSeedEvent.EnteredHarvestPeriod -> {
                _state.value = _state.value.copy(harvestPeriod = event.value)
            }
            is AddEditSeedEvent.EnteredCompanionPlants -> {
                _state.value = _state.value.copy(companionPlants = event.value)
            }
            is AddEditSeedEvent.EnteredAvoidPlants -> {
                _state.value = _state.value.copy(avoidPlants = event.value)
            }
            is AddEditSeedEvent.EnteredPlantSpacing -> {
                _state.value = _state.value.copy(plantSpacing = event.value)
            }
            is AddEditSeedEvent.EnteredRowSpacing -> {
                _state.value = _state.value.copy(rowSpacing = event.value)
            }
            is AddEditSeedEvent.EnteredTags -> {
                _state.value = _state.value.copy(tags = event.value)
            }
            is AddEditSeedEvent.EnteredNotes -> {
                _state.value = _state.value.copy(notes = event.value)
            }
            is AddEditSeedEvent.SaveSeed -> {
                saveSeed()
            }
            is AddEditSeedEvent.ToggleSuggestions -> {
                _state.value = _state.value.copy(showSuggestions = !_state.value.showSuggestions)
            }
            is AddEditSeedEvent.SelectSuggestion -> {
                _state.value = _state.value.copy(
                    name = event.suggestion.name,
                    scientificName = event.suggestion.scientificName ?: "",
                    species = event.suggestion.species ?: "",
                    variety = event.suggestion.variety ?: "",
                    category = event.suggestion.category ?: "",
                    description = event.suggestion.description ?: "",
                    plantingDepth = event.suggestion.plantingDepth?.toString() ?: "",
                    plantingDistance = event.suggestion.plantingDistance?.toString() ?: "",
                    plantingDates = event.suggestion.plantingDates ?: "",
                    sunRequirement = event.suggestion.sunRequirement ?: "",
                    waterRequirement = event.suggestion.waterRequirement ?: "",
                    soilRequirement = event.suggestion.soilRequirement ?: "",
                    hardiness = event.suggestion.hardiness ?: "",
                    sowingInstructions = event.suggestion.sowingInstructions ?: "",
                    growingInstructions = event.suggestion.growingInstructions ?: "",
                    harvestInstructions = event.suggestion.harvestInstructions ?: "",
                    daysToGermination = event.suggestion.daysToGermination?.toString() ?: "",
                    daysToHarvest = event.suggestion.daysToHarvest?.toString() ?: "",
                    harvestPeriod = event.suggestion.harvestPeriod ?: "",
                    companionPlants = event.suggestion.companionPlants ?: "",
                    avoidPlants = event.suggestion.avoidPlants ?: "",
                    plantSpacing = event.suggestion.plantSpacing?.toString() ?: "",
                    rowSpacing = event.suggestion.rowSpacing?.toString() ?: "",
                    tags = event.suggestion.tags ?: "",
                    notes = event.suggestion.notes ?: "",
                    showSuggestions = false
                )
            }
            is AddEditSeedEvent.ClearAll -> {
                _state.value = AddEditSeedState()
            }
        }
    }

    fun loadSeed(seedId: String) {
        viewModelScope.launch {
            try {
                repository.getSeedById(seedId)?.let { seed ->
                    _state.value = _state.value.copy(
                        name = seed.name,
                        scientificName = seed.scientificName ?: "",
                        species = seed.species ?: "",
                        variety = seed.variety ?: "",
                        category = seed.category ?: "",
                        description = seed.description ?: "",
                        plantingDepth = seed.plantingDepth?.toString() ?: "",
                        plantingDistance = seed.plantingDistance?.toString() ?: "",
                        plantingDates = seed.plantingDates ?: "",
                        sunRequirement = seed.sunRequirement ?: "",
                        waterRequirement = seed.waterRequirement ?: "",
                        soilRequirement = seed.soilRequirement ?: "",
                        hardiness = seed.hardiness ?: "",
                        sowingInstructions = seed.sowingInstructions ?: "",
                        growingInstructions = seed.growingInstructions ?: "",
                        harvestInstructions = seed.harvestInstructions ?: "",
                        daysToGermination = seed.daysToGermination?.toString() ?: "",
                        daysToHarvest = seed.daysToHarvest?.toString() ?: "",
                        harvestPeriod = seed.harvestPeriod ?: "",
                        companionPlants = seed.companionPlants ?: "",
                        avoidPlants = seed.avoidPlants ?: "",
                        plantSpacing = seed.plantSpacing?.toString() ?: "",
                        rowSpacing = seed.rowSpacing?.toString() ?: "",
                        tags = seed.tags ?: "",
                        notes = seed.notes ?: ""
                    )
                }
            } catch (e: Exception) {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.ShowError("Kunde inte hämta fröet: ${e.message}"))
                }
            }
        }
    }

    private fun saveSeed() {
        val currentState = _state.value
        if (currentState.name.isBlank()) {
            viewModelScope.launch {
                _eventFlow.emit(UiEvent.ShowError("Namn krävs"))
            }
            return
        }

        viewModelScope.launch {
            try {
                val seed = Seed(
                    id = currentSeedId ?: UUID.randomUUID().toString(),
                    name = currentState.name,
                    scientificName = currentState.scientificName.takeIf { it.isNotBlank() },
                    species = currentState.species.takeIf { it.isNotBlank() },
                    variety = currentState.variety.takeIf { it.isNotBlank() },
                    category = currentState.category.takeIf { it.isNotBlank() },
                    description = currentState.description.takeIf { it.isNotBlank() },
                    plantingDepth = currentState.plantingDepth.toFloatOrNull(),
                    plantingDistance = currentState.plantingDistance.toFloatOrNull(),
                    plantingDates = currentState.plantingDates.takeIf { it.isNotBlank() },
                    sunRequirement = currentState.sunRequirement.takeIf { it.isNotBlank() },
                    waterRequirement = currentState.waterRequirement.takeIf { it.isNotBlank() },
                    soilRequirement = currentState.soilRequirement.takeIf { it.isNotBlank() },
                    hardiness = currentState.hardiness.takeIf { it.isNotBlank() },
                    sowingInstructions = currentState.sowingInstructions.takeIf { it.isNotBlank() },
                    growingInstructions = currentState.growingInstructions.takeIf { it.isNotBlank() },
                    harvestInstructions = currentState.harvestInstructions.takeIf { it.isNotBlank() },
                    daysToGermination = currentState.daysToGermination.toIntOrNull(),
                    daysToHarvest = currentState.daysToHarvest.toIntOrNull(),
                    harvestPeriod = currentState.harvestPeriod.takeIf { it.isNotBlank() },
                    companionPlants = currentState.companionPlants.takeIf { it.isNotBlank() },
                    avoidPlants = currentState.avoidPlants.takeIf { it.isNotBlank() },
                    plantSpacing = currentState.plantSpacing.toFloatOrNull(),
                    rowSpacing = currentState.rowSpacing.toFloatOrNull(),
                    tags = currentState.tags.takeIf { it.isNotBlank() },
                    notes = currentState.notes.takeIf { it.isNotBlank() },
                    createdAt = LocalDate.now(),
                    updatedAt = LocalDate.now()
                )

                if (currentSeedId == null) {
                    repository.insertSeed(seed)
                } else {
                    repository.updateSeed(seed)
                }

                _eventFlow.emit(UiEvent.SaveSeed)
            } catch (e: Exception) {
                _eventFlow.emit(UiEvent.ShowError("Kunde inte spara fröet: ${e.message}"))
            }
        }
    }

    sealed class UiEvent {
        data object SaveSeed : UiEvent()
        data class ShowError(val message: String) : UiEvent()
    }
} 