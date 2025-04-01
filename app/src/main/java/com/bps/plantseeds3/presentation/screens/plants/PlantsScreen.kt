package com.bps.plantseeds3.presentation.screens.plants

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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

private const val TAG = "PlantsScreen"

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
        Log.d(TAG, "PlantsScreen: Setting selected garden ID: $gardenId")
        viewModel.setSelectedGarden(gardenId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Växter") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Tillbaka")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick
            ) {
                Icon(Icons.Default.Add, contentDescription = "Lägg till växt")
            }
        }
    ) { paddingValues ->
        when (plants) {
            is Resource.Loading -> {
                Log.d(TAG, "PlantsScreen: Loading plants...")
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is Resource.Success -> {
                Log.d(TAG, "PlantsScreen: Loaded ${(plants as Resource.Success<List<Plant>>).data.size} plants")
                val plantsList = (plants as Resource.Success<List<Plant>>).data
                if (plantsList.isEmpty()) {
                    Log.d(TAG, "PlantsScreen: No plants found for garden")
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Inga växter i denna trädgård",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(vertical = 8.dp)
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
                                    Column(
                                        modifier = Modifier.weight(1f)
                                    ) {
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
                Log.e(TAG, "PlantsScreen: Error loading plants: ${(plants as Resource.Error).message}")
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = (plants as Resource.Error).message,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
} 