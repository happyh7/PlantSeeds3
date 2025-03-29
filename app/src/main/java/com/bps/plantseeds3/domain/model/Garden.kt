package com.bps.plantseeds3.domain.model

import java.time.LocalDate

data class Garden(
    val id: String,
    val name: String,
    val location: String? = null,
    val description: String? = null,
    val size: Float? = null,
    val width: Float? = null,
    val length: Float? = null,
    val elevation: Float? = null,
    val slope: Float? = null,
    val soilType: String? = null,
    val sunExposure: String? = null,
    val irrigation: String? = null,
    val fence: String? = null,
    val notes: String? = null,
    val createdAt: LocalDate = LocalDate.now(),
    val updatedAt: LocalDate = LocalDate.now()
) 