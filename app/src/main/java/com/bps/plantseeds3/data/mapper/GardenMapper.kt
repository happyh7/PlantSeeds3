package com.bps.plantseeds3.data.mapper

import android.util.Log
import com.bps.plantseeds3.data.local.entity.Garden as GardenEntity
import com.bps.plantseeds3.domain.model.Garden as GardenDomain
import javax.inject.Inject

class GardenMapper @Inject constructor() : BaseMapper<GardenEntity, GardenDomain>() {
    override val TAG = "GardenMapper"

    override fun GardenEntity.convertToDomain(): GardenDomain {
        return GardenDomain(
            id = id,
            name = name,
            location = location,
            description = description,
            size = size,
            width = width,
            length = length,
            elevation = elevation,
            slope = slope,
            soilType = soilType,
            sunExposure = sunExposure,
            irrigation = irrigation,
            fence = fence,
            notes = notes
        )
    }

    override fun GardenDomain.convertToEntity(): GardenEntity {
        return GardenEntity(
            id = id,
            name = name,
            location = location,
            description = description,
            size = size,
            width = width,
            length = length,
            elevation = elevation,
            slope = slope,
            soilType = soilType,
            sunExposure = sunExposure,
            irrigation = irrigation,
            fence = fence,
            notes = notes
        )
    }
} 