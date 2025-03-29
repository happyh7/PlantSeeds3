package com.bps.plantseeds3.presentation.plants

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bps.plantseeds3.domain.model.Plant
import com.bps.plantseeds3.domain.model.PlantCategory
import com.bps.plantseeds3.domain.model.PlantStatus
import com.bps.plantseeds3.domain.repository.GardenRepository
import com.bps.plantseeds3.domain.repository.PlantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PlantViewModel @Inject constructor(
    private val repository: PlantRepository,
    private val gardenRepository: GardenRepository
) : ViewModel() {

    private val _state = MutableStateFlow(PlantState())
    val state: StateFlow<PlantState> = _state.asStateFlow()

    private val gardenNames = mutableMapOf<String, String>()
    private var currentGardenId: String = ""

    fun setGardenId(gardenId: String) {
        currentGardenId = gardenId
        loadPlants()
        loadGardenName(gardenId)
    }

    private fun loadPlants() {
        viewModelScope.launch {
            repository.getAllPlants().collect { plants ->
                _state.value = _state.value.copy(
                    plants = if (currentGardenId.isNotEmpty()) {
                        plants.filter { it.gardenId == currentGardenId }
                    } else {
                        plants
                    }
                )
                plants.forEach { plant ->
                    if (!gardenNames.containsKey(plant.gardenId)) {
                        loadGardenName(plant.gardenId)
                    }
                }
            }
        }
    }

    private fun loadGardenName(gardenId: String) {
        viewModelScope.launch {
            try {
                gardenRepository.getGardenById(gardenId)?.let { garden ->
                    gardenNames[gardenId] = garden.name
                }
            } catch (e: Exception) {
                Log.e("PlantViewModel", "Fel vid hämtning av trädgårdsnamn: ${e.message}")
                gardenNames[gardenId] = "Okänd trädgård"
            }
        }
    }

    fun getGardenName(gardenId: String): String {
        return gardenNames[gardenId] ?: "Okänd trädgård"
    }

    fun addPlant(
        name: String,
        species: String,
        plantingDate: LocalDate? = null,
        description: String? = null,
        notes: String? = null
    ) {
        viewModelScope.launch {
            try {
                val plant = Plant(
                    id = UUID.randomUUID().toString(),
                    name = name.trim(),
                    scientificName = null,
                    species = species.trim(),
                    variety = null,
                    description = description?.trim(),
                    category = PlantCategory.OTHER,
                    status = PlantStatus.SEED,
                    plantingDate = plantingDate?.toString(),
                    harvestDate = null,
                    sunRequirement = null,
                    waterRequirement = null,
                    soilRequirement = null,
                    notes = notes?.trim(),
                    gardenId = currentGardenId
                )
                
                Log.d("PlantViewModel", "Sparar växt: ${plant.name}")
                repository.insertPlant(plant)
                Log.d("PlantViewModel", "Växt sparad framgångsrikt")
                loadPlants()
            } catch (e: Exception) {
                Log.e("PlantViewModel", "Fel vid sparning av växt: ${e.message}")
                _state.value = _state.value.copy(
                    error = "Kunde inte spara växten: ${e.message}"
                )
            }
        }
    }

    fun updatePlant(plant: Plant) {
        viewModelScope.launch {
            try {
                Log.d("PlantViewModel", "Uppdaterar växt: ${plant.name}")
                repository.updatePlant(plant)
                Log.d("PlantViewModel", "Växt uppdaterad framgångsrikt")
                loadPlants()
            } catch (e: Exception) {
                Log.e("PlantViewModel", "Fel vid uppdatering av växt: ${e.message}")
                _state.value = _state.value.copy(
                    error = "Kunde inte uppdatera växten: ${e.message}"
                )
            }
        }
    }

    fun deletePlant(plant: Plant) {
        viewModelScope.launch {
            try {
                Log.d("PlantViewModel", "Tar bort växt: ${plant.name}")
                repository.deletePlant(plant)
                Log.d("PlantViewModel", "Växt borttagen framgångsrikt")
                loadPlants()
            } catch (e: Exception) {
                Log.e("PlantViewModel", "Fel vid borttagning av växt: ${e.message}")
                _state.value = _state.value.copy(
                    error = "Kunde inte ta bort växten: ${e.message}"
                )
            }
        }
    }
}

data class PlantState(
    val plants: List<Plant> = emptyList(),
    val error: String? = null
) 