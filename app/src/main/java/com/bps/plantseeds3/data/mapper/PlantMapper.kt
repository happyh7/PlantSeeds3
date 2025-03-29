package com.bps.plantseeds3.data.mapper

import android.util.Log
import com.bps.plantseeds3.data.local.entity.Plant as PlantEntity
import com.bps.plantseeds3.data.local.entity.PlantStatus as PlantStatusEntity
import com.bps.plantseeds3.domain.model.Plant as PlantDomain
import com.bps.plantseeds3.domain.model.PlantCategory
import com.bps.plantseeds3.domain.model.PlantStatus as PlantStatusDomain
import java.time.LocalDate
import javax.inject.Inject

class PlantMapper @Inject constructor() : BaseMapper<PlantEntity, PlantDomain>() {
    override val TAG = "PlantMapper"

    override fun PlantEntity.convertToDomain(): PlantDomain {
        return PlantDomain(
            id = id,
            name = name,
            scientificName = scientificName,
            species = species,
            variety = variety,
            description = description,
            category = safeConvertEnum(category, PlantCategory::fromName, PlantCategory.OTHER),
            status = safeConvertEnum(status.name, PlantStatusDomain::valueOf, PlantStatusDomain.SEED),
            plantingDate = plantingDate.toString(),
            harvestDate = harvestDate?.toString(),
            sunRequirement = sunRequirement,
            waterRequirement = waterRequirement,
            soilRequirement = soilRequirement,
            notes = notes,
            gardenId = gardenId
        )
    }

    override fun PlantDomain.convertToEntity(): PlantEntity {
        return PlantEntity(
            id = id,
            name = name,
            scientificName = scientificName ?: "",
            species = species ?: "",
            variety = variety ?: "",
            description = description ?: "",
            category = category?.name ?: PlantCategory.OTHER.name,
            gardenId = gardenId,
            status = safeConvertEnum(status?.name, PlantStatusEntity::valueOf, PlantStatusEntity.SEED),
            plantingDate = LocalDate.now(),
            harvestDate = null,
            sowingDepth = null,
            spacing = null,
            daysToGermination = null,
            daysToMaturity = null,
            sunRequirement = sunRequirement,
            waterRequirement = waterRequirement,
            soilRequirement = soilRequirement,
            soilPh = null,
            hardiness = null,
            sowingInstructions = null,
            growingInstructions = null,
            harvestInstructions = null,
            storageInstructions = null,
            companionPlants = null,
            avoidPlants = null,
            height = null,
            spread = null,
            yield = null,
            culinaryUses = null,
            medicinalUses = null,
            tags = null,
            notes = notes,
            createdAt = LocalDate.now(),
            updatedAt = LocalDate.now()
        )
    }
} 