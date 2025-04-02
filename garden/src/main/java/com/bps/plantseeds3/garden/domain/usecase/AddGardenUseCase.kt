package com.bps.plantseeds3.garden.domain.usecase

import com.bps.plantseeds3.garden.domain.model.Garden
import com.bps.plantseeds3.garden.domain.repository.GardenRepository
import javax.inject.Inject

class AddGardenUseCase @Inject constructor(
    private val repository: GardenRepository
) {
    suspend operator fun invoke(garden: Garden) {
        require(garden.name.isNotBlank()) { "Trädgårdens namn får inte vara tomt" }
        require(garden.location.isNotBlank()) { "Plats måste anges" }
        
        repository.insertGarden(garden)
    }
} 