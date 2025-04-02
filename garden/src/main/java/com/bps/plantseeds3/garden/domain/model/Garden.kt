package com.bps.plantseeds3.garden.domain.model

import java.util.Date
import java.util.UUID

data class Garden(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val location: String,
    val description: String? = null,
    val size: String? = null,
    val soilType: String? = null,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
    val plants: List<String> = emptyList() // Lista av plant IDs
) {
    companion object {
        fun createEmpty() = Garden(
            name = "",
            location = "",
            description = null,
            size = null,
            soilType = null
        )
    }
} 