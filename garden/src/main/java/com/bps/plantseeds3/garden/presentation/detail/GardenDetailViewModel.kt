package com.bps.plantseeds3.garden.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bps.plantseeds3.common.model.Resource
import com.bps.plantseeds3.garden.domain.model.Garden
import com.bps.plantseeds3.domain.model.Plant
import com.bps.plantseeds3.garden.domain.repository.GardenRepository
import com.bps.plantseeds3.domain.repository.PlantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class GardenDetailViewModel @Inject constructor(
    private val gardenRepository: GardenRepository,
    private val plantRepository: PlantRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    
    private val gardenId: String = checkNotNull(savedStateHandle["gardenId"])
    
    private val _state = MutableStateFlow(GardenDetailState())
    val state: StateFlow<GardenDetailState> = _state.asStateFlow()

    init {
        loadGardenDetails()
    }

    private fun loadGardenDetails() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            
            val garden = gardenRepository.getGardenById(gardenId)
            if (garden != null) {
                plantRepository.getPlantsByGardenId(gardenId)
                    .collect { plantsResource ->
                        when (plantsResource) {
                            is Resource.Success -> {
                                _state.update { 
                                    it.copy(
                                        isLoading = false,
                                        garden = garden,
                                        plants = plantsResource.data ?: emptyList(),
                                        error = null
                                    )
                                }
                            }
                            is Resource.Error -> {
                                _state.update { 
                                    it.copy(
                                        isLoading = false,
                                        garden = garden,
                                        error = plantsResource.message
                                    )
                                }
                            }
                            is Resource.Loading -> {
                                _state.update { 
                                    it.copy(
                                        isLoading = true,
                                        garden = garden
                                    )
                                }
                            }
                        }
                    }
            } else {
                _state.update { 
                    it.copy(
                        isLoading = false,
                        error = "Kunde inte hitta trädgården"
                    )
                }
            }
        }
    }

    fun onEvent(event: GardenDetailEvent) {
        when (event) {
            is GardenDetailEvent.LoadGarden -> {
                loadGardenDetails()
            }
            is GardenDetailEvent.ShowAddPlantDialog -> {
                _state.update { it.copy(showAddPlantDialog = true) }
            }
            is GardenDetailEvent.HideAddPlantDialog -> {
                _state.update { it.copy(showAddPlantDialog = false) }
            }
            is GardenDetailEvent.ShowEditPlantDialog -> {
                _state.update { it.copy(plantToEdit = event.plant) }
            }
            is GardenDetailEvent.HideEditPlantDialog -> {
                _state.update { it.copy(plantToEdit = null) }
            }
            is GardenDetailEvent.AddPlant -> {
                viewModelScope.launch {
                    val now = Instant.now()
                    val newPlant = Plant(
                        id = "",
                        name = event.name,
                        species = event.species,
                        description = event.description,
                        gardenId = gardenId,
                        lastWatered = null,
                        nextWatering = null,
                        createdAt = now,
                        updatedAt = now
                    )
                    when (val result = plantRepository.insertPlant(newPlant)) {
                        is Resource.Success -> {
                            loadGardenDetails()
                            _state.update { it.copy(showAddPlantDialog = false) }
                        }
                        is Resource.Error -> {
                            _state.update { it.copy(error = result.message) }
                        }
                        is Resource.Loading -> {
                            _state.update { it.copy(isLoading = true) }
                        }
                    }
                }
            }
            is GardenDetailEvent.EditPlant -> {
                viewModelScope.launch {
                    val updatedPlant = event.plant.copy(
                        name = event.name,
                        species = event.species,
                        description = event.description,
                        updatedAt = Instant.now()
                    )
                    when (val result = plantRepository.updatePlant(updatedPlant)) {
                        is Resource.Success -> {
                            loadGardenDetails()
                            _state.update { it.copy(plantToEdit = null) }
                        }
                        is Resource.Error -> {
                            _state.update { it.copy(error = result.message) }
                        }
                        is Resource.Loading -> {
                            _state.update { it.copy(isLoading = true) }
                        }
                    }
                }
            }
            is GardenDetailEvent.DeletePlant -> {
                viewModelScope.launch {
                    when (val result = plantRepository.deletePlant(event.plant.id)) {
                        is Resource.Success -> {
                            loadGardenDetails()
                        }
                        is Resource.Error -> {
                            _state.update { it.copy(error = result.message) }
                        }
                        is Resource.Loading -> {
                            _state.update { it.copy(isLoading = true) }
                        }
                    }
                }
            }
        }
    }
} 