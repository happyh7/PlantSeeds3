package com.bps.plantseeds3.presentation.seeds.add_edit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bps.plantseeds3.R
import com.bps.plantseeds3.data.local.entity.SeedFormData
import kotlinx.coroutines.flow.collect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditSeedScreen(
    onNavigateBack: () -> Unit,
    viewModel: AddEditSeedViewModel = hiltViewModel()
) {
    val formData by viewModel.formData
    val validationErrors by viewModel.validationErrors
    val showSuggestions by viewModel.showSuggestions
    val suggestions by viewModel.suggestions
    val eventFlow = viewModel.eventFlow

    LaunchedEffect(Unit) {
        eventFlow.collect { event ->
            when (event) {
                is AddEditSeedViewModel.UiEvent.SaveSeed -> {
                    onNavigateBack()
                }
                is AddEditSeedViewModel.UiEvent.ShowError -> {
                    // Hantera felmeddelande här
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.add_edit_seed)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.onEvent(AddEditSeedEvent.SaveSeed) }
                    ) {
                        Icon(Icons.Default.Save, contentDescription = stringResource(R.string.save))
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = formData.name,
                onValueChange = { viewModel.onEvent(AddEditSeedEvent.EnteredName(it)) },
                label = { Text(stringResource(R.string.name)) },
                modifier = Modifier.fillMaxWidth(),
                isError = validationErrors.isNotEmpty()
            )

            if (validationErrors.isNotEmpty()) {
                Text(
                    text = validationErrors.joinToString("\n"),
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = showSuggestions,
                    onCheckedChange = { viewModel.onEvent(AddEditSeedEvent.ToggleSuggestions) }
                )
                Text(stringResource(R.string.show_suggestions))
            }

            if (showSuggestions && suggestions.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column {
                        suggestions.forEach { suggestion ->
                            ListItem(
                                headlineContent = { Text(suggestion.name) },
                                supportingContent = { Text(suggestion.species) },
                                modifier = Modifier.clickable {
                                    viewModel.onEvent(AddEditSeedEvent.SelectSuggestion(suggestion))
                                }
                            )
                        }
                    }
                }
            }

            // Grundläggande information
            Text(
                text = stringResource(R.string.basic_info),
                style = MaterialTheme.typography.titleMedium
            )

            OutlinedTextField(
                value = formData.scientificName,
                onValueChange = { viewModel.onEvent(AddEditSeedEvent.EnteredScientificName(it)) },
                label = { Text(stringResource(R.string.scientific_name)) },
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = formData.species,
                    onValueChange = { viewModel.onEvent(AddEditSeedEvent.EnteredSpecies(it)) },
                    label = { Text(stringResource(R.string.species)) },
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = formData.variety,
                    onValueChange = { viewModel.onEvent(AddEditSeedEvent.EnteredVariety(it)) },
                    label = { Text(stringResource(R.string.variety)) },
                    modifier = Modifier.weight(1f)
                )
            }

            OutlinedTextField(
                value = formData.category,
                onValueChange = { viewModel.onEvent(AddEditSeedEvent.EnteredCategory(it)) },
                label = { Text(stringResource(R.string.category)) },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = formData.description,
                onValueChange = { viewModel.onEvent(AddEditSeedEvent.EnteredDescription(it)) },
                label = { Text(stringResource(R.string.description)) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            // Planteringsinstruktioner
            Text(
                text = stringResource(R.string.planting_instructions),
                style = MaterialTheme.typography.titleMedium
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = formData.plantingDepth,
                    onValueChange = { viewModel.onEvent(AddEditSeedEvent.EnteredPlantingDepth(it)) },
                    label = { Text(stringResource(R.string.planting_depth)) },
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = formData.plantingDistance,
                    onValueChange = { viewModel.onEvent(AddEditSeedEvent.EnteredPlantingDistance(it)) },
                    label = { Text(stringResource(R.string.planting_distance)) },
                    modifier = Modifier.weight(1f)
                )
            }

            OutlinedTextField(
                value = formData.plantingDates,
                onValueChange = { viewModel.onEvent(AddEditSeedEvent.EnteredPlantingDates(it)) },
                label = { Text(stringResource(R.string.planting_dates)) },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = formData.sowingInstructions,
                onValueChange = { viewModel.onEvent(AddEditSeedEvent.EnteredSowingInstructions(it)) },
                label = { Text(stringResource(R.string.sowing_instructions)) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            // Växtförhållanden
            Text(
                text = stringResource(R.string.growing_conditions),
                style = MaterialTheme.typography.titleMedium
            )

            OutlinedTextField(
                value = formData.sunRequirement,
                onValueChange = { viewModel.onEvent(AddEditSeedEvent.EnteredSunRequirement(it)) },
                label = { Text(stringResource(R.string.sun_requirement)) },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = formData.waterRequirement,
                onValueChange = { viewModel.onEvent(AddEditSeedEvent.EnteredWaterRequirement(it)) },
                label = { Text(stringResource(R.string.water_requirement)) },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = formData.soilRequirement,
                onValueChange = { viewModel.onEvent(AddEditSeedEvent.EnteredSoilRequirement(it)) },
                label = { Text(stringResource(R.string.soil_requirement)) },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = formData.hardiness,
                onValueChange = { viewModel.onEvent(AddEditSeedEvent.EnteredHardiness(it)) },
                label = { Text(stringResource(R.string.hardiness)) },
                modifier = Modifier.fillMaxWidth()
            )

            // Skötselinstruktioner
            Text(
                text = stringResource(R.string.maintenance_instructions),
                style = MaterialTheme.typography.titleMedium
            )

            OutlinedTextField(
                value = formData.growingInstructions,
                onValueChange = { viewModel.onEvent(AddEditSeedEvent.EnteredGrowingInstructions(it)) },
                label = { Text(stringResource(R.string.growing_instructions)) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            OutlinedTextField(
                value = formData.harvestInstructions,
                onValueChange = { viewModel.onEvent(AddEditSeedEvent.EnteredHarvestInstructions(it)) },
                label = { Text(stringResource(R.string.harvest_instructions)) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = formData.daysToGermination,
                    onValueChange = { viewModel.onEvent(AddEditSeedEvent.EnteredDaysToGermination(it)) },
                    label = { Text(stringResource(R.string.days_to_germination)) },
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = formData.daysToHarvest,
                    onValueChange = { viewModel.onEvent(AddEditSeedEvent.EnteredDaysToHarvest(it)) },
                    label = { Text(stringResource(R.string.days_to_harvest)) },
                    modifier = Modifier.weight(1f)
                )
            }

            OutlinedTextField(
                value = formData.harvestPeriod,
                onValueChange = { viewModel.onEvent(AddEditSeedEvent.EnteredHarvestPeriod(it)) },
                label = { Text(stringResource(R.string.harvest_period)) },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = formData.maintenanceDates,
                onValueChange = { viewModel.onEvent(AddEditSeedEvent.EnteredMaintenanceDates(it)) },
                label = { Text(stringResource(R.string.maintenance_dates)) },
                modifier = Modifier.fillMaxWidth()
            )

            // Följeslagare
            Text(
                text = stringResource(R.string.companions),
                style = MaterialTheme.typography.titleMedium
            )

            OutlinedTextField(
                value = formData.companionPlants,
                onValueChange = { viewModel.onEvent(AddEditSeedEvent.EnteredCompanionPlants(it)) },
                label = { Text(stringResource(R.string.companion_plants)) },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = formData.avoidPlants,
                onValueChange = { viewModel.onEvent(AddEditSeedEvent.EnteredAvoidPlants(it)) },
                label = { Text(stringResource(R.string.avoid_plants)) },
                modifier = Modifier.fillMaxWidth()
            )

            // Övriga detaljer
            Text(
                text = stringResource(R.string.other_details),
                style = MaterialTheme.typography.titleMedium
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = formData.plantSpacing,
                    onValueChange = { viewModel.onEvent(AddEditSeedEvent.EnteredPlantSpacing(it)) },
                    label = { Text(stringResource(R.string.plant_spacing)) },
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = formData.rowSpacing,
                    onValueChange = { viewModel.onEvent(AddEditSeedEvent.EnteredRowSpacing(it)) },
                    label = { Text(stringResource(R.string.row_spacing)) },
                    modifier = Modifier.weight(1f)
                )
            }

            OutlinedTextField(
                value = formData.tags,
                onValueChange = { viewModel.onEvent(AddEditSeedEvent.EnteredTags(it)) },
                label = { Text(stringResource(R.string.tags)) },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = formData.notes,
                onValueChange = { viewModel.onEvent(AddEditSeedEvent.EnteredNotes(it)) },
                label = { Text(stringResource(R.string.notes)) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
        }
    }
} 

