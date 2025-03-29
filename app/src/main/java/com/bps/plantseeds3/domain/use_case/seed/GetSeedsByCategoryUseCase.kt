package com.bps.plantseeds3.domain.use_case.seed

import com.bps.plantseeds3.domain.model.Seed
import com.bps.plantseeds3.domain.repository.SeedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSeedsByCategoryUseCase @Inject constructor(
    private val repository: SeedRepository
) {
    operator fun invoke(category: String): Flow<List<Seed>> {
        return repository.getSeedsByCategory(category)
    }
} 