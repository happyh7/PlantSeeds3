package com.bps.plantseeds3.domain.use_case.seed

import com.bps.plantseeds3.data.local.entity.Seed
import com.bps.plantseeds3.domain.repository.SeedRepository
import javax.inject.Inject

class DeleteSeedUseCase @Inject constructor(
    private val repository: SeedRepository
) {
    suspend operator fun invoke(seed: Seed) {
        repository.deleteSeed(seed)
    }
} 