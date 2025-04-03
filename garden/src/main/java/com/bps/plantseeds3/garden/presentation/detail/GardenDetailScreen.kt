package com.bps.plantseeds3.garden.presentation.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bps.plantseeds3.domain.model.Plant
import com.bps.plantseeds3.garden.presentation.components.AddPlantDialog
import com.bps.plantseeds3.garden.presentation.components.EditPlantDialog
import com.bps.plantseeds3.garden.presentation.components.PlantList

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
                title = { Text(state.garden?.name ?: "Trädgård") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Tillbaka")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onEvent(GardenDetailEvent.ShowAddPlantDialog) }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Lägg till växt")
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
            } else {
                state.garden?.let { garden ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = garden.location,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        garden.description?.let { description ->
                            Text(
                                text = description,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                        }
                        
                        Text(
                            text = "Växter",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        
                        PlantList(
                            plants = state.plants,
                            onPlantClick = { /* TODO: Implementera växtdetaljer */ },
                            onDeletePlant = { plant ->
                                viewModel.onEvent(GardenDetailEvent.DeletePlant(plant))
                            },
                            onEditPlant = { plant ->
                                viewModel.onEvent(GardenDetailEvent.ShowEditPlantDialog(plant))
                            }
                        )
                    }
                }
            }

            if (state.isAddPlantDialogVisible) {
                state.garden?.id?.let { gardenId ->
                    AddPlantDialog(
                        gardenId = gardenId,
                        onDismiss = { viewModel.onEvent(GardenDetailEvent.HideAddPlantDialog) },
                        onConfirm = { plant ->
                            viewModel.onEvent(GardenDetailEvent.AddPlant(plant))
                        }
                    )
                }
            }

            if (state.isEditPlantDialogVisible) {
                state.plantToEdit?.let { plant ->
                    EditPlantDialog(
                        plant = plant,
                        onDismiss = { viewModel.onEvent(GardenDetailEvent.HideEditPlantDialog) },
                        onConfirm = { updatedPlant ->
                            viewModel.onEvent(GardenDetailEvent.EditPlant(updatedPlant))
                        }
                    )
                }
            }
        }
    }
} 