package com.bps.plantseeds3.seeds.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bps.plantseeds3.common.util.Result
import com.bps.plantseeds3.seeds.data.SeedRepository
import com.bps.plantseeds3.seeds.model.Seed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeedsViewModel @Inject constructor(
    private val seedRepository: SeedRepository
) : ViewModel() {

    private val _seeds = MutableStateFlow<List<Seed>>(emptyList())
    val seeds: StateFlow<List<Seed>> = _seeds.asStateFlow()

    private val _favoriteSeeds = MutableStateFlow<List<Seed>>(emptyList())
    val favoriteSeeds: StateFlow<List<Seed>> = _favoriteSeeds.asStateFlow()

    private val _selectedSeed = MutableStateFlow<Seed?>(null)
    val selectedSeed: StateFlow<Seed?> = _selectedSeed.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        loadSeeds()
        loadFavoriteSeeds()
    }

    private fun loadSeeds() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                seedRepository.getAllSeeds()
                    .collect { seeds ->
                        _seeds.value = seeds
                        _isLoading.value = false
                    }
            } catch (e: Exception) {
                _error.value = e.message
                _isLoading.value = false
            }
        }
    }

    private fun loadFavoriteSeeds() {
        viewModelScope.launch {
            try {
                seedRepository.getFavoriteSeeds()
                    .collect { seeds ->
                        _favoriteSeeds.value = seeds
                    }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun selectSeed(seedId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                seedRepository.getSeedById(seedId)
                    .collect { seed ->
                        _selectedSeed.value = seed
                        _isLoading.value = false
                    }
            } catch (e: Exception) {
                _error.value = e.message
                _isLoading.value = false
            }
        }
    }

    fun addSeed(seed: Seed) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                when (val result = seedRepository.insertSeed(seed)) {
                    is Result.Success -> {
                        loadSeeds()
                        _isLoading.value = false
                    }
                    is Result.Error -> {
                        _error.value = result.exception.message
                        _isLoading.value = false
                    }
                    is Result.Loading -> {
                        _isLoading.value = true
                    }
                }
            } catch (e: Exception) {
                _error.value = e.message
                _isLoading.value = false
            }
        }
    }

    fun updateSeed(seed: Seed) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                when (val result = seedRepository.updateSeed(seed)) {
                    is Result.Success -> {
                        loadSeeds()
                        _isLoading.value = false
                    }
                    is Result.Error -> {
                        _error.value = result.exception.message
                        _isLoading.value = false
                    }
                    is Result.Loading -> {
                        _isLoading.value = true
                    }
                }
            } catch (e: Exception) {
                _error.value = e.message
                _isLoading.value = false
            }
        }
    }

    fun deleteSeed(seed: Seed) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                when (val result = seedRepository.deleteSeed(seed)) {
                    is Result.Success -> {
                        loadSeeds()
                        _isLoading.value = false
                    }
                    is Result.Error -> {
                        _error.value = result.exception.message
                        _isLoading.value = false
                    }
                    is Result.Loading -> {
                        _isLoading.value = true
                    }
                }
            } catch (e: Exception) {
                _error.value = e.message
                _isLoading.value = false
            }
        }
    }

    fun toggleFavorite(seedId: Long) {
        viewModelScope.launch {
            try {
                when (val result = seedRepository.toggleFavorite(seedId)) {
                    is Result.Success -> {
                        loadSeeds()
                        loadFavoriteSeeds()
                    }
                    is Result.Error -> {
                        _error.value = result.exception.message
                    }
                    is Result.Loading -> {
                        _isLoading.value = true
                    }
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun clearError() {
        _error.value = null
    }
} 