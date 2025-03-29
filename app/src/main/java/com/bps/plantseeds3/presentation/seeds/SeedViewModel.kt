package com.bps.plantseeds3.presentation.seeds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bps.plantseeds3.domain.model.Seed
import com.bps.plantseeds3.domain.repository.SeedRepository
import com.bps.plantseeds3.domain.use_case.seed.SeedUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeedViewModel @Inject constructor(
    private val seedUseCases: SeedUseCases,
    private val repository: SeedRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SeedState())
    val state: StateFlow<SeedState> = _state.asStateFlow()

    init {
        loadSeeds()
    }

    fun onEvent(event: SeedEvent) {
        when (event) {
            is SeedEvent.DeleteSeed -> {
                deleteSeed(event.seed)
            }
            is SeedEvent.SearchSeeds -> {
                if (event.query.isBlank()) {
                    loadSeeds()
                } else {
                    searchSeeds(event.query)
                }
            }
            is SeedEvent.UpdateSeed -> {
                viewModelScope.launch {
                    seedUseCases.updateSeed(event.seed)
                    loadSeeds()
                }
            }
        }
    }

    private fun searchSeeds(query: String) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true)
                repository.searchSeeds(query).collect { seeds ->
                    _state.value = _state.value.copy(
                        seeds = seeds,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Kunde inte söka efter frön: ${e.message}"
                )
            }
        }
    }

    fun loadSeeds() {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true)
                repository.getAllSeeds().collect { seeds ->
                    _state.value = _state.value.copy(
                        seeds = seeds,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Kunde inte hämta frön: ${e.message}"
                )
            }
        }
    }

    fun deleteSeed(seed: Seed) {
        viewModelScope.launch {
            try {
                repository.deleteSeed(seed)
                loadSeeds()
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = "Kunde inte ta bort fröet: ${e.message}"
                )
            }
        }
    }
} 