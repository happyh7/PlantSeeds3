package com.bps.plantseeds3.garden.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bps.plantseeds3.domain.model.Plant

@Composable
fun PlantList(
    plants: List<Plant>,
    onPlantClick: (String) -> Unit,
    onDeletePlant: (Plant) -> Unit,
    onEditPlant: (Plant) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        plants.forEach { plant ->
            var showDeleteDialog by remember { mutableStateOf(false) }
            
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { onPlantClick(plant.id) },
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = plant.name,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = plant.species,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                        plant.description?.let { description ->
                            Text(
                                text = description,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                    
                    Row {
                        IconButton(onClick = { onEditPlant(plant) }) {
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = "Redigera växt",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        IconButton(onClick = { showDeleteDialog = true }) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Ta bort växt",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
            
            if (showDeleteDialog) {
                AlertDialog(
                    onDismissRequest = { showDeleteDialog = false },
                    title = { Text("Ta bort växt") },
                    text = { Text("Är du säker på att du vill ta bort ${plant.name}?") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                onDeletePlant(plant)
                                showDeleteDialog = false
                            }
                        ) {
                            Text("Ta bort")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDeleteDialog = false }) {
                            Text("Avbryt")
                        }
                    }
                )
            }
        }
    }
} 