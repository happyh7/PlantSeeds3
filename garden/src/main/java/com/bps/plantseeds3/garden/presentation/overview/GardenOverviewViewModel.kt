package com.bps.plantseeds3.garden.presentation.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bps.plantseeds3.garden.domain.model.Garden
import com.bps.plantseeds3.garden.domain.usecase.AddGardenUseCase
import com.bps.plantseeds3.garden.domain.usecase.GetGardensUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GardenOverviewViewModel @Inject constructor(
    private val getGardensUseCase: GetGardensUseCase,
    private val addGardenUseCase: AddGardenUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(GardenOverviewState())
    val state: StateFlow<GardenOverviewState> = _state.asStateFlow()

    init {
        observeGardens()
    }

    fun onEvent(event: GardenOverviewEvent) {
        when (event) {
            is GardenOverviewEvent.LoadGardens -> observeGardens()
            is GardenOverviewEvent.AddGarden -> addGarden(event.garden)
            is GardenOverviewEvent.DeleteGarden -> deleteGarden(event.garden)
            GardenOverviewEvent.ShowAddGardenDialog -> showAddGardenDialog()
            GardenOverviewEvent.HideAddGardenDialog -> hideAddGardenDialog()
        }
    }

    private fun observeGardens() {
        getGardensUseCase()
            .onEach { gardens ->
                _state.update { it.copy(
                    gardens = gardens,
                    isLoading = false,
                    error = null
                ) }
            }
            .catch { e ->
                Timber.e(e, "Fel vid hämtning av trädgårdar")
                _state.update { it.copy(
                    isLoading = false,
                    error = "Kunde inte hämta trädgårdar: ${e.localizedMessage}"
                ) }
            }
            .launchIn(viewModelScope)
    }

    private fun addGarden(garden: Garden) {
        viewModelScope.launch {
            try {
                addGardenUseCase(garden)
                _state.update { it.copy(
                    isAddGardenDialogVisible = false,
                    error = null
                ) }
            } catch (e: Exception) {
                Timber.e(e, "Fel vid tillägg av trädgård")
                _state.update { it.copy(
                    error = "Kunde inte lägga till trädgård: ${e.localizedMessage}"
                ) }
            }
        }
    }

    private fun deleteGarden(garden: Garden) {
        // TODO: Implementera borttagning av trädgård
    }

    private fun showAddGardenDialog() {
        _state.update { it.copy(isAddGardenDialogVisible = true) }
    }

    private fun hideAddGardenDialog() {
        _state.update { it.copy(isAddGardenDialogVisible = false) }
    }
} 