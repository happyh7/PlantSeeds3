package com.bps.plantseeds3.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName

data class User(
    @DocumentId
    val id: String = "",
    
    val email: String = "",
    val displayName: String = "",
    val photoUrl: String? = null,
    
    @PropertyName("created_at")
    val createdAt: Timestamp = Timestamp.now(),
    
    @PropertyName("updated_at")
    val updatedAt: Timestamp = Timestamp.now()
)

data class GardenAccess(
    @DocumentId
    val id: String = "",
    
    @PropertyName("garden_id")
    val gardenId: String = "",
    
    @PropertyName("user_id")
    val userId: String = "",
    
    @PropertyName("access_level")
    val accessLevel: AccessLevel = AccessLevel.READ,
    
    @PropertyName("created_at")
    val createdAt: Timestamp = Timestamp.now(),
    
    @PropertyName("updated_at")
    val updatedAt: Timestamp = Timestamp.now()
)

enum class AccessLevel {
    READ,
    WRITE,
    ADMIN
} 