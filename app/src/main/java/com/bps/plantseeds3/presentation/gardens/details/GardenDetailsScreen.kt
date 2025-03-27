package com.bps.plantseeds3.presentation.gardens.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bps.plantseeds3.R
import com.bps.plantseeds3.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GardenDetailsScreen(
    gardenId: String,
    navController: NavController,
    viewModel: GardenDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val error = state.error

    LaunchedEffect(gardenId) {
        viewModel.loadGarden(gardenId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(state.garden?.name ?: stringResource(R.string.garden_details)) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Tillbaka")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate(Screen.AddEditGarden.route + "?gardenId=$gardenId")
                        }
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = "Redigera")
                    }
                }
            )
        }
    ) { padding ->
        if (error != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = error,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
            }
        } else if (state.garden == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Trädgårdsinformation
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.garden_details),
                            style = MaterialTheme.typography.titleLarge
                        )
                        
                        val garden = state.garden
                        garden?.let {
                            it.location?.let { location ->
                                Text(
                                    text = "Plats: $location",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                            
                            it.size?.let { size ->
                                Text(
                                    text = "Storlek: ${stringResource(R.string.garden_size_format, size)}",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                            
                            it.description?.let { description ->
                                Text(
                                    text = "Beskrivning: $description",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                            
                            it.soilType?.let { soilType ->
                                Text(
                                    text = "Jordtyp: $soilType",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                            
                            it.sunExposure?.let { sunExposure ->
                                Text(
                                    text = "Solsken: $sunExposure",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }
                }

                // Växtlista
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Växter i trädgården",
                            style = MaterialTheme.typography.titleLarge
                        )
                        
                        if (state.plants.isEmpty()) {
                            Text(
                                text = "Inga växter tillagda än",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        } else {
                            state.plants.forEach { plant ->
                                Text(
                                    text = plant.name,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }
                }

                // Statistik
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Statistik",
                            style = MaterialTheme.typography.titleLarge
                        )
                        
                        Text(
                            text = "Antal växter: ${state.plants.size}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        
                        // Här kan vi lägga till mer statistik senare
                    }
                }
            }
        }
    }
} 