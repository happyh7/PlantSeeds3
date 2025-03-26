package com.bps.plantseeds3.presentation.gardens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bps.plantseeds3.data.local.entity.Garden
import com.bps.plantseeds3.domain.repository.GardenRepository
import com.bps.plantseeds3.domain.repository.PlantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GardenViewModel @Inject constructor(
    private val repository: GardenRepository,
    private val plantRepository: PlantRepository
) : ViewModel() {

    private val _state = MutableStateFlow(GardenState())
    val state: StateFlow<GardenState> = _state.asStateFlow()

    init {
        loadGardens()
    }

    private fun loadGardens() {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true)
                Log.d("GardenViewModel", "Hämtar alla trädgårdar")
                repository.getAllGardens().collect { gardens ->
                    Log.d("GardenViewModel", "Hittade ${gardens.size} trädgårdar")
                    _state.value = _state.value.copy(
                        gardens = gardens,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                Log.e("GardenViewModel", "Fel vid hämtning av trädgårdar: ${e.message}")
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Kunde inte hämta trädgårdar: ${e.message}"
                )
            }
        }
    }

    suspend fun getPlantCountForGarden(gardenId: String): Int {
        return try {
            plantRepository.getPlantsByGardenId(gardenId).first().size
        } catch (e: Exception) {
            Log.e("GardenViewModel", "Fel vid hämtning av antal växter: ${e.message}")
            0
        }
    }

    fun deleteGarden(garden: Garden) {
        viewModelScope.launch {
            try {
                val plantCount = getPlantCountForGarden(garden.id)
                if (plantCount > 0) {
                    _state.value = _state.value.copy(
                        gardenToDelete = garden,
                        plantCountToDelete = plantCount,
                        showDeleteWarning = true
                    )
                } else {
                    performDeleteGarden(garden)
                }
            } catch (e: Exception) {
                Log.e("GardenViewModel", "Fel vid kontroll av växter: ${e.message}")
                _state.value = _state.value.copy(
                    error = "Kunde inte kontrollera växter: ${e.message}"
                )
            }
        }
    }

    fun confirmDeleteGarden() {
        _state.value.gardenToDelete?.let { garden ->
            performDeleteGarden(garden)
        }
        _state.value = _state.value.copy(
            showDeleteWarning = false,
            gardenToDelete = null,
            plantCountToDelete = 0
        )
    }

    fun cancelDeleteGarden() {
        _state.value = _state.value.copy(
            showDeleteWarning = false,
            gardenToDelete = null,
            plantCountToDelete = 0
        )
    }

    private fun performDeleteGarden(garden: Garden) {
        viewModelScope.launch {
            try {
                Log.d("GardenViewModel", "Tar bort trädgård: ${garden.name}")
                repository.deleteGarden(garden)
                Log.d("GardenViewModel", "Trädgård borttagen framgångsrikt")
                loadGardens()
            } catch (e: Exception) {
                Log.e("GardenViewModel", "Fel vid borttagning av trädgård: ${e.message}")
                _state.value = _state.value.copy(
                    error = "Kunde inte ta bort trädgården: ${e.message}"
                )
            }
        }
    }
}

data class GardenState(
    val gardens: List<Garden> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val showDeleteWarning: Boolean = false,
    val gardenToDelete: Garden? = null,
    val plantCountToDelete: Int = 0
) 