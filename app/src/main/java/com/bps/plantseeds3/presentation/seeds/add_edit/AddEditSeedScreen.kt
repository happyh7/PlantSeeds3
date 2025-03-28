package com.bps.plantseeds3.presentation.seeds.add_edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bps.plantseeds3.presentation.seeds.add_edit.components.*
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditSeedScreen(
    seedId: String?,
    navController: NavController,
    viewModel: AddEditSeedViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditSeedViewModel.UiEvent.SaveSeed -> {
                    navController.navigateUp()
                }
                is AddEditSeedViewModel.UiEvent.ShowError -> {
                    errorMessage = event.message
                }
            }
        }
    }

    LaunchedEffect(seedId) {
        seedId?.let { viewModel.loadSeed(it) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (seedId == null) "Lägg till frö" else "Redigera frö") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Tillbaka")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BasicInfoSection(
                state = state,
                onUpdateName = { viewModel.onEvent(AddEditSeedEvent.EnteredName(it)) },
                onUpdateScientificName = { viewModel.onEvent(AddEditSeedEvent.EnteredScientificName(it)) },
                onUpdateSpecies = { viewModel.onEvent(AddEditSeedEvent.EnteredSpecies(it)) },
                onUpdateVariety = { viewModel.onEvent(AddEditSeedEvent.EnteredVariety(it)) },
                onUpdateCategory = { viewModel.onEvent(AddEditSeedEvent.EnteredCategory(it)) },
                onUpdateDescription = { viewModel.onEvent(AddEditSeedEvent.EnteredDescription(it)) },
                errorMessage = errorMessage
            )

            PlantingDetailsSection(
                state = state,
                onUpdatePlantingDepth = { viewModel.onEvent(AddEditSeedEvent.EnteredPlantingDepth(it)) },
                onUpdatePlantingDistance = { viewModel.onEvent(AddEditSeedEvent.EnteredPlantingDistance(it)) },
                onUpdatePlantingDates = { viewModel.onEvent(AddEditSeedEvent.EnteredPlantingDates(it)) }
            )

            GrowingConditionsSection(
                state = state,
                onUpdateSunRequirement = { viewModel.onEvent(AddEditSeedEvent.EnteredSunRequirement(it)) },
                onUpdateWaterRequirement = { viewModel.onEvent(AddEditSeedEvent.EnteredWaterRequirement(it)) },
                onUpdateSoilRequirement = { viewModel.onEvent(AddEditSeedEvent.EnteredSoilRequirement(it)) },
                onUpdateHardiness = { viewModel.onEvent(AddEditSeedEvent.EnteredHardiness(it)) }
            )

            InstructionsSection(
                state = state,
                onUpdateSowingInstructions = { viewModel.onEvent(AddEditSeedEvent.EnteredSowingInstructions(it)) },
                onUpdateGrowingInstructions = { viewModel.onEvent(AddEditSeedEvent.EnteredGrowingInstructions(it)) },
                onUpdateHarvestInstructions = { viewModel.onEvent(AddEditSeedEvent.EnteredHarvestInstructions(it)) }
            )

            TimelinesSection(
                state = state,
                onUpdateDaysToGermination = { viewModel.onEvent(AddEditSeedEvent.EnteredDaysToGermination(it)) },
                onUpdateDaysToHarvest = { viewModel.onEvent(AddEditSeedEvent.EnteredDaysToHarvest(it)) },
                onUpdateHarvestPeriod = { viewModel.onEvent(AddEditSeedEvent.EnteredHarvestPeriod(it)) }
            )

            CompanionsSection(
                state = state,
                onUpdateCompanionPlants = { viewModel.onEvent(AddEditSeedEvent.EnteredCompanionPlants(it)) },
                onUpdateAvoidPlants = { viewModel.onEvent(AddEditSeedEvent.EnteredAvoidPlants(it)) }
            )

            OtherDetailsSection(
                state = state,
                onUpdatePlantSpacing = { viewModel.onEvent(AddEditSeedEvent.EnteredPlantSpacing(it)) },
                onUpdateRowSpacing = { viewModel.onEvent(AddEditSeedEvent.EnteredRowSpacing(it)) },
                onUpdateTags = { viewModel.onEvent(AddEditSeedEvent.EnteredTags(it)) },
                onUpdateNotes = { viewModel.onEvent(AddEditSeedEvent.EnteredNotes(it)) }
            )

            Button(
                onClick = { viewModel.onEvent(AddEditSeedEvent.SaveSeed) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text(if (seedId == null) "Lägg till" else "Spara")
            }
        }
    }
} 

