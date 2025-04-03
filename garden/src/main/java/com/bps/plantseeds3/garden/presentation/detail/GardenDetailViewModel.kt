package com.bps.plantseeds3.garden.presentation.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bps.plantseeds3.common.model.Resource
import com.bps.plantseeds3.domain.repository.PlantRepository
import com.bps.plantseeds3.garden.domain.repository.GardenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GardenDetailViewModel @Inject constructor(
    private val gardenRepository: GardenRepository,
    private val plantRepository: PlantRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(GardenDetailState())
    val state: StateFlow<GardenDetailState> = _state.asStateFlow()

    init {
        savedStateHandle.get<String>("gardenId")?.let { gardenId ->
            onEvent(GardenDetailEvent.LoadGarden(gardenId))
        }
    }

    fun onEvent(event: GardenDetailEvent) {
        when (event) {
            is GardenDetailEvent.LoadGarden -> {
                viewModelScope.launch {
                    _state.update { it.copy(isLoading = true) }
                    try {
                        gardenRepository.getGardenById(event.gardenId)?.let { garden ->
                            _state.update { it.copy(garden = garden) }
                            plantRepository.getPlantsByGardenId(garden.id).collect { result ->
                                when (result) {
                                    is Resource.Success -> {
                                        _state.update { 
                                            it.copy(
                                                plants = result.data,
                                                isLoading = false
                                            )
                                        }
                                    }
                                    is Resource.Error -> {
                                        _state.update { 
                                            it.copy(
                                                error = result.message,
                                                isLoading = false
                                            )
                                        }
                                    }
                                    is Resource.Loading -> {
                                        _state.update { it.copy(isLoading = true) }
                                    }
                                }
                            }
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "Error loading garden", e)
                        _state.update { 
                            it.copy(
                                error = "Kunde inte ladda trädgård: ${e.message}",
                                isLoading = false
                            )
                        }
                    }
                }
            }
            is GardenDetailEvent.AddPlant -> {
                viewModelScope.launch {
                    when (val result = plantRepository.insertPlant(event.plant)) {
                        is Resource.Success -> {
                            _state.update { it.copy(isAddPlantDialogVisible = false) }
                        }
                        is Resource.Error -> {
                            _state.update { 
                                it.copy(
                                    error = result.message,
                                    isAddPlantDialogVisible = false
                                )
                            }
                        }
                        is Resource.Loading -> {
                            _state.update { it.copy(isLoading = true) }
                        }
                    }
                }
            }
            is GardenDetailEvent.EditPlant -> {
                viewModelScope.launch {
                    when (val result = plantRepository.updatePlant(event.plant)) {
                        is Resource.Success -> {
                            _state.update { it.copy(isEditPlantDialogVisible = false) }
                        }
                        is Resource.Error -> {
                            _state.update { 
                                it.copy(
                                    error = result.message,
                                    isEditPlantDialogVisible = false
                                )
                            }
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
                            // Plant will be automatically removed from the list by Flow
                        }
                        is Resource.Error -> {
                            _state.update { 
                                it.copy(error = result.message)
                            }
                        }
                        is Resource.Loading -> {
                            _state.update { it.copy(isLoading = true) }
                        }
                    }
                }
            }
            is GardenDetailEvent.ShowAddPlantDialog -> {
                _state.update { it.copy(isAddPlantDialogVisible = true) }
            }
            is GardenDetailEvent.HideAddPlantDialog -> {
                _state.update { it.copy(isAddPlantDialogVisible = false) }
            }
            is GardenDetailEvent.ShowEditPlantDialog -> {
                _state.update { 
                    it.copy(
                        isEditPlantDialogVisible = true,
                        plantToEdit = event.plant
                    )
                }
            }
            is GardenDetailEvent.HideEditPlantDialog -> {
                _state.update { 
                    it.copy(
                        isEditPlantDialogVisible = false,
                        plantToEdit = null
                    )
                }
            }
        }
    }

    companion object {
        private const val TAG = "GardenDetailViewModel"
    }
} 