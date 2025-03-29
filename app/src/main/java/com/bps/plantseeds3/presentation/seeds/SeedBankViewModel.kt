package com.bps.plantseeds3.presentation.seeds

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bps.plantseeds3.domain.model.Seed
import com.bps.plantseeds3.domain.model.PlantCategory
import com.bps.plantseeds3.domain.repository.SeedRepository
import com.bps.plantseeds3.domain.use_case.seed.SeedUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class SortOrder {
    NAME_ASC, NAME_DESC,
    SPECIES_ASC, SPECIES_DESC,
    CATEGORY_ASC, CATEGORY_DESC
}

@HiltViewModel
class SeedBankViewModel @Inject constructor(
    private val repository: SeedRepository,
    private val seedUseCases: SeedUseCases
) : ViewModel() {

    private val TAG = "SeedBankViewModel"
    private val _state = MutableStateFlow(SeedBankState())
    val state: StateFlow<SeedBankState> = _state.asStateFlow()

    init {
        Log.d(TAG, "SeedBankViewModel initieras")
        loadSeeds()
    }

    private fun loadSeeds() {
        Log.d(TAG, "Hämtar frön från databasen")
        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true, error = null) }
                repository.getAllSeeds()
                    .catch { e ->
                        Log.e(TAG, "Fel vid hämtning av frön", e)
                        _state.update { it.copy(error = "Kunde inte hämta frön: ${e.message}") }
                    }
                    .collect { seeds ->
                        Log.d(TAG, "Hämtade ${seeds.size} frön: ${seeds.map { it.name }}")
                        _state.update { currentState ->
                            currentState.copy(
                                seeds = seeds,
                                filteredSeeds = seeds,
                                isLoading = false,
                                error = null
                            )
                        }
                    }
            } catch (e: Exception) {
                Log.e(TAG, "Oväntat fel vid hämtning av frön", e)
                _state.update { it.copy(error = "Ett oväntat fel uppstod: ${e.message}") }
            }
        }
    }

    fun onEvent(event: SeedBankEvent) {
        when (event) {
            is SeedBankEvent.SearchSeeds -> {
                Log.d(TAG, "Söker efter frön med query: ${event.query}")
                _state.update { currentState ->
                    currentState.copy(
                        searchQuery = event.query,
                        filteredSeeds = currentState.seeds.filter { seed ->
                            seed.name.contains(event.query, ignoreCase = true) ||
                            seed.scientificName?.contains(event.query, ignoreCase = true) == true
                        }
                    )
                }
            }
            is SeedBankEvent.SortSeeds -> {
                Log.d(TAG, "Sorterar frön efter: ${event.order}")
                _state.update { currentState ->
                    currentState.copy(
                        filteredSeeds = currentState.filteredSeeds.sortedWith(
                            when (event.order) {
                                SortOrder.NAME_ASC -> compareBy { it.name }
                                SortOrder.NAME_DESC -> compareByDescending { it.name }
                                SortOrder.SPECIES_ASC -> compareBy { it.scientificName }
                                SortOrder.SPECIES_DESC -> compareByDescending { it.scientificName }
                                SortOrder.CATEGORY_ASC -> compareBy { it.category.displayName }
                                SortOrder.CATEGORY_DESC -> compareByDescending { it.category.displayName }
                            }
                        )
                    )
                }
            }
            is SeedBankEvent.FilterByCategory -> {
                Log.d(TAG, "Filtrerar frön efter kategori: ${event.category}")
                _state.update { currentState ->
                    currentState.copy(
                        filteredSeeds = if (event.category == null) {
                            currentState.seeds
                        } else {
                            currentState.seeds.filter { it.category.displayName == event.category }
                        }
                    )
                }
            }
            is SeedBankEvent.DeleteSeed -> {
                Log.d(TAG, "Tar bort frö: ${event.seed.name}")
                viewModelScope.launch {
                    try {
                        repository.deleteSeed(event.seed)
                        _state.update { currentState ->
                            currentState.copy(
                                seeds = currentState.seeds.filter { it.id != event.seed.id },
                                filteredSeeds = currentState.filteredSeeds.filter { it.id != event.seed.id }
                            )
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "Fel vid borttagning av frö", e)
                        _state.update { it.copy(error = "Kunde inte ta bort frö: ${e.message}") }
                    }
                }
            }
            is SeedBankEvent.RefreshSeeds -> {
                Log.d(TAG, "Uppdaterar frölistan")
                loadSeeds()
            }
        }
    }

    private fun filterAndSortSeeds(seeds: List<Seed>): List<Seed> {
        var filteredSeeds = seeds

        // Filtrera efter sökfråga
        if (_state.value.searchQuery.isNotBlank()) {
            filteredSeeds = filteredSeeds.filter { seed ->
                seed.name.contains(_state.value.searchQuery, ignoreCase = true) ||
                (seed.species?.contains(_state.value.searchQuery, ignoreCase = true) ?: false) ||
                seed.category.displayName.contains(_state.value.searchQuery, ignoreCase = true)
            }
        }

        // Filtrera efter kategori
        if (_state.value.selectedCategory != null) {
            filteredSeeds = filteredSeeds.filter { seed ->
                seed.category.displayName == _state.value.selectedCategory
            }
        }

        // Sortera
        filteredSeeds = when (_state.value.sortOrder) {
            SortOrder.NAME_ASC -> filteredSeeds.sortedBy { it.name }
            SortOrder.NAME_DESC -> filteredSeeds.sortedByDescending { it.name }
            SortOrder.SPECIES_ASC -> filteredSeeds.sortedBy { it.species ?: "" }
            SortOrder.SPECIES_DESC -> filteredSeeds.sortedByDescending { it.species ?: "" }
            SortOrder.CATEGORY_ASC -> filteredSeeds.sortedBy { it.category.displayName }
            SortOrder.CATEGORY_DESC -> filteredSeeds.sortedByDescending { it.category.displayName }
        }

        return filteredSeeds
    }

    private fun updateInvalidCategories() {
        viewModelScope.launch {
            try {
                seedUseCases.updateInvalidCategories()
                loadSeeds()
            } catch (e: Exception) {
                _state.update { it.copy(
                    error = "Kunde inte uppdatera kategorierna: ${e.message}"
                )}
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
    data class SortSeeds(val order: SortOrder) : SeedBankEvent()
    data class FilterByCategory(val category: String?) : SeedBankEvent()
    object RefreshSeeds : SeedBankEvent()
} 