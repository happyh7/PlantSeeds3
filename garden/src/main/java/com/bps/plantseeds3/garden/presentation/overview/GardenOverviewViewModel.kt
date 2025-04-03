package com.bps.plantseeds3.garden.presentation.overview

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bps.plantseeds3.garden.domain.model.Garden
import com.bps.plantseeds3.garden.domain.repository.GardenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GardenOverviewViewModel @Inject constructor(
    private val repository: GardenRepository
) : ViewModel() {

    private val _state = MutableStateFlow(GardenOverviewState())
    val state: StateFlow<GardenOverviewState> = _state.asStateFlow()

    init {
        Log.d(TAG, "init: Loading gardens")
        onEvent(GardenOverviewEvent.LoadGardens)
    }

    fun onEvent(event: GardenOverviewEvent) {
        when (event) {
            is GardenOverviewEvent.LoadGardens -> {
                viewModelScope.launch {
                    _state.update { it.copy(isLoading = true) }
                    try {
                        repository.getGardens().collect { gardens ->
                            _state.update { 
                                it.copy(
                                    gardens = gardens,
                                    isLoading = false
                                )
                            }
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "Error loading gardens", e)
                        _state.update { 
                            it.copy(
                                error = "Kunde inte ladda trädgårdar: ${e.message}",
                                isLoading = false
                            )
                        }
                    }
                }
            }
            is GardenOverviewEvent.AddGarden -> {
                viewModelScope.launch {
                    try {
                        repository.insertGarden(event.garden)
                        _state.update { it.copy(isAddGardenDialogVisible = false) }
                    } catch (e: Exception) {
                        Log.e(TAG, "Error adding garden", e)
                        _state.update { 
                            it.copy(
                                error = "Kunde inte lägga till trädgård: ${e.message}"
                            )
                        }
                    }
                }
            }
            is GardenOverviewEvent.EditGarden -> {
                viewModelScope.launch {
                    try {
                        repository.updateGarden(event.garden)
                        _state.update { it.copy(isEditGardenDialogVisible = false) }
                    } catch (e: Exception) {
                        Log.e(TAG, "Error updating garden", e)
                        _state.update { 
                            it.copy(
                                error = "Kunde inte uppdatera trädgård: ${e.message}"
                            )
                        }
                    }
                }
            }
            is GardenOverviewEvent.DeleteGarden -> {
                viewModelScope.launch {
                    try {
                        repository.deleteGarden(event.garden)
                    } catch (e: Exception) {
                        Log.e(TAG, "Error deleting garden", e)
                        _state.update { 
                            it.copy(
                                error = "Kunde inte ta bort trädgård: ${e.message}"
                            )
                        }
                    }
                }
            }
            is GardenOverviewEvent.ShowAddGardenDialog -> {
                _state.update { it.copy(isAddGardenDialogVisible = true) }
            }
            is GardenOverviewEvent.HideAddGardenDialog -> {
                _state.update { it.copy(isAddGardenDialogVisible = false) }
            }
            is GardenOverviewEvent.ShowEditGardenDialog -> {
                _state.update { 
                    it.copy(
                        isEditGardenDialogVisible = true,
                        gardenToEdit = event.garden
                    )
                }
            }
            is GardenOverviewEvent.HideEditGardenDialog -> {
                _state.update { 
                    it.copy(
                        isEditGardenDialogVisible = false,
                        gardenToEdit = null
                    )
                }
            }
        }
    }

    companion object {
        private const val TAG = "GardenOverviewViewModel"
    }
} 