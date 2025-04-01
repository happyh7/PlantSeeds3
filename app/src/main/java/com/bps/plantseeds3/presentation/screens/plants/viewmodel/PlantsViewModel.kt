package com.bps.plantseeds3.presentation.screens.plants.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bps.plantseeds3.common.model.Resource
import com.bps.plantseeds3.domain.model.Plant
import com.bps.plantseeds3.domain.repository.PlantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "PlantsViewModel"

@HiltViewModel
class PlantsViewModel @Inject constructor(
    private val plantRepository: PlantRepository
) : ViewModel() {

    private val _plants = MutableStateFlow<Resource<List<Plant>>>(Resource.Loading())
    val plants: StateFlow<Resource<List<Plant>>> = _plants.asStateFlow()

    private val _selectedGardenId = MutableStateFlow<String?>(null)
    val selectedGardenId: StateFlow<String?> = _selectedGardenId.asStateFlow()

    fun setSelectedGarden(gardenId: String?) {
        Log.d(TAG, "setSelectedGarden: gardenId = $gardenId")
        _selectedGardenId.value = gardenId
        loadPlants()
    }

    private fun loadPlants() {
        viewModelScope.launch {
            val gardenId = _selectedGardenId.value
            Log.d(TAG, "loadPlants: loading plants for gardenId = $gardenId")
            if (gardenId != null) {
                plantRepository.getPlantsByGardenId(gardenId).collect { result ->
                    Log.d(TAG, "loadPlants: received result = $result")
                    _plants.value = result
                }
            } else {
                Log.d(TAG, "loadPlants: no gardenId selected, returning empty list")
                _plants.value = Resource.Success(emptyList())
            }
        }
    }
} 