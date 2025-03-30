package com.bps.plantseeds3.domain.use_case.plant

import com.bps.plantseeds3.domain.model.Plant
import com.bps.plantseeds3.domain.repository.PlantRepository
import javax.inject.Inject

class GetPlantUseCase @Inject constructor(
    private val repository: PlantRepository
) {
    suspend operator fun invoke(id: Long): Plant? {
        return repository.getPlantById(id)?.first()
    }
} 