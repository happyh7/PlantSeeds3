package com.bps.plantseeds3.presentation.gardens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bps.plantseeds3.domain.model.Garden
import com.bps.plantseeds3.domain.model.Plant
import com.bps.plantseeds3.domain.repository.GardenRepository
import com.bps.plantseeds3.domain.repository.PlantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GardenDetailsViewModel @Inject constructor(
    private val gardenRepository: GardenRepository,
    private val plantRepository: PlantRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(GardenDetailsState())
    val state: StateFlow<GardenDetailsState> = _state.asStateFlow()

    fun loadGarden(gardenId: String) {
        viewModelScope.launch {
            try {
                // Hämta trädgård
                gardenRepository.getGardenById(gardenId)?.let { garden ->
                    _state.value = _state.value.copy(garden = garden)
                    
                    // Hämta växter i trädgården
                    plantRepository.getPlantsByGardenId(gardenId).collect { plants ->
                        _state.value = _state.value.copy(plants = plants)
                    }
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }
}

data class GardenDetailsState(
    val garden: Garden? = null,
    val plants: List<Plant> = emptyList(),
    val error: String? = null
) 