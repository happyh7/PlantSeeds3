package com.bps.plantseeds3.domain.use_case

import com.bps.plantseeds3.common.model.Resource
import com.bps.plantseeds3.domain.model.Plant
import com.bps.plantseeds3.domain.repository.PlantRepository
import javax.inject.Inject

class AddPlantUseCase @Inject constructor(
    private val repository: PlantRepository
) {
    suspend operator fun invoke(plant: Plant): Resource<Unit> {
        return repository.insertPlant(plant)
    }
} 