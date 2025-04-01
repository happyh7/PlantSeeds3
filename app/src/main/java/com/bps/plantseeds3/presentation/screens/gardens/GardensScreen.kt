package com.bps.plantseeds3.presentation.screens.gardens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

private data class Garden(
    val id: String,
    val name: String
)

private val temporaryGardens = listOf(
    Garden(id = "garden_1", name = "Balkongen"),
    Garden(id = "garden_2", name = "Köksträdgården"),
    Garden(id = "garden_3", name = "Växthuset")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GardensScreen(
    onAddClick: () -> Unit,
    onGardenClick: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Trädgårdar") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick
            ) {
                Icon(Icons.Default.Add, contentDescription = "Lägg till trädgård")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(temporaryGardens) { garden ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onGardenClick(garden.id) }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = garden.name,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Visa detaljer",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
} 