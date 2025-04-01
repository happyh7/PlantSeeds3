package com.bps.plantseeds3.presentation.screens.plants

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantsScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Växter") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO: Implementera lägg till växt */ }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Lägg till växt")
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
            items(listOf("Monstera", "Fikus", "Strelitzia")) { plantName ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { /* TODO: Implementera växtdetaljer */ }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = plantName,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Visa detaljer",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
} 