package com.bps.plantseeds3.presentation.ui.state

import com.bps.plantseeds3.domain.model.Seed

data class SeedDetailUiState(
    val seed: Seed? = null,
    val isLoading: Boolean = false,
    val error: String? = null
) 