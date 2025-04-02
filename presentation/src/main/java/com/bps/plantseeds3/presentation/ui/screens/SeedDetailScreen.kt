package com.bps.plantseeds3.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bps.plantseeds3.domain.model.Seed
import com.bps.plantseeds3.presentation.ui.state.SeedDetailUiState
import com.bps.plantseeds3.presentation.ui.viewmodel.SeedDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeedDetailScreen(
    onNavigateBack: () -> Unit,
    onEditSeed: () -> Unit,
    onDeleteSeed: () -> Unit,
    viewModel: SeedDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Frödetaljer") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Tillbaka")
                    }
                },
                actions = {
                    IconButton(onClick = onEditSeed) {
                        Icon(Icons.Default.Edit, contentDescription = "Redigera")
                    }
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(Icons.Default.Delete, contentDescription = "Ta bort")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                uiState.error != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = uiState.error ?: "Ett fel uppstod",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = onNavigateBack) {
                            Text("Tillbaka")
                        }
                    }
                }
                uiState.seed != null -> {
                    val seed = uiState.seed!!
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Grundläggande information
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
                                    text = "Grundläggande information",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = "Namn: ${seed.name}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                seed.species?.let { species ->
                                    Text(
                                        text = "Art: $species",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                                seed.description?.let { description ->
                                    if (description.isNotBlank()) {
                                        Text(
                                            text = "Beskrivning: $description",
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                }
                            }
                        }

                        // Odlingsinstruktioner
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
                                    text = "Odlingsinstruktioner",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                seed.plantingInstructions?.let { instructions ->
                                    if (instructions.isNotBlank()) {
                                        Text(
                                            text = instructions,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                }
                                seed.daysToGermination?.let { days ->
                                    Text(
                                        text = "Dagar till grodd: $days",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                                seed.daysToHarvest?.let { days ->
                                    Text(
                                        text = "Dagar till skörd: $days",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }

                        // Växtens behov
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
                                    text = "Växtens behov",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                seed.lightNeeds?.let { light ->
                                    Text(
                                        text = "Ljus: $light",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                                seed.waterNeeds?.let { water ->
                                    Text(
                                        text = "Vatten: $water",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                                seed.soilType?.let { soil ->
                                    Text(
                                        text = "Jordtyp: $soil",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                                seed.temperature?.let { temp ->
                                    Text(
                                        text = "Temperatur: $temp",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                                seed.spacing?.let { space ->
                                    Text(
                                        text = "Avstånd: $space",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }

                        // Följeslagare och växter att undvika
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
                                    text = "Följeslagare och växter att undvika",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                seed.companionPlants?.let { companions ->
                                    if (companions.isNotEmpty()) {
                                        Text(
                                            text = "Följeslagare: ${companions.joinToString(", ")}",
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                }
                                seed.avoidPlants?.let { avoid ->
                                    if (avoid.isNotEmpty()) {
                                        Text(
                                            text = "Växter att undvika: ${avoid.joinToString(", ")}",
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Ta bort frö") },
            text = { Text("Är du säker på att du vill ta bort detta frö?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        onDeleteSeed()
                    }
                ) {
                    Text("Ta bort")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Avbryt")
                }
            }
        )
    }
} 