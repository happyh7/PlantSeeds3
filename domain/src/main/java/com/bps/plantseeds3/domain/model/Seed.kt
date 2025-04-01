package com.bps.plantseeds3.domain.model

import java.time.Instant

data class Seed(
    val id: String,
    val plantId: String,
    val name: String,
    val species: String?,
    val description: String?,
    val plantingInstructions: String?,
    val daysToGermination: Int?,
    val daysToHarvest: Int?,
    val lightNeeds: String?,
    val waterNeeds: String?,
    val soilType: String?,
    val temperature: String?,
    val spacing: String?,
    val companionPlants: List<String>?,
    val avoidPlants: List<String>?,
    val imageUrl: String?,
    val createdAt: Instant,
    val updatedAt: Instant,
    val isSynced: Boolean = false
) 