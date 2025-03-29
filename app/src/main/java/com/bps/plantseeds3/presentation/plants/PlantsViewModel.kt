package com.bps.plantseeds3.presentation.plants

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bps.plantseeds3.domain.model.Garden
import com.bps.plantseeds3.domain.model.Plant
import com.bps.plantseeds3.domain.repository.GardenRepository
import com.bps.plantseeds3.domain.repository.PlantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlantsViewModel @Inject constructor(
    private val plantRepository: PlantRepository,
    private val gardenRepository: GardenRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(PlantsState())
    val state: StateFlow<PlantsState> = _state.asStateFlow()

    private var currentGardenId: String? = null

    init {
        loadData()
    }

    fun setGardenId(gardenId: String) {
        currentGardenId = gardenId
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true)
                
                // Hämta trädgårdar och växter med collect för realtidsuppdateringar
                combine(
                    gardenRepository.getAllGardens(),
                    plantRepository.getAllPlants()
                ) { gardens, plants ->
                    // Beräkna antal växter per trädgård
                    val gardenPlantCounts = plants.groupBy { plant -> plant.gardenId }
                        .mapValues { (_, plants) -> plants.size }
                    
                    // Sortera trädgårdar efter antal växter (mest först)
                    val sortedGardens = gardens.sortedByDescending { garden ->
                        gardenPlantCounts[garden.id] ?: 0
                    }

                    // Filtrera växter baserat på vald trädgård
                    val filteredPlants = if (currentGardenId != null) {
                        plants.filter { it.gardenId == currentGardenId }
                    } else {
                        plants
                    }
                    
                    _state.value = _state.value.copy(
                        plants = filteredPlants,
                        gardens = sortedGardens,
                        gardenPlantCounts = gardenPlantCounts,
                        isLoading = false,
                        error = null
                    )
                }.collect()
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = "Kunde inte ladda data: ${e.message}",
                    isLoading = false
                )
            }
        }
    }

    fun deletePlant(plant: Plant) {
        viewModelScope.launch {
            try {
                plantRepository.deletePlant(plant)
                Log.d("PlantsViewModel", "Växt borttagen framgångsrikt")
                loadData()
            } catch (e: Exception) {
                Log.e("PlantsViewModel", "Fel vid borttagning av växt: ${e.message}")
                _state.value = _state.value.copy(error = "Kunde inte ta bort växten: ${e.message}")
            }
        }
    }

    fun saveSelectedGardenId(gardenId: String) {
        viewModelScope.launch {
            try {
                context.getSharedPreferences("plants_prefs", Context.MODE_PRIVATE)
                    .edit()
                    .putString("selected_garden_id", gardenId)
                    .apply()
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = "Kunde inte spara vald trädgård: ${e.message}")
            }
        }
    }

    suspend fun getLastSelectedGardenId(): String? {
        return try {
            context.getSharedPreferences("plants_prefs", Context.MODE_PRIVATE)
                .getString("selected_garden_id", null)
        } catch (e: Exception) {
            _state.value = _state.value.copy(error = "Kunde inte hämta vald trädgård: ${e.message}")
            null
        }
    }

    fun clearError() {
        _state.value = _state.value.copy(error = null)
    }
}

data class PlantsState(
    val plants: List<Plant> = emptyList(),
    val gardens: List<Garden> = emptyList(),
    val gardenPlantCounts: Map<String, Int> = emptyMap(),
    val isLoading: Boolean = true,
    val error: String? = null
) 