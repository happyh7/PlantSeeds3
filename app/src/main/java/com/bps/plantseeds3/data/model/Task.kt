package com.bps.plantseeds3.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName

data class Task(
    @DocumentId
    val id: String = "",
    
    @PropertyName("plant_id")
    val plantId: String = "",
    
    val title: String = "",
    val description: String = "",
    
    @PropertyName("due_date")
    val dueDate: Timestamp? = null,
    
    @PropertyName("is_completed")
    val isCompleted: Boolean = false,
    
    @PropertyName("completed_at")
    val completedAt: Timestamp? = null,
    
    @PropertyName("completed_by")
    val completedBy: String? = null,
    
    @PropertyName("created_at")
    val createdAt: Timestamp = Timestamp.now(),
    
    @PropertyName("updated_at")
    val updatedAt: Timestamp = Timestamp.now()
)

enum class TaskType {
    WATERING,
    FERTILIZING,
    PRUNING,
    HARVESTING,
    PLANTING,
    PREPARATION,
    OTHER
} 