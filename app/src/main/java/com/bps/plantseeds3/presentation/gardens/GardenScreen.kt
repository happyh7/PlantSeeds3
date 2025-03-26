package com.bps.plantseeds3.presentation.gardens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bps.plantseeds3.R
import com.bps.plantseeds3.data.local.entity.Garden

@Composable
fun GardenItem(
    garden: Garden,
    onGardenClick: (Garden) -> Unit,
    onDeleteGarden: (Garden) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onGardenClick(garden) },
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
                    style = MaterialTheme.typography.titleMedium
                )
                garden.location?.let { location ->
                    Text(
                        text = location,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            IconButton(
                onClick = { onDeleteGarden(garden) }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Ta bort trädgård",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun GardenScreen(
    onNavigateToAddEditGarden: (String) -> Unit,
    viewModel: GardenViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()

    if (state.showDeleteWarning) {
        AlertDialog(
            onDismissRequest = { viewModel.cancelDeleteGarden() },
            title = { Text("Ta bort trädgård") },
            text = {
                Text(
                    "Denna trädgård innehåller ${state.plantCountToDelete} växter som kommer att tas bort. " +
                    "Är du säker på att du vill fortsätta?"
                )
            },
            confirmButton = {
                TextButton(
                    onClick = { viewModel.confirmDeleteGarden() }
                ) {
                    Text("Ta bort", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { viewModel.cancelDeleteGarden() }
                ) {
                    Text("Avbryt")
                }
            }
        )
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateToAddEditGarden("-1") }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_garden)
                )
            }
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.gardens),
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                if (state.gardens.isEmpty()) {
                    Text(
                        text = stringResource(R.string.no_gardens),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.gardens) { garden ->
                            GardenItem(
                                garden = garden,
                                onGardenClick = { onNavigateToAddEditGarden(it.id) },
                                onDeleteGarden = { viewModel.deleteGarden(it) }
                            )
                        }
                    }
                }
            }
        }
    }
} 