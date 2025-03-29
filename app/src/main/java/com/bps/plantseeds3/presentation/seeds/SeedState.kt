package com.bps.plantseeds3.presentation.seeds

import com.bps.plantseeds3.domain.model.Seed

data class SeedState(
    val seeds: List<Seed> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
) 