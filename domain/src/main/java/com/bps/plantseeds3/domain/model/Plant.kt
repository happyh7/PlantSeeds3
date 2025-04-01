package com.bps.plantseeds3.domain.model

import java.time.Instant

data class Plant(
    val id: String,
    val name: String,
    val species: String,
    val description: String?,
    val gardenId: String?,
    val lastWatered: Instant?,
    val nextWatering: Instant?,
    val createdAt: Instant,
    val updatedAt: Instant
) 