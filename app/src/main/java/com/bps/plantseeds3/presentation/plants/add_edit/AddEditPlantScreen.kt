package com.bps.plantseeds3.presentation.plants.add_edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bps.plantseeds3.domain.model.PlantCategory
import com.bps.plantseeds3.domain.model.PlantStatus
import com.bps.plantseeds3.presentation.components.CapitalizedTextField
import java.time.LocalDate
import androidx.compose.foundation.clickable
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.rememberDatePickerState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditPlantScreen(
    onNavigateBack: () -> Unit,
    onNavigateToGardenDetails: (String) -> Unit,
    viewModel: AddEditPlantViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var showPlantingDatePicker by remember { mutableStateOf(false) }
    var showExpectedHarvestDatePicker by remember { mutableStateOf(false) }
    var showActualHarvestDatePicker by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.onEvent(AddEditPlantEvent.OnLoadPlant)
    }

    LaunchedEffect(state.isSaved) {
        if (state.isSaved) {
            onNavigateBack()
        }
    }

    LaunchedEffect(state.error) {
        state.error?.let { error ->
            snackbarHostState.showSnackbar(
                message = error,
                duration = SnackbarDuration.Short
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lägg till/Redigera växt") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Tillbaka")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = state.name,
                onValueChange = { viewModel.onEvent(AddEditPlantEvent.OnNameChange(it)) },
                label = { Text("Namn") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.scientificName,
                onValueChange = { viewModel.onEvent(AddEditPlantEvent.OnScientificNameChange(it)) },
                label = { Text("Vetenskapligt namn") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.species,
                onValueChange = { viewModel.onEvent(AddEditPlantEvent.OnSpeciesChange(it)) },
                label = { Text("Art") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.variety,
                onValueChange = { viewModel.onEvent(AddEditPlantEvent.OnVarietyChange(it)) },
                label = { Text("Sort") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.description,
                onValueChange = { viewModel.onEvent(AddEditPlantEvent.OnDescriptionChange(it)) },
                label = { Text("Beskrivning") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { showPlantingDatePicker = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Planteringsdatum: ${state.plantingDate ?: "Ej valt"}")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { showExpectedHarvestDatePicker = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Förväntad skördedatum: ${state.expectedHarvestDate ?: "Ej valt"}")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { showActualHarvestDatePicker = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Faktiskt skördedatum: ${state.actualHarvestDate ?: "Ej valt"}")
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.sowingDepth,
                onValueChange = { viewModel.onEvent(AddEditPlantEvent.OnSowingDepthChange(it)) },
                label = { Text("Sådjup (cm)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.spacing,
                onValueChange = { viewModel.onEvent(AddEditPlantEvent.OnSpacingChange(it)) },
                label = { Text("Avstånd mellan plantor (cm)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.daysToGermination,
                onValueChange = { viewModel.onEvent(AddEditPlantEvent.OnDaysToGerminationChange(it)) },
                label = { Text("Dagar till grodd") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.daysToMaturity,
                onValueChange = { viewModel.onEvent(AddEditPlantEvent.OnDaysToMaturityChange(it)) },
                label = { Text("Dagar till mognad") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.sunRequirement,
                onValueChange = { viewModel.onEvent(AddEditPlantEvent.OnSunRequirementChange(it)) },
                label = { Text("Solbehov") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.waterRequirement,
                onValueChange = { viewModel.onEvent(AddEditPlantEvent.OnWaterRequirementChange(it)) },
                label = { Text("Vattenbehov") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.soilRequirement,
                onValueChange = { viewModel.onEvent(AddEditPlantEvent.OnSoilRequirementChange(it)) },
                label = { Text("Jordbehov") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.soilPh,
                onValueChange = { viewModel.onEvent(AddEditPlantEvent.OnSoilPhChange(it)) },
                label = { Text("Jord pH") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.hardiness,
                onValueChange = { viewModel.onEvent(AddEditPlantEvent.OnHardinessChange(it)) },
                label = { Text("Hårdhet") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.sowingInstructions,
                onValueChange = { viewModel.onEvent(AddEditPlantEvent.OnSowingInstructionsChange(it)) },
                label = { Text("Såinstruktioner") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.growingInstructions,
                onValueChange = { viewModel.onEvent(AddEditPlantEvent.OnGrowingInstructionsChange(it)) },
                label = { Text("Odlingsinstruktioner") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.harvestInstructions,
                onValueChange = { viewModel.onEvent(AddEditPlantEvent.OnHarvestInstructionsChange(it)) },
                label = { Text("Skördeinstruktioner") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.storageInstructions,
                onValueChange = { viewModel.onEvent(AddEditPlantEvent.OnStorageInstructionsChange(it)) },
                label = { Text("Förvaringsinstruktioner") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.companionPlants,
                onValueChange = { viewModel.onEvent(AddEditPlantEvent.OnCompanionPlantsChange(it)) },
                label = { Text("Följväxter") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.avoidPlants,
                onValueChange = { viewModel.onEvent(AddEditPlantEvent.OnAvoidPlantsChange(it)) },
                label = { Text("Undvik växter") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.height,
                onValueChange = { viewModel.onEvent(AddEditPlantEvent.OnHeightChange(it)) },
                label = { Text("Höjd (cm)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.spread,
                onValueChange = { viewModel.onEvent(AddEditPlantEvent.OnSpreadChange(it)) },
                label = { Text("Utbredning (cm)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.yield,
                onValueChange = { viewModel.onEvent(AddEditPlantEvent.OnYieldChange(it)) },
                label = { Text("Skörd") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.culinaryUses,
                onValueChange = { viewModel.onEvent(AddEditPlantEvent.OnCulinaryUsesChange(it)) },
                label = { Text("Kulinariska användningar") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.medicinalUses,
                onValueChange = { viewModel.onEvent(AddEditPlantEvent.OnMedicinalUsesChange(it)) },
                label = { Text("Medicinska användningar") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.tags,
                onValueChange = { viewModel.onEvent(AddEditPlantEvent.OnTagsChange(it)) },
                label = { Text("Taggar") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.notes,
                onValueChange = { viewModel.onEvent(AddEditPlantEvent.OnNotesChange(it)) },
                label = { Text("Anteckningar") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.onEvent(AddEditPlantEvent.OnShowCategoryDialog) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Välj kategori: ${state.category}")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { viewModel.onEvent(AddEditPlantEvent.OnShowStatusDialog) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Välj status: ${state.status}")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.onEvent(AddEditPlantEvent.OnSaveClick) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Spara")
            }
        }
    }

    if (showPlantingDatePicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showPlantingDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val localDate = LocalDate.ofEpochDay(millis / (24 * 60 * 60 * 1000))
                        viewModel.onEvent(AddEditPlantEvent.OnPlantingDateChange(localDate))
                    }
                    showPlantingDatePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showPlantingDatePicker = false }) {
                    Text("Avbryt")
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                showModeToggle = false
            )
        }
    }

    if (showExpectedHarvestDatePicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showExpectedHarvestDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val localDate = LocalDate.ofEpochDay(millis / (24 * 60 * 60 * 1000))
                        viewModel.onEvent(AddEditPlantEvent.OnExpectedHarvestDateChange(localDate))
                    }
                    showExpectedHarvestDatePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showExpectedHarvestDatePicker = false }) {
                    Text("Avbryt")
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                showModeToggle = false
            )
        }
    }

    if (showActualHarvestDatePicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showActualHarvestDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val localDate = LocalDate.ofEpochDay(millis / (24 * 60 * 60 * 1000))
                        viewModel.onEvent(AddEditPlantEvent.OnActualHarvestDateChange(localDate))
                    }
                    showActualHarvestDatePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showActualHarvestDatePicker = false }) {
                    Text("Avbryt")
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                showModeToggle = false
            )
        }
    }

    if (state.showCategoryDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.onEvent(AddEditPlantEvent.OnHideCategoryDialog) },
            title = { Text("Välj kategori") },
            text = {
                Column {
                    PlantCategory.values().forEach { category ->
                        TextButton(
                            onClick = {
                                viewModel.onEvent(AddEditPlantEvent.OnCategoryChange(category))
                                viewModel.onEvent(AddEditPlantEvent.OnHideCategoryDialog)
                            }
                        ) {
                            Text(category.displayName)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { viewModel.onEvent(AddEditPlantEvent.OnHideCategoryDialog) }) {
                    Text("Avbryt")
                }
            }
        )
    }

    if (state.showStatusDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.onEvent(AddEditPlantEvent.OnHideStatusDialog) },
            title = { Text("Välj status") },
            text = {
                Column {
                    PlantStatus.values().forEach { status ->
                        TextButton(
                            onClick = {
                                viewModel.onEvent(AddEditPlantEvent.OnStatusChange(status))
                                viewModel.onEvent(AddEditPlantEvent.OnHideStatusDialog)
                            }
                        ) {
                            Text(status.displayName)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { viewModel.onEvent(AddEditPlantEvent.OnHideStatusDialog) }) {
                    Text("Avbryt")
                }
            }
        )
    }
} 