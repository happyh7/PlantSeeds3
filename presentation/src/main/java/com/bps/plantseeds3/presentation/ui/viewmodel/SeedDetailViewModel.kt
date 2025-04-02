package com.bps.plantseeds3.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bps.plantseeds3.common.model.Resource
import com.bps.plantseeds3.domain.model.Seed
import com.bps.plantseeds3.domain.use_case.GetSeedUseCase
import com.bps.plantseeds3.presentation.ui.state.SeedDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SeedDetailViewModel"

@HiltViewModel
class SeedDetailViewModel @Inject constructor(
    private val getSeedUseCase: GetSeedUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(SeedDetailUiState())
    val uiState: StateFlow<SeedDetailUiState> = _uiState.asStateFlow()

    private var seedId: String? = null

    init {
        savedStateHandle.get<String>("seedId")?.let { id ->
            seedId = id
            Log.d(TAG, "init: Loading seed with ID: $id")
            loadSeed(id)
        }
    }

    fun refreshSeed() {
        seedId?.let { id ->
            Log.d(TAG, "refreshSeed: Reloading seed with ID: $id")
            loadSeed(id)
        }
    }

    private fun loadSeed(seedId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                Log.d(TAG, "loadSeed: Attempting to load seed with ID: $seedId")
                when (val result = getSeedUseCase(seedId)) {
                    is Resource.Success -> {
                        val seed = result.data
                        Log.d(TAG, "loadSeed: Successfully loaded seed: $seed")
                        _uiState.value = _uiState.value.copy(
                            seed = seed,
                            isLoading = false,
                            error = null
                        )
                    }
                    is Resource.Error -> {
                        Log.e(TAG, "loadSeed: Error loading seed: ${result.message}")
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = result.message ?: "Ett fel uppstod"
                        )
                    }
                    else -> {
                        Log.e(TAG, "loadSeed: Unknown result type")
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = "Okänt fel"
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "loadSeed: Exception while loading seed", e)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Kunde inte ladda frö: ${e.message}"
                )
            }
        }
    }

    fun onDeleteSeed(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.value.seed?.id?.let { seedId ->
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                try {
                    when (val result = getSeedUseCase(seedId)) {
                        is Resource.Success -> {
                            onSuccess()
                        }
                        is Resource.Error -> {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                error = result.message ?: "Ett fel uppstod"
                            )
                        }
                        else -> {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                error = "Okänt fel"
                            )
                        }
                    }
                } catch (e: Exception) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Kunde inte ta bort frö: ${e.message}"
                    )
                }
            }
        }
    }
} 