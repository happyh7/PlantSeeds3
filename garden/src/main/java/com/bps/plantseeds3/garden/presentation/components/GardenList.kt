package com.bps.plantseeds3.garden.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bps.plantseeds3.garden.domain.model.Garden

@Composable
fun GardenList(
    gardens: List<Garden>,
    onGardenClick: (String) -> Unit,
    onDeleteGarden: (Garden) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        gardens.forEach { garden ->
            var showDeleteDialog by remember { mutableStateOf(false) }
            
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { onGardenClick(garden.id) },
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
                            text = garden.name,
                            style = MaterialTheme.typography.titleLarge
                        )
                        garden.description?.let { description ->
                            Text(
                                text = description,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                    
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Ta bort trädgård",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
            
            if (showDeleteDialog) {
                AlertDialog(
                    onDismissRequest = { showDeleteDialog = false },
                    title = { Text("Ta bort trädgård") },
                    text = { Text("Är du säker på att du vill ta bort ${garden.name}?") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                onDeleteGarden(garden)
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