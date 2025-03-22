package com.bps.plantseeds3.data.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName
import java.util.Date

data class Garden(
    @DocumentId
    override val id: String = "",
    @PropertyName("name")
    val name: String = "",
    @PropertyName("description")
    val description: String? = null,
    @PropertyName("location")
    val location: String? = null,
    @PropertyName("size")
    val size: Double? = null,
    @PropertyName("unit")
    val unit: String? = "m²",
    @PropertyName("seedIds")
    val seedIds: List<String> = emptyList(),
    @PropertyName("notes")
    val notes: String? = null,
    @PropertyName("tags")
    val tags: List<String> = emptyList(),
    @PropertyName("userId")
    val userId: String? = null,
    @PropertyName("createdAt")
    val createdAt: Date? = null,
    @PropertyName("updatedAt")
    val updatedAt: Date? = null
) : HasId

data class GardenGuide(
    val name: String = "",
    val overview: String = "",
    val instructions: String = ""
) 