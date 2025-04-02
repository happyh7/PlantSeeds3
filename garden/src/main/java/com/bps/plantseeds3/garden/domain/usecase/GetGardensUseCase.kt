package com.bps.plantseeds3.garden.domain.usecase

import com.bps.plantseeds3.garden.domain.model.Garden
import com.bps.plantseeds3.garden.domain.repository.GardenRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGardensUseCase @Inject constructor(
    private val repository: GardenRepository
) {
    operator fun invoke(): Flow<List<Garden>> {
        return repository.getGardens()
    }
} 