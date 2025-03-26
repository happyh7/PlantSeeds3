package com.bps.plantseeds3.presentation.seeds

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bps.plantseeds3.data.local.entity.Seed
import com.bps.plantseeds3.domain.use_case.seed.SeedUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeedViewModel @Inject constructor(
    private val seedUseCases: SeedUseCases
) : ViewModel() {

    private val _state = mutableStateOf(SeedState())
    val state: State<SeedState> = _state

    private var searchJob: Job? = null
    private var getAllJob: Job? = null

    init {
        getAllSeeds()
    }

    fun onEvent(event: SeedEvent) {
        when (event) {
            is SeedEvent.DeleteSeed -> {
                viewModelScope.launch {
                    seedUseCases.deleteSeed(event.seed)
                    getAllSeeds() // Uppdatera listan efter borttagning
                }
            }
            is SeedEvent.SearchSeeds -> {
                if (event.query.isBlank()) {
                    getAllSeeds()
                } else {
                    searchSeeds(event.query)
                }
            }
            is SeedEvent.UpdateSeed -> {
                viewModelScope.launch {
                    seedUseCases.updateSeed(event.seed)
                    getAllSeeds() // Uppdatera listan efter uppdatering
                }
            }
        }
    }

    private fun searchSeeds(query: String) {
        searchJob?.cancel()
        getAllJob?.cancel()
        
        _state.value = state.value.copy(
            isLoading = true
        )

        searchJob = seedUseCases.searchSeeds(query)
            .onEach { seeds ->
                _state.value = state.value.copy(
                    seeds = seeds,
                    isLoading = false
                )
            }
            .launchIn(viewModelScope)
    }

    private fun getAllSeeds() {
        searchJob?.cancel()
        getAllJob?.cancel()

        _state.value = state.value.copy(
            isLoading = true
        )

        getAllJob = seedUseCases.getSeeds()
            .onEach { seeds ->
                _state.value = state.value.copy(
                    seeds = seeds,
                    isLoading = false
                )
            }
            .launchIn(viewModelScope)
    }
} 