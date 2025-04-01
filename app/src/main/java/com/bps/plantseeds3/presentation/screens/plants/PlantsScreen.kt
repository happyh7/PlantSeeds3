package com.bps.plantseeds3.presentation.screens.plants

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bps.plantseeds3.common.model.Resource
import com.bps.plantseeds3.domain.model.Plant
import com.bps.plantseeds3.presentation.screens.plants.viewmodel.PlantsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantsScreen(
    gardenId: String,
    onAddClick: () -> Unit,
    onPlantClick: (String) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: PlantsViewModel = hiltViewModel()
) {
    val plants by viewModel.plants.collectAsState()

    LaunchedEffect(gardenId) {
        viewModel.setSelectedGarden(gardenId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Växter") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Tillbaka")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = "Lägg till växt")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (plants) {
                is Resource.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is Resource.Success -> {
                    val plantsList = (plants as Resource.Success<List<Plant>>).data
                    if (plantsList.isEmpty()) {
                        Text(
                            text = "Inga växter i denna trädgård",
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                                .padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(plantsList) { plant ->
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    onClick = { onPlantClick(plant.id) }
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
                                                text = plant.species,
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        }
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
                is Resource.Error -> {
                    Text(
                        text = (plants as Resource.Error).message,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
} 