package com.bps.plantseeds3.presentation.seeds

import com.bps.plantseeds3.domain.model.Seed

sealed class SeedEvent {
    data class SearchSeeds(val query: String) : SeedEvent()
    data class DeleteSeed(val seed: Seed) : SeedEvent()
    data class UpdateSeed(val seed: Seed) : SeedEvent()
} 