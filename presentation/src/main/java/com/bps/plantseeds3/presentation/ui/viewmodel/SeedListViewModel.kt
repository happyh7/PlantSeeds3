package com.bps.plantseeds3.presentation.ui.viewmodel

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

@HiltViewModel
class SeedListViewModel @Inject constructor(
    private val getSeedsUseCase: GetSeedsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SeedListUiState())
    val uiState: StateFlow<SeedListUiState> = _uiState.asStateFlow()

    init {
        loadSeeds()
    }

    fun onSearchQueryChange(query: String) {
        val currentSeeds = _uiState.value.seeds
        val filteredSeeds = if (query.isBlank()) {
            currentSeeds
        } else {
            currentSeeds.filter { seed ->
                seed.name?.contains(query, ignoreCase = true) == true ||
                seed.description?.contains(query, ignoreCase = true) == true
            }
        }
        _uiState.value = _uiState.value.copy(
            searchQuery = query,
            filteredSeeds = filteredSeeds
        )
    }

    private fun loadSeeds() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            getSeedsUseCase().collect { resource ->
                handleSeedListResponse(resource)
            }
        }
    }

    private fun handleSeedListResponse(response: Resource<List<Seed>>) {
        when (response) {
            is Resource.Success -> {
                _uiState.value = _uiState.value.copy(
                    seeds = response.data,
                    isLoading = false,
                    error = null
                )
            }
            is Resource.Error -> {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = response.message
                )
            }
            is Resource.Loading -> {
                _uiState.value = _uiState.value.copy(
                    isLoading = true,
                    error = null
                )
            }
        }
    }

    private fun handleSeedDeletionResponse(response: Resource<Unit>) {
        when (response) {
            is Resource.Success -> {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = null
                )
            }
            is Resource.Error -> {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = response.message
                )
            }
            is Resource.Loading -> {
                _uiState.value = _uiState.value.copy(
                    isLoading = true,
                    error = null
                )
            }
        }
    }
} 