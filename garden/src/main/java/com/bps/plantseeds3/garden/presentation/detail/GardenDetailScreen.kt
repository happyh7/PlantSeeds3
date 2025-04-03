package com.bps.plantseeds3.garden.presentation.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bps.plantseeds3.garden.domain.model.Garden
import com.bps.plantseeds3.garden.presentation.components.AddPlantDialog
import com.bps.plantseeds3.garden.presentation.components.EditPlantDialog
import com.bps.plantseeds3.garden.presentation.components.PlantList
import com.bps.plantseeds3.garden.presentation.detail.GardenDetailEvent.*
import com.bps.plantseeds3.domain.model.Plant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GardenDetailScreen(
    onNavigateBack: () -> Unit,
    viewModel: GardenDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(state.garden?.name ?: "Trädgård")
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Tillbaka")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onEvent(ShowAddPlantDialog) }
            ) {
                Icon(Icons.Default.Edit, contentDescription = "Lägg till växt")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (state.error != null) {
                Text(
                    text = state.error ?: "Ett fel uppstod",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    state.garden?.let { currentGarden ->
                        Text(
                            text = "Plats: ${currentGarden.location}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        
                        currentGarden.description?.let { description ->
                            Text(
                                text = "Beskrivning: $description",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        
                        Text(
                            text = "Växter",
                            style = MaterialTheme.typography.titleMedium
                        )
                        
                        PlantList(
                            plants = state.plants,
                            onEditPlant = { plant -> viewModel.onEvent(ShowEditPlantDialog(plant)) },
                            onDeletePlant = { plant -> viewModel.onEvent(DeletePlant(plant)) }
                        )
                    }
                }

                // Handle dialogs
                if (state.showAddPlantDialog) {
                    state.garden?.let { garden ->
                        AddPlantDialog(
                            gardenId = garden.id,
                            onDismiss = { viewModel.onEvent(HideAddPlantDialog) },
                            onConfirm = { plant ->
                                viewModel.onEvent(AddPlant(
                                    name = plant.name,
                                    species = plant.species,
                                    description = plant.description
                                ))
                            }
                        )
                    }
                }

                state.plantToEdit?.let { plant ->
                    EditPlantDialog(
                        plant = plant,
                        onDismiss = { viewModel.onEvent(HideEditPlantDialog) },
                        onConfirm = { updatedPlant ->
                            viewModel.onEvent(EditPlant(
                                plant = plant,
                                name = updatedPlant.name,
                                species = updatedPlant.species,
                                description = updatedPlant.description
                            ))
                        }
                    )
                }
            }
        }
    }
} 