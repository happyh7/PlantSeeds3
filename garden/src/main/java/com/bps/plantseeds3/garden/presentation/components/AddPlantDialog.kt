package com.bps.plantseeds3.garden.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bps.plantseeds3.domain.model.Plant
import java.time.Instant
import java.util.*

@Composable
fun AddPlantDialog(
    gardenId: String,
    onDismiss: () -> Unit,
    onConfirm: (Plant) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var species by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Lägg till växt") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Namn") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = species,
                    onValueChange = { species = it },
                    label = { Text("Art") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Beskrivning") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val plant = Plant(
                        id = UUID.randomUUID().toString(),
                        name = name,
                        species = species,
                        description = description.takeIf { it.isNotBlank() },
                        gardenId = gardenId,
                        lastWatered = null,
                        nextWatering = null,
                        createdAt = Instant.now(),
                        updatedAt = Instant.now()
                    )
                    onConfirm(plant)
                    onDismiss()
                },
                enabled = name.isNotBlank() && species.isNotBlank()
            ) {
                Text("Lägg till")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Avbryt")
            }
        }
    )
} 