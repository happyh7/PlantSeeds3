package com.bps.plantseeds3.seeds.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bps.plantseeds3.common.ui.CommonTopAppBar
import com.bps.plantseeds3.common.ui.LoadingScreen
import com.bps.plantseeds3.common.ui.ErrorScreen
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeedDetailScreen(
    seedId: Long,
    onNavigateBack: () -> Unit,
    onEditSeed: () -> Unit,
    viewModel: SeedsViewModel = hiltViewModel()
) {
    val selectedSeed by viewModel.selectedSeed.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(seedId) {
        viewModel.selectSeed(seedId)
    }

    Scaffold(
        topBar = {
            CommonTopAppBar(
                title = "Frödetaljer",
                onNavigateBack = onNavigateBack,
                actions = {
                    IconButton(
                        onClick = { viewModel.toggleFavorite(seedId) }
                    ) {
                        Icon(
                            if (selectedSeed?.isFavorite == true) Icons.Default.Favorite
                            else Icons.Default.FavoriteBorder,
                            "Favorit"
                        )
                    }
                    IconButton(
                        onClick = onEditSeed
                    ) {
                        Icon(Icons.Default.Edit, "Redigera")
                    }
                }
            )
        }
    ) { padding ->
        when {
            isLoading -> {
                LoadingScreen(modifier = Modifier.fillMaxSize())
            }
            error != null -> {
                ErrorScreen(
                    message = error!!,
                    onRetry = { viewModel.clearError() },
                    modifier = Modifier.fillMaxSize()
                )
            }
            selectedSeed == null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Inget frö hittades")
                }
            }
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = selectedSeed!!.name,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )

                    if (selectedSeed!!.species.isNotEmpty()) {
                        Text(
                            text = "Art: ${selectedSeed!!.species}",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    if (selectedSeed!!.variety.isNotEmpty()) {
                        Text(
                            text = "Sort: ${selectedSeed!!.variety}",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    if (selectedSeed!!.description.isNotEmpty()) {
                        Text(
                            text = "Beskrivning",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = selectedSeed!!.description)
                    }

                    if (selectedSeed!!.sowingInstructions.isNotEmpty()) {
                        Text(
                            text = "Såinstruktioner",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = selectedSeed!!.sowingInstructions)
                    }

                    Text(
                        text = "Växtinformation",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    if (selectedSeed!!.germinationTime > 0) {
                        Text("Groningstid: ${selectedSeed!!.germinationTime} dagar")
                    }
                    if (selectedSeed!!.harvestTime > 0) {
                        Text("Skördetid: ${selectedSeed!!.harvestTime} dagar")
                    }
                    if (selectedSeed!!.plantingDepth > 0) {
                        Text("Planteringsdjup: ${selectedSeed!!.plantingDepth} cm")
                    }
                    if (selectedSeed!!.spacing > 0) {
                        Text("Mellanrum: ${selectedSeed!!.spacing} cm")
                    }

                    Text(
                        text = "Växtbehov",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    if (selectedSeed!!.lightNeeds.isNotEmpty()) {
                        Text("Ljusbehov: ${selectedSeed!!.lightNeeds}")
                    }
                    if (selectedSeed!!.waterNeeds.isNotEmpty()) {
                        Text("Vattenbehov: ${selectedSeed!!.waterNeeds}")
                    }
                    if (selectedSeed!!.temperatureNeeds.isNotEmpty()) {
                        Text("Temperaturbehov: ${selectedSeed!!.temperatureNeeds}")
                    }

                    Text(
                        text = "Lager",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Text("Antal: ${selectedSeed!!.quantity} ${selectedSeed!!.unit}")
                }
            }
        }
    }
} 