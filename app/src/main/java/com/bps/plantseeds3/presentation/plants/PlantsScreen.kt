package com.bps.plantseeds3.presentation.plants

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
import com.bps.plantseeds3.data.local.entity.PlantStatus
import com.bps.plantseeds3.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantsScreen(
    navController: NavController,
    viewModel: PlantsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var showGardenDialog by remember { mutableStateOf(false) }
    var selectedGardenId by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(state.error) {
        state.error?.let { _ ->
            // Visa felmeddelande
            // TODO: Implementera felhantering
        }
    }

    LaunchedEffect(Unit) {
        // Återställ den senast valda trädgården om det finns en
        viewModel.getLastSelectedGardenId()?.let { id ->
            selectedGardenId = id
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.plants)) }
            )
        },
        floatingActionButton = {
            if (selectedGardenId != null) {
                FloatingActionButton(
                    onClick = { 
                        // Spara den valda trädgården innan navigering
                        viewModel.saveSelectedGardenId(selectedGardenId!!)
                        navController.navigate(Screen.AddEditPlant.route + "?gardenId=${selectedGardenId}")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.add_plant)
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Trädgårdsväljare
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        "Välj trädgård",
                        style = MaterialTheme.typography.titleMedium
                    )
                    
                    if (state.gardens.isEmpty()) {
                        Button(
                            onClick = { navController.navigate(Screen.AddEditGarden.route) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Skapa en trädgård först")
                        }
                    } else {
                        OutlinedButton(
                            onClick = { showGardenDialog = true },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                selectedGardenId?.let { id ->
                                    state.gardens.find { garden -> garden.id == id }?.name ?: "Välj trädgård"
                                } ?: "Välj trädgård"
                            )
                        }
                    }
                }
            }

            // Växtlista
            if (selectedGardenId != null) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.plants.filter { it.gardenId == selectedGardenId }) { plant ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate(Screen.AddEditPlant.route + "?plantId=${plant.id}")
                                }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = plant.name,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(
                                        text = plant.description,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                                IconButton(
                                    onClick = { viewModel.deletePlant(plant) }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Ta bort"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Trädgårdsväljardialog
    if (showGardenDialog) {
        AlertDialog(
            onDismissRequest = { showGardenDialog = false },
            title = { Text("Välj trädgård") },
            text = {
                Column {
                    state.gardens.forEach { garden ->
                        ListItem(
                            headlineContent = { 
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = garden.name)
                                    Text(
                                        text = "(${state.gardenPlantCounts[garden.id] ?: 0} växter)",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            },
                            modifier = Modifier.clickable {
                                selectedGardenId = garden.id
                                // Spara den valda trädgården när användaren väljer en ny
                                viewModel.saveSelectedGardenId(garden.id)
                                showGardenDialog = false
                            }
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showGardenDialog = false }) {
                    Text("Avbryt")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlantItem(
    plant: com.bps.plantseeds3.data.local.entity.Plant,
    onDeleteClick: () -> Unit,
    onItemClick: () -> Unit
) {
    Card(
        onClick = onItemClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = plant.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = plant.description,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = when (plant.status) {
                        PlantStatus.SEED -> "Frö"
                        PlantStatus.SEEDLING -> "Groning"
                        PlantStatus.GROWING -> "Växer"
                        PlantStatus.FLOWERING -> "Blommar"
                        PlantStatus.FRUITING -> "Sätter frukt"
                        PlantStatus.HARVESTED -> "Skördad"
                        PlantStatus.DEAD -> "Död"
                    },
                    style = MaterialTheme.typography.bodySmall
                )
            }
            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.delete),
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
} 