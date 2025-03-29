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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject
import java.util.UUID

@HiltViewModel
class AddEditPlantViewModel @Inject constructor(
    private val plantRepository: PlantRepository,
    private val gardenRepository: GardenRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(AddEditPlantState())
    val state: StateFlow<AddEditPlantState> = _state.asStateFlow()

    init {
        savedStateHandle.get<String>("plantId")?.let { plantId ->
            if (plantId != "-1") {
                viewModelScope.launch {
                    plantRepository.getPlantById(plantId)?.let { plant ->
                        _state.value = AddEditPlantState(
                            name = plant.name,
                            scientificName = plant.scientificName ?: "",
                            species = plant.species ?: "",
                            variety = plant.variety ?: "",
                            category = plant.category?.name ?: "",
                            description = plant.description ?: "",
                            selectedGardenId = plant.gardenId,
                            status = plant.status ?: PlantStatus.SEED,
                            plantingDate = plant.plantingDate?.let { LocalDate.parse(it) } ?: LocalDate.now(),
                            harvestDate = plant.harvestDate?.let { LocalDate.parse(it) },
                            sunRequirement = plant.sunRequirement,
                            waterRequirement = plant.waterRequirement,
                            soilRequirement = plant.soilRequirement,
                            notes = plant.notes
                        )
                    }
                }
            }
        }

        savedStateHandle.get<String>("gardenId")?.let { gardenId ->
            if (gardenId != "-1") {
                _state.value = _state.value.copy(selectedGardenId = gardenId)
            }
        }
    }

    fun onNameChange(name: String) {
        _state.value = _state.value.copy(name = name)
    }

    fun onScientificNameChange(scientificName: String) {
        _state.value = _state.value.copy(scientificName = scientificName)
    }

    fun onSpeciesChange(species: String) {
        _state.value = _state.value.copy(species = species)
    }

    fun onVarietyChange(variety: String) {
        _state.value = _state.value.copy(variety = variety)
    }

    fun onCategoryChange(category: String) {
        _state.value = _state.value.copy(category = category)
    }

    fun onDescriptionChange(description: String) {
        _state.value = _state.value.copy(description = description)
    }

    fun onStatusChange(status: PlantStatus) {
        _state.value = _state.value.copy(status = status)
    }

    fun onPlantingDateChange(date: LocalDate) {
        _state.value = _state.value.copy(plantingDate = date)
    }

    fun onHarvestDateChange(date: LocalDate?) {
        _state.value = _state.value.copy(harvestDate = date)
    }

    fun onSunRequirementChange(requirement: String) {
        _state.value = _state.value.copy(sunRequirement = requirement)
    }

    fun onWaterRequirementChange(requirement: String) {
        _state.value = _state.value.copy(waterRequirement = requirement)
    }

    fun onSoilRequirementChange(requirement: String) {
        _state.value = _state.value.copy(soilRequirement = requirement)
    }

    fun onNotesChange(notes: String) {
        _state.value = _state.value.copy(notes = notes)
    }

    fun onSowingDepthChange(depth: String) {
        _state.value = _state.value.copy(sowingDepth = depth)
    }

    fun onSpacingChange(spacing: String) {
        _state.value = _state.value.copy(spacing = spacing)
    }

    fun onDaysToGerminationChange(days: String) {
        _state.value = _state.value.copy(daysToGermination = days)
    }

    fun onDaysToMaturityChange(days: String) {
        _state.value = _state.value.copy(daysToMaturity = days)
    }

    fun onSoilPhChange(ph: String) {
        _state.value = _state.value.copy(soilPh = ph)
    }

    fun onHardinessChange(hardiness: String) {
        _state.value = _state.value.copy(hardiness = hardiness)
    }

    fun onSowingInstructionsChange(instructions: String) {
        _state.value = _state.value.copy(sowingInstructions = instructions)
    }

    fun onGrowingInstructionsChange(instructions: String) {
        _state.value = _state.value.copy(growingInstructions = instructions)
    }

    fun onHarvestInstructionsChange(instructions: String) {
        _state.value = _state.value.copy(harvestInstructions = instructions)
    }

    fun onStorageInstructionsChange(instructions: String) {
        _state.value = _state.value.copy(storageInstructions = instructions)
    }

    fun onCompanionPlantsChange(plants: String) {
        _state.value = _state.value.copy(companionPlants = plants)
    }

    fun onAvoidPlantsChange(plants: String) {
        _state.value = _state.value.copy(avoidPlants = plants)
    }

    fun onHeightChange(height: String) {
        _state.value = _state.value.copy(height = height)
    }

    fun onSpreadChange(spread: String) {
        _state.value = _state.value.copy(spread = spread)
    }

    fun onYieldChange(yield: String) {
        _state.value = _state.value.copy(yield = yield)
    }

    fun onCulinaryUsesChange(uses: String) {
        _state.value = _state.value.copy(culinaryUses = uses)
    }

    fun onMedicinalUsesChange(uses: String) {
        _state.value = _state.value.copy(medicinalUses = uses)
    }

    fun onTagsChange(tags: String) {
        _state.value = _state.value.copy(tags = tags)
    }

    fun savePlant() {
        viewModelScope.launch {
            val currentState = _state.value
            try {
                // Validera obligatoriska fält
                val validationErrors = mutableListOf<String>()
                
                if (currentState.name.isBlank()) {
                    validationErrors.add("Namn är obligatoriskt")
                }
                
                if (currentState.selectedGardenId == null) {
                    validationErrors.add("Ingen trädgård vald")
                }
                
                if (validationErrors.isNotEmpty()) {
                    _state.value = currentState.copy(error = validationErrors.joinToString("\n"))
                    return@launch
                }

                val plant = Plant(
                    id = UUID.randomUUID().toString(),
                    name = currentState.name.trim(),
                    scientificName = currentState.scientificName.trim(),
                    species = currentState.species.trim(),
                    variety = currentState.variety.trim(),
                    description = currentState.description.trim(),
                    category = currentState.category.let { PlantCategory.fromName(it) },
                    status = currentState.status,
                    plantingDate = currentState.plantingDate.toString(),
                    harvestDate = currentState.harvestDate?.toString() ?: "",
                    sunRequirement = currentState.sunRequirement?.trim() ?: "",
                    waterRequirement = currentState.waterRequirement?.trim() ?: "",
                    soilRequirement = currentState.soilRequirement?.trim() ?: "",
                    notes = currentState.notes?.trim() ?: "",
                    gardenId = currentState.selectedGardenId!!
                )

                Log.d("AddEditPlantViewModel", "Sparar växt: ${plant.name}")
                plantRepository.insertPlant(plant)
                Log.d("AddEditPlantViewModel", "Växt sparad framgångsrikt")
                _state.value = currentState.copy(isSaved = true)
            } catch (e: Exception) {
                Log.e("AddEditPlantViewModel", "Fel vid sparning av växt: ${e.message}")
                _state.value = currentState.copy(error = "Kunde inte spara växten: ${e.message}")
            }
        }
    }
} 