package com.bps.plantseeds3.presentation.screens.plants.viewmodel

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

@HiltViewModel
class PlantsViewModel @Inject constructor(
    private val repository: PlantRepository
) : ViewModel() {

    private val _plants = MutableStateFlow<Resource<List<Plant>>>(Resource.Loading())
    val plants: StateFlow<Resource<List<Plant>>> = _plants.asStateFlow()

    private val _selectedGardenId = MutableStateFlow<String?>(null)
    val selectedGardenId: StateFlow<String?> = _selectedGardenId.asStateFlow()

    init {
        loadPlants()
    }

    fun setSelectedGarden(gardenId: String?) {
        _selectedGardenId.value = gardenId
        loadPlants()
    }

    private fun loadPlants() {
        viewModelScope.launch {
            val gardenId = _selectedGardenId.value
            if (gardenId != null) {
                repository.getPlantsByGardenId(gardenId).collect { result ->
                    _plants.value = result
                }
            } else {
                _plants.value = Resource.Success(emptyList())
            }
        }
    }
} 