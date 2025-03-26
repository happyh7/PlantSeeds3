package com.bps.plantseeds3.presentation.seeds.add_edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bps.plantseeds3.data.local.entity.SeedFormData
import com.bps.plantseeds3.data.local.entity.Seed
import com.bps.plantseeds3.domain.use_case.seed.SeedUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditSeedViewModel @Inject constructor(
    private val seedUseCases: SeedUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _formData = mutableStateOf(SeedFormData())
    val formData: State<SeedFormData> = _formData

    private val _validationErrors = mutableStateOf<List<String>>(emptyList())
    val validationErrors: State<List<String>> = _validationErrors

    private val _showSuggestions = mutableStateOf(true)
    val showSuggestions: State<Boolean> = _showSuggestions

    private val _suggestions = mutableStateOf<List<SeedFormData>>(emptyList())
    val suggestions: State<List<SeedFormData>> = _suggestions

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentSeedId: String? = null

    init {
        savedStateHandle.get<String>("seedId")?.let { seedId ->
            if (seedId.isNotEmpty()) {
                currentSeedId = seedId
                viewModelScope.launch {
                    seedUseCases.getSeed(seedId)?.let { seed ->
                        _formData.value = _formData.value.copy(
                            name = seed.name,
                            scientificName = seed.scientificName,
                            species = seed.species,
                            variety = seed.variety,
                            category = seed.category,
                            description = seed.description,
                            plantingInstructions = seed.sowingInstructions,
                            daysToGermination = seed.daysToGermination.toString(),
                            daysToHarvest = seed.daysToHarvest.toString(),
                            plantingDepth = seed.plantingDepth.toString(),
                            plantingDistance = seed.plantingDistance.toString(),
                            plantSpacing = seed.plantSpacing.toString(),
                            rowSpacing = seed.rowSpacing.toString(),
                            sunRequirement = seed.sunRequirement,
                            waterRequirement = seed.waterRequirement,
                            soilRequirement = seed.soilRequirement,
                            hardiness = seed.hardiness,
                            companionPlants = seed.companionPlants,
                            avoidPlants = seed.avoidPlants,
                            tags = seed.tags
                        )
                    }
                }
            }
        }
    }

    private fun validateForm(): Boolean {
        val errors = mutableListOf<String>()
        
        if (_formData.value.name.isBlank()) {
            errors.add("Namn är obligatoriskt")
        }
        
        _validationErrors.value = errors
        return errors.isEmpty()
    }

    private fun updateSuggestions(query: String) {
        viewModelScope.launch {
            seedUseCases.getSeeds().collect { seeds ->
                _suggestions.value = seeds
                    .filter { seed: Seed -> seed.name.startsWith(query, ignoreCase = true) }
                    .map { seed: Seed ->
                        SeedFormData(
                            name = seed.name,
                            scientificName = seed.scientificName,
                            species = seed.species,
                            variety = seed.variety,
                            category = seed.category,
                            description = seed.description,
                            plantingDepth = seed.plantingDepth.toString(),
                            plantingDistance = seed.plantingDistance.toString(),
                            plantingDates = seed.plantingDates,
                            harvestPeriod = seed.harvestPeriod,
                            maintenanceDates = seed.maintenanceDates,
                            notes = seed.notes,
                            sowingInstructions = seed.sowingInstructions,
                            growingInstructions = seed.growingInstructions,
                            harvestInstructions = seed.harvestInstructions,
                            daysToGermination = seed.daysToGermination.toString(),
                            daysToHarvest = seed.daysToHarvest.toString(),
                            plantSpacing = seed.plantSpacing.toString(),
                            rowSpacing = seed.rowSpacing.toString(),
                            sunRequirement = seed.sunRequirement,
                            waterRequirement = seed.waterRequirement,
                            soilRequirement = seed.soilRequirement,
                            hardiness = seed.hardiness,
                            companionPlants = seed.companionPlants,
                            avoidPlants = seed.avoidPlants,
                            tags = seed.tags
                        )
                    }
            }
        }
    }

    fun onEvent(event: AddEditSeedEvent) {
        when (event) {
            is AddEditSeedEvent.EnteredName -> {
                _formData.value = _formData.value.copy(name = event.value)
                if (_showSuggestions.value) {
                    updateSuggestions(event.value)
                }
            }
            is AddEditSeedEvent.ToggleSuggestions -> {
                _showSuggestions.value = !_showSuggestions.value
                if (!_showSuggestions.value) {
                    _suggestions.value = emptyList()
                } else {
                    updateSuggestions(_formData.value.name)
                }
            }
            is AddEditSeedEvent.SelectSuggestion -> {
                _formData.value = event.suggestion
                _suggestions.value = emptyList()
            }
            is AddEditSeedEvent.ClearAll -> {
                _formData.value = SeedFormData()
                _suggestions.value = emptyList()
            }
            is AddEditSeedEvent.EnteredSpecies -> {
                _formData.value = _formData.value.copy(species = event.value)
            }
            is AddEditSeedEvent.EnteredDescription -> {
                _formData.value = _formData.value.copy(description = event.value)
            }
            is AddEditSeedEvent.EnteredSowingInstructions -> {
                _formData.value = _formData.value.copy(plantingInstructions = event.value)
            }
            is AddEditSeedEvent.EnteredDaysToGermination -> {
                _formData.value = _formData.value.copy(daysToGermination = event.value)
            }
            is AddEditSeedEvent.EnteredDaysToHarvest -> {
                _formData.value = _formData.value.copy(daysToHarvest = event.value)
            }
            is AddEditSeedEvent.EnteredScientificName -> {
                _formData.value = _formData.value.copy(scientificName = event.value)
            }
            is AddEditSeedEvent.EnteredVariety -> {
                _formData.value = _formData.value.copy(variety = event.value)
            }
            is AddEditSeedEvent.EnteredCategory -> {
                _formData.value = _formData.value.copy(category = event.value)
            }
            is AddEditSeedEvent.EnteredPlantingDepth -> {
                _formData.value = _formData.value.copy(plantingDepth = event.value)
            }
            is AddEditSeedEvent.EnteredPlantingDistance -> {
                _formData.value = _formData.value.copy(plantingDistance = event.value)
            }
            is AddEditSeedEvent.EnteredPlantingDates -> {
                _formData.value = _formData.value.copy(plantingDates = event.value)
            }
            is AddEditSeedEvent.EnteredHarvestPeriod -> {
                _formData.value = _formData.value.copy(harvestPeriod = event.value)
            }
            is AddEditSeedEvent.EnteredMaintenanceDates -> {
                _formData.value = _formData.value.copy(maintenanceDates = event.value)
            }
            is AddEditSeedEvent.EnteredNotes -> {
                _formData.value = _formData.value.copy(notes = event.value)
            }
            is AddEditSeedEvent.EnteredGrowingInstructions -> {
                _formData.value = _formData.value.copy(growingInstructions = event.value)
            }
            is AddEditSeedEvent.EnteredHarvestInstructions -> {
                _formData.value = _formData.value.copy(harvestInstructions = event.value)
            }
            is AddEditSeedEvent.EnteredPlantSpacing -> {
                _formData.value = _formData.value.copy(plantSpacing = event.value)
            }
            is AddEditSeedEvent.EnteredRowSpacing -> {
                _formData.value = _formData.value.copy(rowSpacing = event.value)
            }
            is AddEditSeedEvent.EnteredSunRequirement -> {
                _formData.value = _formData.value.copy(sunRequirement = event.value)
            }
            is AddEditSeedEvent.EnteredWaterRequirement -> {
                _formData.value = _formData.value.copy(waterRequirement = event.value)
            }
            is AddEditSeedEvent.EnteredSoilRequirement -> {
                _formData.value = _formData.value.copy(soilRequirement = event.value)
            }
            is AddEditSeedEvent.EnteredHardiness -> {
                _formData.value = _formData.value.copy(hardiness = event.value)
            }
            is AddEditSeedEvent.EnteredCompanionPlants -> {
                _formData.value = _formData.value.copy(companionPlants = event.value)
            }
            is AddEditSeedEvent.EnteredAvoidPlants -> {
                _formData.value = _formData.value.copy(avoidPlants = event.value)
            }
            is AddEditSeedEvent.EnteredTags -> {
                _formData.value = _formData.value.copy(tags = event.value)
            }
            is AddEditSeedEvent.SaveSeed -> {
                viewModelScope.launch {
                    try {
                        if (!validateForm()) {
                            _eventFlow.emit(UiEvent.ShowError("Vänligen korrigera följande fel:\n" + _validationErrors.value.joinToString("\n")))
                            return@launch
                        }
                        
                        seedUseCases.addSeed(
                            name = _formData.value.name,
                            scientificName = _formData.value.scientificName,
                            species = _formData.value.species,
                            variety = _formData.value.variety,
                            category = _formData.value.category,
                            description = _formData.value.description,
                            plantingDepth = _formData.value.plantingDepth.toFloatOrNull() ?: 0f,
                            plantingDistance = _formData.value.plantingDistance.toFloatOrNull() ?: 0f,
                            plantSpacing = _formData.value.plantSpacing.toFloatOrNull() ?: 0f,
                            rowSpacing = _formData.value.rowSpacing.toFloatOrNull() ?: 0f,
                            plantingDates = _formData.value.plantingDates,
                            sunRequirement = _formData.value.sunRequirement,
                            waterRequirement = _formData.value.waterRequirement,
                            soilRequirement = _formData.value.soilRequirement,
                            hardiness = _formData.value.hardiness,
                            sowingInstructions = _formData.value.sowingInstructions,
                            growingInstructions = _formData.value.growingInstructions,
                            harvestInstructions = _formData.value.harvestInstructions,
                            daysToGermination = _formData.value.daysToGermination.toIntOrNull() ?: 0,
                            daysToHarvest = _formData.value.daysToHarvest.toIntOrNull() ?: 0,
                            harvestPeriod = _formData.value.harvestPeriod,
                            maintenanceDates = _formData.value.maintenanceDates,
                            companionPlants = _formData.value.companionPlants,
                            avoidPlants = _formData.value.avoidPlants,
                            tags = _formData.value.tags,
                            notes = _formData.value.notes
                        )
                        _eventFlow.emit(UiEvent.SaveSeed)
                    } catch (e: Exception) {
                        _eventFlow.emit(UiEvent.ShowError(e.message ?: "Kunde inte spara fröet"))
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data object SaveSeed : UiEvent()
        data class ShowError(val message: String) : UiEvent()
    }
} 