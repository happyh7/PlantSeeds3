package com.bps.plantseeds3.domain.model

import java.time.Instant

data class Garden(
    val id: String,
    val name: String,
    val description: String?,
    val location: String?,
    val createdAt: Instant,
    val updatedAt: Instant
) 