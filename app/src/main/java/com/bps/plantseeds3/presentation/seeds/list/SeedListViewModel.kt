package com.bps.plantseeds3.presentation.seeds.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bps.plantseeds3.domain.repository.SeedRepository
import com.bps.plantseeds3.domain.model.Seed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SeedListState(
    val seeds: List<Seed> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class SeedListViewModel @Inject constructor(
    private val repository: SeedRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SeedListState())
    val state: StateFlow<SeedListState> = _state.asStateFlow()

    init {
        loadSeeds()
    }

    private fun loadSeeds() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                repository.getAllSeeds().collect { seeds ->
                    _state.value = _state.value.copy(
                        seeds = seeds,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message ?: "Ett fel uppstod vid hämtning av frön",
                    isLoading = false
                )
            }
        }
    }
} 