package com.bps.plantseeds3.domain.use_case.seed

import com.bps.plantseeds3.domain.repository.SeedRepository
import javax.inject.Inject

class UpdateInvalidCategoriesUseCase @Inject constructor(
    private val repository: SeedRepository
) {
    suspend operator fun invoke() {
        repository.updateInvalidCategories()
    }
} 