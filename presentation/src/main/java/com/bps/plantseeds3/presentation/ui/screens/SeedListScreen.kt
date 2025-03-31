package com.bps.plantseeds3.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bps.plantseeds3.domain.model.Seed
import com.bps.plantseeds3.presentation.ui.state.SeedListUiState
import com.bps.plantseeds3.presentation.ui.viewmodel.SeedListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeedListScreen(
    onAddSeed: () -> Unit,
    onSeedClick: (Seed) -> Unit,
    viewModel: SeedListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mina frön") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddSeed
            ) {
                Icon(Icons.Default.Add, contentDescription = "Lägg till frö")
            }
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
                    Text(
                        text = uiState.error!!,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    )
                }
                uiState.seeds.isEmpty() -> {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Inga frön än",
                            style = MaterialTheme.typography.headlineMedium,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Tryck på + för att lägga till ditt första frö",
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                else -> {
                    SeedList(
                        seeds = uiState.seeds,
                        onSeedClick = onSeedClick
                    )
                }
            }
        }
    }
}

@Composable
private fun SeedList(
    seeds: List<Seed>,
    onSeedClick: (Seed) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(seeds) { seed ->
            SeedItem(
                seed = seed,
                onClick = { onSeedClick(seed) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SeedItem(
    seed: Seed,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = seed.name,
                style = MaterialTheme.typography.titleMedium
            )
            if (seed.description.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = seed.description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
} 