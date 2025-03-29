package com.bps.plantseeds3.presentation.seeds.add_edit

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bps.plantseeds3.domain.model.Seed
import com.bps.plantseeds3.domain.model.PlantCategory
import com.bps.plantseeds3.domain.repository.SeedRepository
import com.bps.plantseeds3.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.util.UUID
import java.util.Date

@HiltViewModel
class AddEditSeedViewModel @Inject constructor(
    private val repository: SeedRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val TAG = "AddEditSeedViewModel"
    private val _state = MutableStateFlow(AddEditSeedState())
    val state: StateFlow<AddEditSeedState> = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        Log.d(TAG, "AddEditSeedViewModel initieras")
        savedStateHandle.get<String>("seedId")?.let { seedId ->
            Log.d(TAG, "Laddar frö med ID: $seedId")
            if (seedId.isNotEmpty() && seedId != "new") {
                loadSeed(seedId)
            }
        }
    }

    private fun loadSeed(seedId: String) {
        Log.d(TAG, "Hämtar frö från databasen: $seedId")
        viewModelScope.launch {
            try {
                if (seedId == "new") {
                    Log.d(TAG, "Skapar nytt frö")
                    _state.update { currentState ->
                        currentState.copy(
                            name = "",
                            scientificName = "",
                            species = "",
                            variety = "",
                            category = null,
                            description = "",
                            plantingDepth = "",
                            plantingDistance = "",
                            plantSpacing = "",
                            rowSpacing = "",
                            plantingDates = "",
                            daysToGermination = "",
                            daysToMaturity = "",
                            harvestPeriod = "",
                            lifespan = "",
                            hardinessZone = "",
                            sunRequirement = "",
                            waterRequirement = "",
                            soilType = "",
                            soilPh = "",
                            sowingInstructions = "",
                            growingInstructions = "",
                            harvestingInstructions = "",
                            storageInstructions = "",
                            companionPlants = "",
                            avoidPlants = "",
                            tags = "",
                            notes = "",
                            imageUrl = "",
                            source = "",
                            isFavorite = false,
                            error = null
                        )
                    }
                    return@launch
                }
                
                repository.getSeedById(seedId)?.let { seed ->
                    Log.d(TAG, "Hittade frö: ${seed.name}")
                    _state.update { currentState ->
                        currentState.copy(
                            seedId = seed.id,
                            name = seed.name,
                            scientificName = seed.scientificName ?: "",
                            species = seed.species ?: "",
                            variety = seed.variety ?: "",
                            category = seed.category,
                            description = seed.description ?: "",
                            plantingDepth = seed.plantingDepth?.toString() ?: "",
                            plantingDistance = seed.plantingDistance?.toString() ?: "",
                            plantSpacing = seed.plantSpacing?.toString() ?: "",
                            rowSpacing = seed.rowSpacing?.toString() ?: "",
                            plantingDates = seed.plantingDates ?: "",
                            daysToGermination = seed.daysToGermination?.toString() ?: "",
                            daysToMaturity = seed.daysToMaturity?.toString() ?: "",
                            harvestPeriod = seed.harvestPeriod ?: "",
                            lifespan = seed.lifespan ?: "",
                            hardinessZone = seed.hardinessZone ?: "",
                            sunRequirement = seed.sunRequirement ?: "",
                            waterRequirement = seed.waterRequirement ?: "",
                            soilType = seed.soilType ?: "",
                            soilPh = seed.soilPh?.toString() ?: "",
                            sowingInstructions = seed.sowingInstructions ?: "",
                            growingInstructions = seed.growingInstructions ?: "",
                            harvestingInstructions = seed.harvestingInstructions ?: "",
                            storageInstructions = seed.storageInstructions ?: "",
                            companionPlants = seed.companionPlants ?: "",
                            avoidPlants = seed.avoidPlants ?: "",
                            tags = seed.tags ?: "",
                            notes = seed.notes ?: "",
                            imageUrl = seed.imageUrl ?: "",
                            source = seed.source ?: "",
                            isFavorite = seed.isFavorite,
                            error = null
                        )
                    }
                } ?: run {
                    Log.w(TAG, "Inget frö hittades med ID: $seedId")
                    _state.update { it.copy(error = "Kunde inte hitta fröet") }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Fel vid hämtning av frö", e)
                _state.update { it.copy(error = "Ett fel uppstod: ${e.message}") }
            }
        }
    }

    fun onEvent(event: AddEditSeedEvent) {
        when (event) {
            is AddEditSeedEvent.OnNameChange -> {
                Log.d(TAG, "Uppdaterar namn: ${event.name}")
                _state.update { it.copy(name = event.name) }
            }
            is AddEditSeedEvent.OnScientificNameChange -> {
                Log.d(TAG, "Uppdaterar vetenskapligt namn: ${event.scientificName}")
                _state.update { it.copy(scientificName = event.scientificName) }
            }
            is AddEditSeedEvent.OnSpeciesChange -> {
                Log.d(TAG, "Uppdaterar art: ${event.species}")
                _state.update { it.copy(species = event.species) }
            }
            is AddEditSeedEvent.OnVarietyChange -> {
                Log.d(TAG, "Uppdaterar sort: ${event.variety}")
                _state.update { it.copy(variety = event.variety) }
            }
            is AddEditSeedEvent.OnDescriptionChange -> {
                Log.d(TAG, "Uppdaterar beskrivning: ${event.description}")
                _state.update { it.copy(description = event.description) }
            }
            is AddEditSeedEvent.OnCategoryChange -> {
                Log.d(TAG, "Uppdaterar kategori: ${event.category}")
                _state.update { it.copy(category = event.category) }
            }
            is AddEditSeedEvent.OnPlantingDepthChange -> {
                Log.d(TAG, "Uppdaterar planteringsdjup: ${event.plantingDepth}")
                _state.update { it.copy(plantingDepth = event.plantingDepth) }
            }
            is AddEditSeedEvent.OnPlantingDistanceChange -> {
                Log.d(TAG, "Uppdaterar planteringsavstånd: ${event.plantingDistance}")
                _state.update { it.copy(plantingDistance = event.plantingDistance) }
            }
            is AddEditSeedEvent.OnPlantSpacingChange -> {
                Log.d(TAG, "Uppdaterar plantavstånd: ${event.plantSpacing}")
                _state.update { it.copy(plantSpacing = event.plantSpacing) }
            }
            is AddEditSeedEvent.OnRowSpacingChange -> {
                Log.d(TAG, "Uppdaterar radavstånd: ${event.rowSpacing}")
                _state.update { it.copy(rowSpacing = event.rowSpacing) }
            }
            is AddEditSeedEvent.OnPlantingDatesChange -> {
                Log.d(TAG, "Uppdaterar planteringsdatum: ${event.plantingDates}")
                _state.update { it.copy(plantingDates = event.plantingDates) }
            }
            is AddEditSeedEvent.OnDaysToGerminationChange -> {
                Log.d(TAG, "Uppdaterar dagar till grodd: ${event.daysToGermination}")
                _state.update { it.copy(daysToGermination = event.daysToGermination) }
            }
            is AddEditSeedEvent.OnDaysToMaturityChange -> {
                Log.d(TAG, "Uppdaterar dagar till mognad: ${event.daysToMaturity}")
                _state.update { it.copy(daysToMaturity = event.daysToMaturity) }
            }
            is AddEditSeedEvent.OnHarvestPeriodChange -> {
                Log.d(TAG, "Uppdaterar skördeperiod: ${event.harvestPeriod}")
                _state.update { it.copy(harvestPeriod = event.harvestPeriod) }
            }
            is AddEditSeedEvent.OnLifespanChange -> {
                Log.d(TAG, "Uppdaterar livslängd: ${event.lifespan}")
                _state.update { it.copy(lifespan = event.lifespan) }
            }
            is AddEditSeedEvent.OnHardinessZoneChange -> {
                Log.d(TAG, "Uppdaterar hårdhetszon: ${event.hardinessZone}")
                _state.update { it.copy(hardinessZone = event.hardinessZone) }
            }
            is AddEditSeedEvent.OnSunRequirementChange -> {
                Log.d(TAG, "Uppdaterar solkrav: ${event.sunRequirement}")
                _state.update { it.copy(sunRequirement = event.sunRequirement) }
            }
            is AddEditSeedEvent.OnWaterRequirementChange -> {
                Log.d(TAG, "Uppdaterar vattenkrav: ${event.waterRequirement}")
                _state.update { it.copy(waterRequirement = event.waterRequirement) }
            }
            is AddEditSeedEvent.OnSoilTypeChange -> {
                Log.d(TAG, "Uppdaterar jordtyp: ${event.soilType}")
                _state.update { it.copy(soilType = event.soilType) }
            }
            is AddEditSeedEvent.OnSoilPhChange -> {
                Log.d(TAG, "Uppdaterar jord-pH: ${event.soilPh}")
                _state.update { it.copy(soilPh = event.soilPh) }
            }
            is AddEditSeedEvent.OnSowingInstructionsChange -> {
                Log.d(TAG, "Uppdaterar såinstruktioner: ${event.sowingInstructions}")
                _state.update { it.copy(sowingInstructions = event.sowingInstructions) }
            }
            is AddEditSeedEvent.OnGrowingInstructionsChange -> {
                Log.d(TAG, "Uppdaterar odlingsinstruktioner: ${event.growingInstructions}")
                _state.update { it.copy(growingInstructions = event.growingInstructions) }
            }
            is AddEditSeedEvent.OnHarvestingInstructionsChange -> {
                Log.d(TAG, "Uppdaterar skördeinstruktioner: ${event.harvestingInstructions}")
                _state.update { it.copy(harvestingInstructions = event.harvestingInstructions) }
            }
            is AddEditSeedEvent.OnStorageInstructionsChange -> {
                Log.d(TAG, "Uppdaterar lagringsinstruktioner: ${event.storageInstructions}")
                _state.update { it.copy(storageInstructions = event.storageInstructions) }
            }
            is AddEditSeedEvent.OnCompanionPlantsChange -> {
                Log.d(TAG, "Uppdaterar följgrödor: ${event.companionPlants}")
                _state.update { it.copy(companionPlants = event.companionPlants) }
            }
            is AddEditSeedEvent.OnAvoidPlantsChange -> {
                Log.d(TAG, "Uppdaterar undvik grödor: ${event.avoidPlants}")
                _state.update { it.copy(avoidPlants = event.avoidPlants) }
            }
            is AddEditSeedEvent.OnTagsChange -> {
                Log.d(TAG, "Uppdaterar taggar: ${event.tags}")
                _state.update { it.copy(tags = event.tags) }
            }
            is AddEditSeedEvent.OnNotesChange -> {
                Log.d(TAG, "Uppdaterar anteckningar: ${event.notes}")
                _state.update { it.copy(notes = event.notes) }
            }
            is AddEditSeedEvent.OnImageUrlChange -> {
                Log.d(TAG, "Uppdaterar bild-URL: ${event.imageUrl}")
                _state.update { it.copy(imageUrl = event.imageUrl) }
            }
            is AddEditSeedEvent.OnSourceChange -> {
                Log.d(TAG, "Uppdaterar källa: ${event.source}")
                _state.update { it.copy(source = event.source) }
            }
            is AddEditSeedEvent.OnFavoriteChange -> {
                Log.d(TAG, "Uppdaterar favoritstatus: ${event.isFavorite}")
                _state.update { it.copy(isFavorite = event.isFavorite) }
            }
            is AddEditSeedEvent.OnSaveClick -> {
                Log.d(TAG, "Sparar frö")
                saveSeed()
            }
        }
    }

    private fun saveSeed() {
        Log.d(TAG, "Validerar och sparar frö")
        if (!validateForm()) {
            Log.w(TAG, "Formuläret är inte giltigt")
            return
        }

        val seed = Seed(
            id = state.value.seedId.ifEmpty { UUID.randomUUID().toString() },
            name = state.value.name,
            scientificName = state.value.scientificName.takeIf { it.isNotBlank() },
            species = state.value.species.takeIf { it.isNotBlank() },
            variety = state.value.variety.takeIf { it.isNotBlank() },
            description = state.value.description.takeIf { it.isNotBlank() },
            category = state.value.category ?: PlantCategory.OTHER,
            plantingDepth = state.value.plantingDepth.toDoubleOrNull(),
            plantingDistance = state.value.plantingDistance.toDoubleOrNull(),
            plantSpacing = state.value.plantSpacing.toFloatOrNull(),
            rowSpacing = state.value.rowSpacing.toFloatOrNull(),
            plantingDates = state.value.plantingDates.takeIf { it.isNotBlank() },
            daysToGermination = state.value.daysToGermination.toIntOrNull(),
            daysToMaturity = state.value.daysToMaturity.toIntOrNull(),
            harvestPeriod = state.value.harvestPeriod.takeIf { it.isNotBlank() },
            lifespan = state.value.lifespan.takeIf { it.isNotBlank() },
            hardinessZone = state.value.hardinessZone.takeIf { it.isNotBlank() },
            sunRequirement = state.value.sunRequirement.takeIf { it.isNotBlank() },
            waterRequirement = state.value.waterRequirement.takeIf { it.isNotBlank() },
            soilType = state.value.soilType.takeIf { it.isNotBlank() },
            soilPh = state.value.soilPh.toDoubleOrNull(),
            sowingInstructions = state.value.sowingInstructions.takeIf { it.isNotBlank() },
            growingInstructions = state.value.growingInstructions.takeIf { it.isNotBlank() },
            harvestingInstructions = state.value.harvestingInstructions.takeIf { it.isNotBlank() },
            storageInstructions = state.value.storageInstructions.takeIf { it.isNotBlank() },
            companionPlants = state.value.companionPlants.takeIf { it.isNotBlank() },
            avoidPlants = state.value.avoidPlants.takeIf { it.isNotBlank() },
            tags = state.value.tags.takeIf { it.isNotBlank() },
            notes = state.value.notes.takeIf { it.isNotBlank() },
            imageUrl = state.value.imageUrl.takeIf { it.isNotBlank() },
            source = state.value.source.takeIf { it.isNotBlank() },
            isFavorite = state.value.isFavorite
        )

        viewModelScope.launch {
            try {
                if (state.value.seedId.isNotEmpty()) {
                    repository.updateSeed(seed)
                } else {
                    repository.insertSeed(seed)
                }
                _eventFlow.emit(UiEvent.SaveSeed)
            } catch (e: Exception) {
                Log.e(TAG, "Fel vid sparande av frö", e)
                _state.update { it.copy(error = "Ett fel uppstod: ${e.message}") }
            }
        }
    }

    private fun validateForm(): Boolean {
        if (state.value.name.isBlank()) {
            _state.update { it.copy(error = "Namn är obligatoriskt") }
            return false
        }
        return true
    }

    fun loadSeedById(id: String) {
        Log.d(TAG, "Laddar frö med ID: $id")
        loadSeed(id)
    }
}

data class AddEditSeedState(
    val seedId: String = "",
    val name: String = "",
    val scientificName: String = "",
    val species: String = "",
    val variety: String = "",
    val description: String = "",
    val category: PlantCategory? = null,
    val plantingDepth: String = "",
    val plantingDistance: String = "",
    val plantSpacing: String = "",
    val rowSpacing: String = "",
    val plantingDates: String = "",
    val daysToGermination: String = "",
    val daysToMaturity: String = "",
    val harvestPeriod: String = "",
    val lifespan: String = "",
    val hardinessZone: String = "",
    val sunRequirement: String = "",
    val waterRequirement: String = "",
    val soilType: String = "",
    val soilPh: String = "",
    val sowingInstructions: String = "",
    val growingInstructions: String = "",
    val harvestingInstructions: String = "",
    val storageInstructions: String = "",
    val companionPlants: String = "",
    val avoidPlants: String = "",
    val tags: String = "",
    val notes: String = "",
    val imageUrl: String = "",
    val source: String = "",
    val isFavorite: Boolean = false,
    val error: String? = null
)

sealed class AddEditSeedEvent {
    data class OnNameChange(val name: String) : AddEditSeedEvent()
    data class OnScientificNameChange(val scientificName: String) : AddEditSeedEvent()
    data class OnSpeciesChange(val species: String) : AddEditSeedEvent()
    data class OnVarietyChange(val variety: String) : AddEditSeedEvent()
    data class OnDescriptionChange(val description: String) : AddEditSeedEvent()
    data class OnCategoryChange(val category: PlantCategory) : AddEditSeedEvent()
    data class OnPlantingDepthChange(val plantingDepth: String) : AddEditSeedEvent()
    data class OnPlantingDistanceChange(val plantingDistance: String) : AddEditSeedEvent()
    data class OnPlantSpacingChange(val plantSpacing: String) : AddEditSeedEvent()
    data class OnRowSpacingChange(val rowSpacing: String) : AddEditSeedEvent()
    data class OnPlantingDatesChange(val plantingDates: String) : AddEditSeedEvent()
    data class OnDaysToGerminationChange(val daysToGermination: String) : AddEditSeedEvent()
    data class OnDaysToMaturityChange(val daysToMaturity: String) : AddEditSeedEvent()
    data class OnHarvestPeriodChange(val harvestPeriod: String) : AddEditSeedEvent()
    data class OnLifespanChange(val lifespan: String) : AddEditSeedEvent()
    data class OnHardinessZoneChange(val hardinessZone: String) : AddEditSeedEvent()
    data class OnSunRequirementChange(val sunRequirement: String) : AddEditSeedEvent()
    data class OnWaterRequirementChange(val waterRequirement: String) : AddEditSeedEvent()
    data class OnSoilTypeChange(val soilType: String) : AddEditSeedEvent()
    data class OnSoilPhChange(val soilPh: String) : AddEditSeedEvent()
    data class OnSowingInstructionsChange(val sowingInstructions: String) : AddEditSeedEvent()
    data class OnGrowingInstructionsChange(val growingInstructions: String) : AddEditSeedEvent()
    data class OnHarvestingInstructionsChange(val harvestingInstructions: String) : AddEditSeedEvent()
    data class OnStorageInstructionsChange(val storageInstructions: String) : AddEditSeedEvent()
    data class OnCompanionPlantsChange(val companionPlants: String) : AddEditSeedEvent()
    data class OnAvoidPlantsChange(val avoidPlants: String) : AddEditSeedEvent()
    data class OnTagsChange(val tags: String) : AddEditSeedEvent()
    data class OnNotesChange(val notes: String) : AddEditSeedEvent()
    data class OnImageUrlChange(val imageUrl: String) : AddEditSeedEvent()
    data class OnSourceChange(val source: String) : AddEditSeedEvent()
    data class OnFavoriteChange(val isFavorite: Boolean) : AddEditSeedEvent()
    object OnSaveClick : AddEditSeedEvent()
} 