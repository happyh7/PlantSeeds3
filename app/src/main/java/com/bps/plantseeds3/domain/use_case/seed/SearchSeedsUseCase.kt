package com.bps.plantseeds3.domain.use_case.seed

import com.bps.plantseeds3.data.local.entity.Seed
import com.bps.plantseeds3.domain.repository.SeedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchSeedsUseCase @Inject constructor(
    private val repository: SeedRepository
) {
    operator fun invoke(query: String): Flow<List<Seed>> {
        return repository.searchSeeds(query)
    }
} 