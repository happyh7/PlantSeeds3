package com.bps.plantseeds3.domain.model

import java.util.UUID

data class Plant(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val scientificName: String? = null,
    val species: String? = null,
    val variety: String? = null,
    val description: String? = null,
    val category: PlantCategory? = null,
    val status: PlantStatus? = null,
    val plantingDate: String? = null,
    val harvestDate: String? = null,
    val sunRequirement: String? = null,
    val waterRequirement: String? = null,
    val soilRequirement: String? = null,
    val notes: String? = null,
    val gardenId: String
) 