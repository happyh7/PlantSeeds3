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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bps.plantseeds3.presentation.R
import com.bps.plantseeds3.presentation.ui.viewmodel.AddSeedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSeedScreen(
    onNavigateBack: () -> Unit,
    onSaveSeed: (String, String, String?, String?, Int?, Int?, String?, String?, String?, String?, String?) -> Unit,
    viewModel: AddSeedViewModel = hiltViewModel()
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
                title = { Text(stringResource(R.string.add_seed)) },
                navigationIcon = {
                    IconButton(onClick = {
                        focusManager.clearFocus()
                        onNavigateBack()
                    }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (uiState.name.isNotBlank()) {
                        viewModel.saveSeed {
                            onSaveSeed(
                                uiState.name,
                                uiState.species,
                                uiState.description.ifBlank { null },
                                uiState.plantingInstructions.ifBlank { null },
                                uiState.daysToGermination,
                                uiState.daysToHarvest,
                                uiState.lightNeeds.ifBlank { null },
                                uiState.waterNeeds.ifBlank { null },
                                uiState.soilType.ifBlank { null },
                                uiState.temperature.ifBlank { null },
                                uiState.spacing.ifBlank { null }
                            )
                        }
                    }
                }
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text(stringResource(R.string.save))
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = uiState.name,
                    onValueChange = viewModel::onNameChange,
                    label = { Text(stringResource(R.string.name)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(nameFocusRequester),
                    singleLine = true,
                    enabled = !uiState.isLoading
                )

                OutlinedTextField(
                    value = uiState.species,
                    onValueChange = viewModel::onSpeciesChange,
                    label = { Text(stringResource(R.string.species)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(speciesFocusRequester),
                    singleLine = true,
                    enabled = !uiState.isLoading
                )

                OutlinedTextField(
                    value = uiState.description,
                    onValueChange = viewModel::onDescriptionChange,
                    label = { Text(stringResource(R.string.description)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(descriptionFocusRequester),
                    minLines = 3,
                    enabled = !uiState.isLoading
                )

                OutlinedTextField(
                    value = uiState.plantingInstructions,
                    onValueChange = viewModel::onPlantingInstructionsChange,
                    label = { Text("Planteringsinstruktioner") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(plantingInstructionsFocusRequester),
                    minLines = 3,
                    enabled = !uiState.isLoading
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
                    singleLine = true,
                    enabled = !uiState.isLoading
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
                    singleLine = true,
                    enabled = !uiState.isLoading
                )

                OutlinedTextField(
                    value = uiState.lightNeeds,
                    onValueChange = viewModel::onLightNeedsChange,
                    label = { Text("Ljusbehov") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(lightNeedsFocusRequester),
                    singleLine = true,
                    enabled = !uiState.isLoading
                )

                OutlinedTextField(
                    value = uiState.waterNeeds,
                    onValueChange = viewModel::onWaterNeedsChange,
                    label = { Text("Vattenbehov") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(waterNeedsFocusRequester),
                    singleLine = true,
                    enabled = !uiState.isLoading
                )

                OutlinedTextField(
                    value = uiState.soilType,
                    onValueChange = viewModel::onSoilTypeChange,
                    label = { Text("Jordtyp") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(soilTypeFocusRequester),
                    singleLine = true,
                    enabled = !uiState.isLoading
                )

                OutlinedTextField(
                    value = uiState.temperature,
                    onValueChange = viewModel::onTemperatureChange,
                    label = { Text("Temperatur") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(temperatureFocusRequester),
                    singleLine = true,
                    enabled = !uiState.isLoading
                )

                OutlinedTextField(
                    value = uiState.spacing,
                    onValueChange = viewModel::onSpacingChange,
                    label = { Text("Avstånd") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(spacingFocusRequester),
                    singleLine = true,
                    enabled = !uiState.isLoading
                )

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