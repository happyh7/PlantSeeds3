package com.bps.plantseeds3.domain.model

import java.time.LocalDate

data class Seed(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val plantingDate: LocalDate? = null,
    val germinationDate: LocalDate? = null,
    val harvestDate: LocalDate? = null,
    val notes: String = "",
    val imageUrl: String? = null,
    val createdAt: LocalDate = LocalDate.now(),
    val updatedAt: LocalDate = LocalDate.now()
) 