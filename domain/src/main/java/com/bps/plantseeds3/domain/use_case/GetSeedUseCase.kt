package com.bps.plantseeds3.domain.use_case

import com.bps.plantseeds3.common.model.Resource
import com.bps.plantseeds3.domain.model.Seed
import com.bps.plantseeds3.domain.repository.SeedRepository
import javax.inject.Inject

class GetSeedUseCase @Inject constructor(
    private val repository: SeedRepository
) {
    suspend operator fun invoke(id: String): Resource<Seed> {
        return repository.getSeedById(id)
    }
} 