package com.bps.plantseeds3.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
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
import com.bps.plantseeds3.presentation.ui.viewmodel.EditSeedUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditSeedScreen(
    viewModel: EditSeedViewModel,
    onNavigateBack: () -> Unit,
    onSaveSeed: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    // Skapa FocusRequester för varje textfält
    val nameFocusRequester = remember { FocusRequester() }
    val speciesFocusRequester = remember { FocusRequester() }
    val descriptionFocusRequester = remember { FocusRequester() }
    val plantingInstructionsFocusRequester = remember { FocusRequester() }
    val daysToGerminationFocusRequester = remember { FocusRequester() }
    val daysToHarvestFocusRequester = remember { FocusRequester() }
    val lightNeedsFocusRequester = remember { FocusRequester() }
    val waterNeedsFocusRequester = remember { FocusRequester() }
    val soilTypeFocusRequester = remember { FocusRequester() }
    val temperatureFocusRequester = remember { FocusRequester() }
    val spacingFocusRequester = remember { FocusRequester() }

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
                    IconButton(
                        onClick = {
                            focusManager.clearFocus()
                            viewModel.onSaveSeed {
                                onSaveSeed()
                            }
                        },
                        enabled = !uiState.isLoading
                    ) {
                        Icon(Icons.Default.Save, contentDescription = "Spara")
                    }
                }
            )
        }
    ) { padding ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(scrollState)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = uiState.name,
                    onValueChange = viewModel::onNameChange,
                    label = { Text("Namn") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(nameFocusRequester),
                    singleLine = true
                )

                OutlinedTextField(
                    value = uiState.species ?: "",
                    onValueChange = viewModel::onSpeciesChange,
                    label = { Text("Art") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(speciesFocusRequester),
                    singleLine = true
                )

                OutlinedTextField(
                    value = uiState.description ?: "",
                    onValueChange = viewModel::onDescriptionChange,
                    label = { Text("Beskrivning") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(descriptionFocusRequester),
                    minLines = 3
                )

                OutlinedTextField(
                    value = uiState.plantingInstructions ?: "",
                    onValueChange = viewModel::onPlantingInstructionsChange,
                    label = { Text("Planteringsinstruktioner") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(plantingInstructionsFocusRequester),
                    minLines = 3
                )

                OutlinedTextField(
                    value = uiState.daysToGermination?.toString() ?: "",
                    onValueChange = { value ->
                        value.toIntOrNull()?.let { viewModel.onDaysToGerminationChange(it) }
                    },
                    label = { Text("Dagar till grodd") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(daysToGerminationFocusRequester),
                    singleLine = true
                )

                OutlinedTextField(
                    value = uiState.daysToHarvest?.toString() ?: "",
                    onValueChange = { value ->
                        value.toIntOrNull()?.let { viewModel.onDaysToHarvestChange(it) }
                    },
                    label = { Text("Dagar till skörd") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(daysToHarvestFocusRequester),
                    singleLine = true
                )

                OutlinedTextField(
                    value = uiState.lightNeeds ?: "",
                    onValueChange = viewModel::onLightNeedsChange,
                    label = { Text("Ljusbehov") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(lightNeedsFocusRequester),
                    singleLine = true
                )

                OutlinedTextField(
                    value = uiState.waterNeeds ?: "",
                    onValueChange = viewModel::onWaterNeedsChange,
                    label = { Text("Vattenbehov") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(waterNeedsFocusRequester),
                    singleLine = true
                )

                OutlinedTextField(
                    value = uiState.soilType ?: "",
                    onValueChange = viewModel::onSoilTypeChange,
                    label = { Text("Jordtyp") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(soilTypeFocusRequester),
                    singleLine = true
                )

                OutlinedTextField(
                    value = uiState.temperature ?: "",
                    onValueChange = viewModel::onTemperatureChange,
                    label = { Text("Temperatur") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(temperatureFocusRequester),
                    singleLine = true
                )

                OutlinedTextField(
                    value = uiState.spacing ?: "",
                    onValueChange = viewModel::onSpacingChange,
                    label = { Text("Avstånd") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(spacingFocusRequester),
                    singleLine = true
                )

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
                            onValueChange = { viewModel.onCompanionPlantsChange(it.split(",").map { it.trim() }) },
                            label = { Text("Följeslagare (kommaseparerade)") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = uiState.avoidPlants?.joinToString(", ") ?: "",
                            onValueChange = { viewModel.onAvoidPlantsChange(it.split(",").map { it.trim() }) },
                            label = { Text("Växter att undvika (kommaseparerade)") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                    }
                }

                if (uiState.error != null) {
                    Text(
                        text = uiState.error!!,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
} 