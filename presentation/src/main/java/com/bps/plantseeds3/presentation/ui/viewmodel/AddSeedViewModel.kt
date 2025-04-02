package com.bps.plantseeds3.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bps.plantseeds3.domain.model.Resource
import com.bps.plantseeds3.domain.model.Seed
import com.bps.plantseeds3.domain.repository.SeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddSeedViewModel @Inject constructor(
    private val seedRepository: SeedRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddSeedUiState())
    val uiState: StateFlow<AddSeedUiState> = _uiState.asStateFlow()

    fun onNameChange(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    fun onSpeciesChange(species: String) {
        _uiState.value = _uiState.value.copy(species = species)
    }

    fun onDescriptionChange(description: String) {
        _uiState.value = _uiState.value.copy(description = description)
    }

    fun saveSeed(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val now = Instant.now()
                val seed = Seed(
                    id = UUID.randomUUID().toString(),
                    plantId = UUID.randomUUID().toString(), // TODO: Implementera Plant-hantering
                    name = _uiState.value.name,
                    species = _uiState.value.species.ifBlank { null },
                    description = _uiState.value.description.ifBlank { null },
                    plantingInstructions = null,
                    daysToGermination = null,
                    daysToHarvest = null,
                    lightNeeds = null,
                    waterNeeds = null,
                    soilType = null,
                    temperature = null,
                    spacing = null,
                    companionPlants = null,
                    avoidPlants = null,
                    imageUrl = null,
                    createdAt = now,
                    updatedAt = now
                )

                when (val result = seedRepository.insertSeed(seed)) {
                    is Resource.Success<*> -> {
                        onSuccess()
                    }
                    is Resource.Error<*> -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = result.message ?: "Ett fel uppstod"
                        )
                    }
                    else -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = "Okänt fel"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Kunde inte spara frö: ${e.message}"
                )
            }
        }
    }
}

data class AddSeedUiState(
    val name: String = "",
    val species: String = "",
    val description: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
) 