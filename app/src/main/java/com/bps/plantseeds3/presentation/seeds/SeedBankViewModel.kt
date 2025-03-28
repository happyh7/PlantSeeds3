package com.bps.plantseeds3.presentation.seeds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bps.plantseeds3.data.local.entity.Seed
import com.bps.plantseeds3.domain.repository.SeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeedBankViewModel @Inject constructor(
    private val repository: SeedRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SeedBankState())
    val state: StateFlow<SeedBankState> = _state.asStateFlow()

    init {
        loadSeeds()
    }

    private fun loadSeeds() {
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

data class SeedBankState(
    val seeds: List<Seed> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
) 