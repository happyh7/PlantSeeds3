package com.bps.plantseeds3.domain.use_case.seed

import com.bps.plantseeds3.domain.model.Seed
import com.bps.plantseeds3.domain.repository.SeedRepository
import javax.inject.Inject

class GetSeedUseCase @Inject constructor(
    private val repository: SeedRepository
) {
    suspend operator fun invoke(id: String): Seed? {
        return repository.getSeedById(id)
    }
} 