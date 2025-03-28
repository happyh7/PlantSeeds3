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

enum class SortOrder {
    NAME_ASC, NAME_DESC,
    SPECIES_ASC, SPECIES_DESC,
    CATEGORY_ASC, CATEGORY_DESC
}

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
                        filteredSeeds = filterAndSortSeeds(seeds),
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

    fun onEvent(event: SeedBankEvent) {
        when (event) {
            is SeedBankEvent.DeleteSeed -> {
                deleteSeed(event.seed)
            }
            is SeedBankEvent.SearchSeeds -> {
                _state.value = _state.value.copy(
                    searchQuery = event.query,
                    filteredSeeds = filterAndSortSeeds(_state.value.seeds)
                )
            }
            is SeedBankEvent.SortSeeds -> {
                _state.value = _state.value.copy(
                    sortOrder = event.sortOrder,
                    filteredSeeds = filterAndSortSeeds(_state.value.seeds)
                )
            }
            is SeedBankEvent.FilterByCategory -> {
                _state.value = _state.value.copy(
                    selectedCategory = event.category,
                    filteredSeeds = filterAndSortSeeds(_state.value.seeds)
                )
            }
        }
    }

    private fun filterAndSortSeeds(seeds: List<Seed>): List<Seed> {
        var filteredSeeds = seeds

        // Filtrera efter sökfråga
        if (_state.value.searchQuery.isNotBlank()) {
            filteredSeeds = filteredSeeds.filter { seed ->
                seed.name.contains(_state.value.searchQuery, ignoreCase = true) ||
                seed.species?.contains(_state.value.searchQuery, ignoreCase = true) == true ||
                seed.category?.contains(_state.value.searchQuery, ignoreCase = true) == true
            }
        }

        // Filtrera efter kategori
        if (_state.value.selectedCategory != null) {
            filteredSeeds = filteredSeeds.filter { seed ->
                seed.category == _state.value.selectedCategory
            }
        }

        // Sortera
        filteredSeeds = when (_state.value.sortOrder) {
            SortOrder.NAME_ASC -> filteredSeeds.sortedBy { it.name }
            SortOrder.NAME_DESC -> filteredSeeds.sortedByDescending { it.name }
            SortOrder.SPECIES_ASC -> filteredSeeds.sortedBy { it.species }
            SortOrder.SPECIES_DESC -> filteredSeeds.sortedByDescending { it.species }
            SortOrder.CATEGORY_ASC -> filteredSeeds.sortedBy { it.category }
            SortOrder.CATEGORY_DESC -> filteredSeeds.sortedByDescending { it.category }
        }

        return filteredSeeds
    }

    private fun deleteSeed(seed: Seed) {
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
    val filteredSeeds: List<Seed> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val searchQuery: String = "",
    val sortOrder: SortOrder = SortOrder.NAME_ASC,
    val selectedCategory: String? = null
)

sealed class SeedBankEvent {
    data class DeleteSeed(val seed: Seed) : SeedBankEvent()
    data class SearchSeeds(val query: String) : SeedBankEvent()
    data class SortSeeds(val sortOrder: SortOrder) : SeedBankEvent()
    data class FilterByCategory(val category: String?) : SeedBankEvent()
} 