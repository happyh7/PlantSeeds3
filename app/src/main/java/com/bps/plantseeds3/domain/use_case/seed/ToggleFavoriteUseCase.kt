package com.bps.plantseeds3.domain.use_case.seed

import com.bps.plantseeds3.domain.model.Seed
import com.bps.plantseeds3.domain.repository.SeedRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: SeedRepository
) {
    suspend operator fun invoke(seed: Seed) {
        repository.updateSeed(seed.copy(isFavorite = !seed.isFavorite))
    }
} 