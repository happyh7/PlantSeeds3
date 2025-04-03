package com.bps.plantseeds3.garden.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bps.plantseeds3.domain.model.Plant
import java.time.Instant

@Composable
fun EditPlantDialog(
    plant: Plant,
    onDismiss: () -> Unit,
    onConfirm: (Plant) -> Unit
) {
    var name by remember { mutableStateOf(plant.name) }
    var species by remember { mutableStateOf(plant.species) }
    var description by remember { mutableStateOf(plant.description ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Redigera växt") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Namn") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = species,
                    onValueChange = { species = it },
                    label = { Text("Art") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Beskrivning") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (name.isNotBlank() && species.isNotBlank()) {
                        onConfirm(
                            plant.copy(
                                name = name,
                                species = species,
                                description = description.ifBlank { null },
                                updatedAt = Instant.now()
                            )
                        )
                    }
                },
                enabled = name.isNotBlank() && species.isNotBlank()
            ) {
                Text("Spara")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Avbryt")
            }
        }
    )
} 