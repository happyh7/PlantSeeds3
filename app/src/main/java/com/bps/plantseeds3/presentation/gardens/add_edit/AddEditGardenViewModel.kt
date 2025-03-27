package com.bps.plantseeds3.presentation.gardens.add_edit

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bps.plantseeds3.data.local.entity.Garden
import com.bps.plantseeds3.data.local.entity.GardenFormData
import com.bps.plantseeds3.domain.repository.GardenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

sealed class UiEvent {
    object SaveGarden : UiEvent()
    data class ShowError(val message: String) : UiEvent()
    object NavigateBack : UiEvent()
}

@HiltViewModel
class AddEditGardenViewModel @Inject constructor(
    private val repository: GardenRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _formData = mutableStateOf(GardenFormData())
    val formData: State<GardenFormData> = _formData

    private val _validationErrors = mutableStateOf<List<String>>(emptyList())
    val validationErrors: State<List<String>> = _validationErrors

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentGardenId: String? = null

    private val _state = MutableStateFlow(AddEditGardenState())
    val state: StateFlow<AddEditGardenState> = _state.asStateFlow()

    init {
        savedStateHandle.get<String>("gardenId")?.let { gardenId ->
            if (gardenId != "-1") {
                currentGardenId = gardenId
                viewModelScope.launch {
                    try {
                        repository.getGardenById(gardenId)?.let { garden ->
                            _formData.value = GardenFormData(
                                name = garden.name,
                                location = garden.location ?: "",
                                description = garden.description ?: "",
                                size = garden.size?.toString() ?: "",
                                width = garden.width?.toString() ?: "",
                                length = garden.length?.toString() ?: "",
                                elevation = garden.elevation?.toString() ?: "",
                                slope = garden.slope?.toString() ?: "",
                                soilType = garden.soilType ?: "",
                                sunExposure = garden.sunExposure ?: "",
                                irrigation = garden.irrigation ?: "",
                                fence = garden.fence ?: "",
                                notes = garden.notes ?: ""
                            )
                            Log.d("AddEditGardenViewModel", "Hämtade trädgård: ${garden.name}")
                        }
                    } catch (e: Exception) {
                        Log.e("AddEditGardenViewModel", "Fel vid hämtning av trädgård", e)
                        _state.value = _state.value.copy(
                            error = "Kunde inte hämta trädgården: ${e.message}"
                        )
                    }
                }
            }
        }
    }

    private fun validateForm(): Boolean {
        val errors = mutableListOf<String>()
        
        if (_formData.value.name.isBlank()) {
            errors.add("Namn är obligatoriskt")
        }
        
        _validationErrors.value = errors
        return errors.isEmpty()
    }

    fun onEvent(event: AddEditGardenEvent) {
        when (event) {
            is AddEditGardenEvent.EnteredName -> {
                _formData.value = _formData.value.copy(name = event.value)
            }
            is AddEditGardenEvent.EnteredLocation -> {
                _formData.value = _formData.value.copy(location = event.value)
            }
            is AddEditGardenEvent.EnteredDescription -> {
                _formData.value = _formData.value.copy(description = event.value)
            }
            is AddEditGardenEvent.EnteredSize -> {
                _formData.value = _formData.value.copy(size = event.value)
            }
            is AddEditGardenEvent.EnteredWidth -> {
                _formData.value = _formData.value.copy(width = event.value)
            }
            is AddEditGardenEvent.EnteredLength -> {
                _formData.value = _formData.value.copy(length = event.value)
            }
            is AddEditGardenEvent.EnteredElevation -> {
                _formData.value = _formData.value.copy(elevation = event.value)
            }
            is AddEditGardenEvent.EnteredSlope -> {
                _formData.value = _formData.value.copy(slope = event.value)
            }
            is AddEditGardenEvent.EnteredSoilType -> {
                _formData.value = _formData.value.copy(soilType = event.value)
            }
            is AddEditGardenEvent.EnteredSunExposure -> {
                _formData.value = _formData.value.copy(sunExposure = event.value)
            }
            is AddEditGardenEvent.EnteredIrrigation -> {
                _formData.value = _formData.value.copy(irrigation = event.value)
            }
            is AddEditGardenEvent.EnteredFence -> {
                _formData.value = _formData.value.copy(fence = event.value)
            }
            is AddEditGardenEvent.EnteredNotes -> {
                _formData.value = _formData.value.copy(notes = event.value)
            }
            is AddEditGardenEvent.SaveGarden -> {
                viewModelScope.launch {
                    try {
                        if (!validateForm()) {
                            _eventFlow.emit(UiEvent.ShowError("Vänligen korrigera följande fel:\n" + _validationErrors.value.joinToString("\n")))
                            return@launch
                        }

                        val garden = Garden(
                            id = currentGardenId ?: UUID.randomUUID().toString(),
                            name = _formData.value.name.trim(),
                            location = _formData.value.location.trim().ifBlank { null },
                            description = _formData.value.description.trim().ifBlank { null },
                            size = _formData.value.size.toFloatOrNull(),
                            width = _formData.value.width.toFloatOrNull(),
                            length = _formData.value.length.toFloatOrNull(),
                            elevation = _formData.value.elevation.toFloatOrNull(),
                            slope = _formData.value.slope.toFloatOrNull(),
                            soilType = _formData.value.soilType.trim().ifBlank { null },
                            sunExposure = _formData.value.sunExposure.trim().ifBlank { null },
                            irrigation = _formData.value.irrigation.trim().ifBlank { null },
                            fence = _formData.value.fence.trim().ifBlank { null },
                            notes = _formData.value.notes.trim().ifBlank { null },
                            createdAt = LocalDate.now(),
                            updatedAt = LocalDate.now()
                        )

                        if (currentGardenId != null) {
                            repository.updateGarden(garden)
                            _eventFlow.emit(UiEvent.SaveGarden)
                        } else {
                            repository.insertGarden(garden)
                            _eventFlow.emit(UiEvent.SaveGarden)
                        }

                        _eventFlow.emit(UiEvent.NavigateBack)
                    } catch (e: Exception) {
                        Log.e("AddEditGardenViewModel", "Fel vid sparning av trädgård", e)
                        _eventFlow.emit(UiEvent.ShowError("Ett fel uppstod när trädgården skulle sparas: ${e.message}"))
                    }
                }
            }
        }
    }

    fun validateAndSaveGarden(
        name: String,
        location: String,
        description: String,
        size: String,
        width: String,
        length: String,
        elevation: String,
        slope: String,
        soilType: String,
        sunExposure: String,
        irrigation: String,
        fence: String,
        notes: String
    ) {
        if (name.isBlank()) {
            _state.value = _state.value.copy(
                error = "Namn är obligatoriskt"
            )
            return
        }

        val garden = Garden(
            id = currentGardenId ?: UUID.randomUUID().toString(),
            name = name.trim(),
            location = location.trim().takeIf { it.isNotBlank() },
            description = description.trim().takeIf { it.isNotBlank() },
            size = size.trim().toFloatOrNull(),
            width = width.trim().toFloatOrNull(),
            length = length.trim().toFloatOrNull(),
            elevation = elevation.trim().toFloatOrNull(),
            slope = slope.trim().toFloatOrNull(),
            soilType = soilType.trim().takeIf { it.isNotBlank() },
            sunExposure = sunExposure.trim().takeIf { it.isNotBlank() },
            irrigation = irrigation.trim().takeIf { it.isNotBlank() },
            fence = fence.trim().takeIf { it.isNotBlank() },
            notes = notes.trim().takeIf { it.isNotBlank() },
            createdAt = LocalDate.now(),
            updatedAt = LocalDate.now()
        )

        viewModelScope.launch {
            try {
                Log.d("AddEditGardenViewModel", "Sparar trädgård: ${garden.name}")
                if (currentGardenId != null) {
                    repository.updateGarden(garden)
                } else {
                    repository.insertGarden(garden)
                }
                Log.d("AddEditGardenViewModel", "Trädgård sparad framgångsrikt")
                _state.value = _state.value.copy(
                    isSaved = true,
                    error = null
                )
            } catch (e: Exception) {
                Log.e("AddEditGardenViewModel", "Fel vid sparning av trädgård: ${e.message}")
                _state.value = _state.value.copy(
                    error = "Kunde inte spara trädgården: ${e.message}"
                )
            }
        }
    }
}

data class AddEditGardenState(
    val name: String = "",
    val location: String = "",
    val description: String = "",
    val size: String = "",
    val width: String = "",
    val length: String = "",
    val elevation: String = "",
    val slope: String = "",
    val soilType: String = "",
    val sunExposure: String = "",
    val irrigation: String = "",
    val fence: String = "",
    val notes: String = "",
    val error: String? = null,
    val isSaved: Boolean = false
) 