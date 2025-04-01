package com.bps.plantseeds3.presentation.screens.gardens.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bps.plantseeds3.common.model.Resource
import com.bps.plantseeds3.domain.model.Garden
import com.bps.plantseeds3.domain.repository.GardenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class AddGardenViewModel @Inject constructor(
    private val gardenRepository: GardenRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AddGardenUiState>(AddGardenUiState.Initial)
    val uiState: StateFlow<AddGardenUiState> = _uiState

    fun addGarden(name: String, description: String, location: String) {
        viewModelScope.launch {
            try {
                Log.d("AddGardenViewModel", "Börjar lägga till trädgård: $name")
                _uiState.value = AddGardenUiState.Loading
                
                val garden = Garden(
                    id = System.currentTimeMillis().toString(), // Använder timestamp som temporärt ID
                    name = name,
                    description = description,
                    location = location,
                    createdAt = Instant.now(),
                    updatedAt = Instant.now()
                )
                
                Log.d("AddGardenViewModel", "Skapade garden-objekt: $garden")
                
                when (val result = gardenRepository.insertGarden(garden)) {
                    is Resource.Success -> {
                        Log.d("AddGardenViewModel", "Trädgård lades till framgångsrikt")
                        _uiState.value = AddGardenUiState.Success
                    }
                    is Resource.Error -> {
                        Log.e("AddGardenViewModel", "Fel vid tillägg av trädgård: ${result.message}")
                        _uiState.value = AddGardenUiState.Error(result.message)
                    }
                    is Resource.Loading -> {
                        Log.d("AddGardenViewModel", "Laddar...")
                        _uiState.value = AddGardenUiState.Loading
                    }
                }
            } catch (e: Exception) {
                Log.e("AddGardenViewModel", "Ett oväntat fel uppstod", e)
                _uiState.value = AddGardenUiState.Error(e.message ?: "Ett fel uppstod")
            }
        }
    }
}

sealed class AddGardenUiState {
    object Initial : AddGardenUiState()
    object Loading : AddGardenUiState()
    object Success : AddGardenUiState()
    data class Error(val message: String) : AddGardenUiState()
} 