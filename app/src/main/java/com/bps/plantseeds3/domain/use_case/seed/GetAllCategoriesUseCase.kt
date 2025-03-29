package com.bps.plantseeds3.domain.use_case.seed

import com.bps.plantseeds3.domain.repository.SeedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCategoriesUseCase @Inject constructor(
    private val repository: SeedRepository
) {
    operator fun invoke(): Flow<List<String>> {
        return repository.getDistinctCategories()
    }
} 