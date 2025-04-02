package com.bps.plantseeds3.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bps.plantseeds3.common.model.Resource
import com.bps.plantseeds3.domain.model.Seed
import com.bps.plantseeds3.domain.use_case.GetSeedsUseCase
import com.bps.plantseeds3.presentation.ui.state.SeedListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
        loadSeeds()
    }

    fun refreshSeeds() {
        Log.d(TAG, "refreshSeeds: Refreshing seeds")
        loadSeeds()
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

    fun loadSeeds() {
        Log.d(TAG, "loadSeeds: Starting to load seeds")
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                getSeedsUseCase().collect { resource ->
                    Log.d(TAG, "loadSeeds: Received resource: $resource")
                    handleSeedListResponse(resource)
                }
            } catch (e: Exception) {
                Log.e(TAG, "loadSeeds: Exception occurred", e)
                Log.e(TAG, "loadSeeds: Exception message: ${e.message}")
                Log.e(TAG, "loadSeeds: Exception stack trace: ${e.stackTraceToString()}")
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
                        isLoading = false,
                        error = null
                    )
                } else {
                    Log.d(TAG, "handleSeedListResponse: No changes detected, skipping update")
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