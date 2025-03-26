package com.bps.plantseeds3.presentation.plants.add_edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bps.plantseeds3.data.local.entity.PlantCategory
import com.bps.plantseeds3.data.local.entity.PlantStatus
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditPlantScreen(
    navController: NavController,
    viewModel: AddEditPlantViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var showCategoryDialog by remember { mutableStateOf(false) }
    var showStatusDialog by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(PlantCategory.SEED) }
    var selectedStatus by remember { mutableStateOf(PlantStatus.SEED) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.isSaved) {
        if (state.isSaved) {
            navController.navigateUp()
        }
    }

    LaunchedEffect(state.error) {
        state.error?.let { error ->
            // Visa felmeddelande med Snackbar
            snackbarHostState.showSnackbar(
                message = error,
                duration = SnackbarDuration.Short
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (state.selectedGardenId == null) "Lägg till växt" else "Redigera växt") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Gå tillbaka")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.savePlant() }) {
                        Icon(Icons.Default.Save, contentDescription = "Spara växt")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = state.name,
                onValueChange = { viewModel.onNameChange(it) },
                label = { Text("Namn *") },
                modifier = Modifier.fillMaxWidth(),
                isError = state.error != null && state.name.isBlank()
            )

            OutlinedTextField(
                value = state.scientificName,
                onValueChange = { viewModel.onScientificNameChange(it) },
                label = { Text("Vetenskapligt namn") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.species,
                onValueChange = { viewModel.onSpeciesChange(it) },
                label = { Text("Art") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.variety,
                onValueChange = { viewModel.onVarietyChange(it) },
                label = { Text("Sort") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedButton(
                onClick = { showCategoryDialog = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Kategori: ${state.category}")
            }

            OutlinedButton(
                onClick = { showStatusDialog = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Status: ${state.status}")
            }

            OutlinedTextField(
                value = state.description,
                onValueChange = { viewModel.onDescriptionChange(it) },
                label = { Text("Beskrivning") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            OutlinedTextField(
                value = state.sowingDepth ?: "",
                onValueChange = { viewModel.onSowingDepthChange(it) },
                label = { Text("Sådjup") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.spacing ?: "",
                onValueChange = { viewModel.onSpacingChange(it) },
                label = { Text("Avstånd mellan plantor") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.daysToGermination ?: "",
                onValueChange = { viewModel.onDaysToGerminationChange(it) },
                label = { Text("Dagar till grodd") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.daysToMaturity ?: "",
                onValueChange = { viewModel.onDaysToMaturityChange(it) },
                label = { Text("Dagar till mognad") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.sunRequirement ?: "",
                onValueChange = { viewModel.onSunRequirementChange(it) },
                label = { Text("Solbehov") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.waterRequirement ?: "",
                onValueChange = { viewModel.onWaterRequirementChange(it) },
                label = { Text("Vattenbehov") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.soilRequirement ?: "",
                onValueChange = { viewModel.onSoilRequirementChange(it) },
                label = { Text("Jordbehov") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.soilPh ?: "",
                onValueChange = { viewModel.onSoilPhChange(it) },
                label = { Text("Jord pH") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.hardiness ?: "",
                onValueChange = { viewModel.onHardinessChange(it) },
                label = { Text("Hårdhet") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.sowingInstructions ?: "",
                onValueChange = { viewModel.onSowingInstructionsChange(it) },
                label = { Text("Såinstruktioner") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            OutlinedTextField(
                value = state.growingInstructions ?: "",
                onValueChange = { viewModel.onGrowingInstructionsChange(it) },
                label = { Text("Odlingsinstruktioner") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            OutlinedTextField(
                value = state.harvestInstructions ?: "",
                onValueChange = { viewModel.onHarvestInstructionsChange(it) },
                label = { Text("Skördeinstruktioner") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            OutlinedTextField(
                value = state.storageInstructions ?: "",
                onValueChange = { viewModel.onStorageInstructionsChange(it) },
                label = { Text("Förvaringsinstruktioner") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            OutlinedTextField(
                value = state.companionPlants ?: "",
                onValueChange = { viewModel.onCompanionPlantsChange(it) },
                label = { Text("Följeväxter") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.avoidPlants ?: "",
                onValueChange = { viewModel.onAvoidPlantsChange(it) },
                label = { Text("Undvik dessa växter") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.height ?: "",
                onValueChange = { viewModel.onHeightChange(it) },
                label = { Text("Höjd") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.spread ?: "",
                onValueChange = { viewModel.onSpreadChange(it) },
                label = { Text("Bredd") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.yield ?: "",
                onValueChange = { viewModel.onYieldChange(it) },
                label = { Text("Skörd") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.culinaryUses ?: "",
                onValueChange = { viewModel.onCulinaryUsesChange(it) },
                label = { Text("Kulinariska användningar") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.medicinalUses ?: "",
                onValueChange = { viewModel.onMedicinalUsesChange(it) },
                label = { Text("Medicinska användningar") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.tags ?: "",
                onValueChange = { viewModel.onTagsChange(it) },
                label = { Text("Taggar") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.notes ?: "",
                onValueChange = { viewModel.onNotesChange(it) },
                label = { Text("Anteckningar") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
        }
    }

    if (showCategoryDialog) {
        AlertDialog(
            onDismissRequest = { showCategoryDialog = false },
            title = { Text("Välj kategori") },
            text = {
                Column {
                    PlantCategory.values().forEach { category ->
                        TextButton(
                            onClick = {
                                selectedCategory = category
                                viewModel.onCategoryChange(category.name)
                                showCategoryDialog = false
                            }
                        ) {
                            Text(text = when (category) {
                                PlantCategory.SEED -> "Frö"
                                PlantCategory.PLANT -> "Planta"
                                PlantCategory.BULB -> "Lök"
                                PlantCategory.CUTTING -> "Stickling"
                                PlantCategory.SEEDLING -> "Groddplanta"
                                PlantCategory.TREE -> "Träd"
                                PlantCategory.SHRUB -> "Buske"
                                PlantCategory.HERB -> "Ört"
                                PlantCategory.VEGETABLE -> "Grönsak"
                                PlantCategory.FRUIT -> "Frukt"
                                PlantCategory.FLOWER -> "Blomma"
                                PlantCategory.SUCCULENT -> "Suckulent"
                                PlantCategory.CACTUS -> "Kaktus"
                                PlantCategory.FERN -> "Ormbunke"
                                PlantCategory.GRASS -> "Gräs"
                                PlantCategory.CLIMBER -> "Klätterväxt"
                                PlantCategory.GROUND_COVER -> "Marktäckare"
                                PlantCategory.AQUATIC -> "Vattenväxt"
                                PlantCategory.TROPICAL -> "Tropisk växt"
                                PlantCategory.MEDITERRANEAN -> "Medelhavsväxt"
                                PlantCategory.ALPINE -> "Alpinväxt"
                                PlantCategory.BONSAI -> "Bonsai"
                                PlantCategory.INDOOR -> "Inomhusväxt"
                                PlantCategory.OUTDOOR -> "Utomhusväxt"
                                PlantCategory.ANNUAL -> "Ettårig"
                                PlantCategory.BIENNIAL -> "Tvåårig"
                                PlantCategory.PERENNIAL -> "Flerårig"
                                PlantCategory.EVERGREEN -> "Städsegrön"
                                PlantCategory.DECIDUOUS -> "Lövfällande"
                                PlantCategory.CONIFER -> "Barrväxt"
                                PlantCategory.BAMBOO -> "Bambu"
                                PlantCategory.PALM -> "Palm"
                                PlantCategory.CYCAD -> "Kottepalm"
                                PlantCategory.GINGER -> "Ingefära"
                                PlantCategory.BROMELIAD -> "Bromeliad"
                                PlantCategory.ORCHID -> "Orkidé"
                                PlantCategory.CARNIVOROUS -> "Köttätande växt"
                                PlantCategory.AIR_PLANT -> "Luftväxt"
                                PlantCategory.MOSS -> "Mossa"
                                PlantCategory.LICHEN -> "Lav"
                                PlantCategory.FUNGUS -> "Svamp"
                                PlantCategory.ALGAE -> "Alg"
                                PlantCategory.OTHER -> "Övrigt"
                            })
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showCategoryDialog = false }) {
                    Text("Avbryt")
                }
            }
        )
    }

    if (showStatusDialog) {
        AlertDialog(
            onDismissRequest = { showStatusDialog = false },
            title = { Text("Välj status") },
            text = {
                Column {
                    PlantStatus.values().forEach { status ->
                        TextButton(
                            onClick = {
                                selectedStatus = status
                                viewModel.onStatusChange(status)
                                showStatusDialog = false
                            }
                        ) {
                            Text(text = when (status) {
                                PlantStatus.SEED -> "Frö"
                                PlantStatus.SEEDLING -> "Groddplanta"
                                PlantStatus.GROWING -> "Växande"
                                PlantStatus.FLOWERING -> "Blommar"
                                PlantStatus.FRUITING -> "Bär frukt"
                                PlantStatus.HARVESTED -> "Skördad"
                                PlantStatus.DEAD -> "Död"
                            })
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showStatusDialog = false }) {
                    Text("Avbryt")
                }
            }
        )
    }
} 