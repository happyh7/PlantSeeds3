package com.bps.plantseeds3.presentation.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bps.plantseeds3.domain.model.Resource
import com.bps.plantseeds3.domain.model.Seed
import com.bps.plantseeds3.domain.repository.SeedRepository
import com.bps.plantseeds3.presentation.ui.state.SeedDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeedDetailViewModel @Inject constructor(
    private val seedRepository: SeedRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(SeedDetailUiState())
    val uiState: StateFlow<SeedDetailUiState> = _uiState.asStateFlow()

    init {
        savedStateHandle.get<String>("seedId")?.let { seedId ->
            loadSeed(seedId)
        }
    }

    private fun loadSeed(seedId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                when (val result = seedRepository.getSeedById(seedId)) {
                    is Resource.Success<*> -> {
                        val seed = result.data
                        if (seed is Seed) {
                            _uiState.value = _uiState.value.copy(
                                seed = seed,
                                isLoading = false,
                                error = null
                            )
                        } else {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                error = "Ogiltig datatyp returnerad från servern"
                            )
                        }
                    }
                    is Resource.Error<*> -> {
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
                    when (val result = seedRepository.deleteSeed(seedId)) {
                        is Resource.Success<*> -> {
                            onSuccess()
                        }
                        is Resource.Error<*> -> {
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