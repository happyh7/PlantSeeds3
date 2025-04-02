package com.bps.plantseeds3.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bps.plantseeds3.common.model.Resource
import com.bps.plantseeds3.domain.model.Seed
import com.bps.plantseeds3.domain.use_case.GetSeedsUseCase
import com.bps.plantseeds3.presentation.ui.state.SeedListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SeedListViewModel"

@HiltViewModel
class SeedListViewModel @Inject constructor(
    private val getSeedsUseCase: GetSeedsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SeedListUiState())
    val uiState: StateFlow<SeedListUiState> = _uiState.asStateFlow()

    init {
        Log.d(TAG, "init: Loading seeds")
        observeSeeds()
    }

    fun refreshSeeds() {
        Log.d(TAG, "refreshSeeds: Refreshing seeds")
        // Vi behöver inte göra något här eftersom Flow automatiskt uppdateras
    }

    fun onSearchQueryChange(query: String) {
        Log.d(TAG, "onSearchQueryChange: Query = $query")
        val currentSeeds = _uiState.value.seeds
        val filteredSeeds = if (query.isBlank()) {
            currentSeeds
        } else {
            currentSeeds.filter { seed ->
                seed.name.contains(query, ignoreCase = true) ||
                seed.description?.contains(query, ignoreCase = true) == true
            }
        }
        _uiState.value = _uiState.value.copy(
            searchQuery = query,
            filteredSeeds = filteredSeeds
        )
    }

    private fun observeSeeds() {
        viewModelScope.launch {
            try {
                getSeedsUseCase()
                    .onStart { 
                        Log.d(TAG, "observeSeeds: Starting to observe seeds")
                        _uiState.value = _uiState.value.copy(isLoading = true) 
                    }
                    .catch { e ->
                        Log.e(TAG, "observeSeeds: Exception occurred", e)
                        Log.e(TAG, "observeSeeds: Exception message: ${e.message}")
                        Log.e(TAG, "observeSeeds: Exception stack trace: ${e.stackTraceToString()}")
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = "Kunde inte ladda frön: ${e.message}"
                        )
                    }
                    .collect { resource ->
                        Log.d(TAG, "observeSeeds: Received resource: $resource")
                        handleSeedListResponse(resource)
                    }
            } catch (e: Exception) {
                Log.e(TAG, "observeSeeds: Exception occurred", e)
                Log.e(TAG, "observeSeeds: Exception message: ${e.message}")
                Log.e(TAG, "observeSeeds: Exception stack trace: ${e.stackTraceToString()}")
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Kunde inte ladda frön: ${e.message}"
                )
            }
        }
    }

    private fun handleSeedListResponse(response: Resource<List<Seed>>) {
        Log.d(TAG, "handleSeedListResponse: Handling response: $response")
        when (response) {
            is Resource.Success -> {
                Log.d(TAG, "handleSeedListResponse: Success, found ${response.data.size} seeds")
                val currentSeeds = _uiState.value.seeds
                val newSeeds = response.data
                
                // Uppdatera endast om det finns faktiska ändringar
                if (currentSeeds != newSeeds) {
                    _uiState.value = _uiState.value.copy(
                        seeds = newSeeds,
                        filteredSeeds = if (_uiState.value.searchQuery.isBlank()) newSeeds else _uiState.value.filteredSeeds,
                        isLoading = false,
                        error = null
                    )
                } else {
                    Log.d(TAG, "handleSeedListResponse: No changes detected, skipping update")
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
            }
            is Resource.Error -> {
                Log.e(TAG, "handleSeedListResponse: Error: ${response.message}")
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = response.message
                )
            }
            is Resource.Loading -> {
                Log.d(TAG, "handleSeedListResponse: Loading")
                _uiState.value = _uiState.value.copy(
                    isLoading = true,
                    error = null
                )
            }
        }
    }

    private fun handleSeedDeletionResponse(response: Resource<Unit>) {
        Log.d(TAG, "handleSeedDeletionResponse: Handling response: $response")
        when (response) {
            is Resource.Success -> {
                Log.d(TAG, "handleSeedDeletionResponse: Success")
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = null
                )
            }
            is Resource.Error -> {
                Log.e(TAG, "handleSeedDeletionResponse: Error: ${response.message}")
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = response.message
                )
            }
            is Resource.Loading -> {
                Log.d(TAG, "handleSeedDeletionResponse: Loading")
                _uiState.value = _uiState.value.copy(
                    isLoading = true,
                    error = null
                )
            }
        }
    }
} 