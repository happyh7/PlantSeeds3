package com.bps.plantseeds3.presentation.ui.state

import com.bps.plantseeds3.domain.model.Seed

data class SeedListUiState(
    val seeds: List<Seed> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val filteredSeeds: List<Seed> = emptyList()
) 