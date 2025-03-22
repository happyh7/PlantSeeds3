package com.bps.plantseeds3.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName

data class Plant(
    @DocumentId
    val id: String = "",
    
    @PropertyName("seed_id")
    val seedId: String = "",
    
    @PropertyName("garden_id")
    val gardenId: String = "",
    
    @PropertyName("name")
    val name: String = "",
    
    @PropertyName("species")
    val species: String = "",
    
    @PropertyName("planted_at")
    val plantedAt: Timestamp = Timestamp.now(),
    
    @PropertyName("expected_harvest_date")
    val expectedHarvestDate: Timestamp? = null,
    
    @PropertyName("status")
    val status: PlantStatus = PlantStatus.PLANTED,
    
    @PropertyName("notes")
    val notes: String = "",
    
    @PropertyName("location")
    val location: String = "",
    
    @PropertyName("last_watered")
    val lastWatered: Timestamp? = null,
    
    @PropertyName("last_fertilized")
    val lastFertilized: Timestamp? = null,
    
    @PropertyName("created_at")
    val createdAt: Timestamp = Timestamp.now(),
    
    @PropertyName("updated_at")
    val updatedAt: Timestamp = Timestamp.now(),
    
    @PropertyName("created_by")
    val userId: String = ""
) 