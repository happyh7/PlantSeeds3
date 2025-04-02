package com.bps.plantseeds3.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bps.plantseeds3.presentation.ui.viewmodel.EditSeedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditSeedScreen(
    onNavigateBack: () -> Unit,
    onSaveSeed: () -> Unit,
    viewModel: EditSeedViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()
    val focusRequester = remember { FocusRequester() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Redigera frö") },
                navigationIcon = {
                    IconButton(onClick = {
                        focusManager.clearFocus()
                        onNavigateBack()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Tillbaka")
                    }
                },
                actions = {
                    TextButton(
                        onClick = {
                            focusManager.clearFocus()
                            viewModel.onSaveSeed {
                                onSaveSeed()
                            }
                        },
                        enabled = !uiState.isLoading
                    ) {
                        Text("Spara")
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
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(scrollState),
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
                            OutlinedTextField(
                                value = uiState.name,
                                onValueChange = { viewModel.onNameChange(it) },
                                label = { Text("Namn") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .focusRequester(focusRequester),
                                enabled = !uiState.isLoading
                            )
                            OutlinedTextField(
                                value = uiState.species ?: "",
                                onValueChange = { viewModel.onSpeciesChange(it) },
                                label = { Text("Art") },
                                modifier = Modifier.fillMaxWidth(),
                                enabled = !uiState.isLoading
                            )
                            OutlinedTextField(
                                value = uiState.description ?: "",
                                onValueChange = { viewModel.onDescriptionChange(it) },
                                label = { Text("Beskrivning") },
                                modifier = Modifier.fillMaxWidth(),
                                minLines = 3,
                                enabled = !uiState.isLoading
                            )
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
                            OutlinedTextField(
                                value = uiState.plantingInstructions ?: "",
                                onValueChange = { viewModel.onPlantingInstructionsChange(it) },
                                label = { Text("Odlingsinstruktioner") },
                                modifier = Modifier.fillMaxWidth(),
                                minLines = 3,
                                enabled = !uiState.isLoading
                            )
                            OutlinedTextField(
                                value = uiState.daysToGermination?.toString() ?: "",
                                onValueChange = { value ->
                                    value.toIntOrNull()?.let { viewModel.onDaysToGerminationChange(it) }
                                },
                                label = { Text("Dagar till grodd") },
                                modifier = Modifier.fillMaxWidth(),
                                enabled = !uiState.isLoading
                            )
                            OutlinedTextField(
                                value = uiState.daysToHarvest?.toString() ?: "",
                                onValueChange = { value ->
                                    value.toIntOrNull()?.let { viewModel.onDaysToHarvestChange(it) }
                                },
                                label = { Text("Dagar till skörd") },
                                modifier = Modifier.fillMaxWidth(),
                                enabled = !uiState.isLoading
                            )
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
                            OutlinedTextField(
                                value = uiState.lightNeeds ?: "",
                                onValueChange = { viewModel.onLightNeedsChange(it) },
                                label = { Text("Ljusbehov") },
                                modifier = Modifier.fillMaxWidth(),
                                enabled = !uiState.isLoading
                            )
                            OutlinedTextField(
                                value = uiState.waterNeeds ?: "",
                                onValueChange = { viewModel.onWaterNeedsChange(it) },
                                label = { Text("Vattenbehov") },
                                modifier = Modifier.fillMaxWidth(),
                                enabled = !uiState.isLoading
                            )
                            OutlinedTextField(
                                value = uiState.soilType ?: "",
                                onValueChange = { viewModel.onSoilTypeChange(it) },
                                label = { Text("Jordtyp") },
                                modifier = Modifier.fillMaxWidth(),
                                enabled = !uiState.isLoading
                            )
                            OutlinedTextField(
                                value = uiState.temperature ?: "",
                                onValueChange = { viewModel.onTemperatureChange(it) },
                                label = { Text("Temperatur") },
                                modifier = Modifier.fillMaxWidth(),
                                enabled = !uiState.isLoading
                            )
                            OutlinedTextField(
                                value = uiState.spacing ?: "",
                                onValueChange = { viewModel.onSpacingChange(it) },
                                label = { Text("Avstånd") },
                                modifier = Modifier.fillMaxWidth(),
                                enabled = !uiState.isLoading
                            )
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
                            OutlinedTextField(
                                value = uiState.companionPlants?.joinToString(", ") ?: "",
                                onValueChange = { value ->
                                    viewModel.onCompanionPlantsChange(
                                        value.split(",").map { it.trim() }.filter { it.isNotBlank() }
                                    )
                                },
                                label = { Text("Följeslagare (separera med kommatecken)") },
                                modifier = Modifier.fillMaxWidth(),
                                enabled = !uiState.isLoading
                            )
                            OutlinedTextField(
                                value = uiState.avoidPlants?.joinToString(", ") ?: "",
                                onValueChange = { value ->
                                    viewModel.onAvoidPlantsChange(
                                        value.split(",").map { it.trim() }.filter { it.isNotBlank() }
                                    )
                                },
                                label = { Text("Växter att undvika (separera med kommatecken)") },
                                modifier = Modifier.fillMaxWidth(),
                                enabled = !uiState.isLoading
                            )
                        }
                    }

                    uiState.error?.let { error ->
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
} 