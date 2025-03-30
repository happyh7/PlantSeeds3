package com.bps.plantseeds3.seeds.model

import androidx.room.Entity
import androidx.room.Index
import com.bps.plantseeds3.common.model.BaseEntity
import java.time.LocalDate

@Entity(
    tableName = "seeds",
    indices = [
        Index(value = ["name"]),
        Index(value = ["species"]),
        Index(value = ["variety"]),
        Index(value = ["isFavorite"])
    ]
)
class Seed : BaseEntity() {
    var name: String = ""
    var species: String = ""
    var variety: String = ""
    var description: String = ""
    var sowingInstructions: String = ""
    var germinationTime: Int = 0
    var harvestTime: Int = 0
    var plantingDepth: Float = 0f
    var spacing: Float = 0f
    var lightNeeds: String = ""
    var waterNeeds: String = ""
    var temperatureNeeds: String = ""
    var quantity: Int = 0
    var unit: String = ""
    var isFavorite: Boolean = false
    var imageUrl: String? = null
    var purchaseDate: LocalDate? = null
    var expiryDate: LocalDate? = null
} 