package com.bps.plantseeds3.presentation.gardens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bps.plantseeds3.R
import com.bps.plantseeds3.data.local.entity.Garden
import com.bps.plantseeds3.presentation.navigation.Screen

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
            .clickable { onGardenClick(garden) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = garden.name,
                    style = MaterialTheme.typography.titleMedium
                )
                
                if (garden.location != null && garden.location.isNotBlank()) {
                    Text(
                        text = garden.location,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            
            IconButton(
                onClick = { onDeleteGarden(garden) }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Ta bort trädgård"
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GardensScreen(
    navController: NavController,
    viewModel: GardenViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.error) {
        state.error?.let { error ->
            // Visa felmeddelande
            // TODO: Implementera felhantering
        }
    }

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
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.gardens)) },
                actions = {
                    IconButton(
                        onClick = { navController.navigate(Screen.AddEditGarden.route) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(R.string.add_garden)
                        )
                    }
                }
            )
        }
    ) { padding ->
        if (state.gardens.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.no_gardens),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.gardens) { garden ->
                    GardenItem(
                        garden = garden,
                        onGardenClick = { 
                            navController.navigate(Screen.GardenDetails.createRoute(it.id))
                        },
                        onDeleteGarden = { viewModel.deleteGarden(it) }
                    )
                }
            }
        }
    }
} 