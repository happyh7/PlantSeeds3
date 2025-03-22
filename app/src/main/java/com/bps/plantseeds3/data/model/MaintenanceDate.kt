package com.bps.plantseeds3.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName

data class MaintenanceDate(
    @PropertyName("date")
    val date: Timestamp = Timestamp.now(),
    
    @PropertyName("type")
    val type: String = "",
    
    @PropertyName("description")
    val description: String = "",
    
    @PropertyName("notes")
    val notes: String = ""
) 