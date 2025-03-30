package com.bps.plantseeds3.seeds.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bps.plantseeds3.common.ui.CommonTopAppBar
import com.bps.plantseeds3.common.ui.LoadingScreen
import com.bps.plantseeds3.common.ui.ErrorScreen
import com.bps.plantseeds3.seeds.model.Seed
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeedsScreen(
    onNavigateToAddSeed: () -> Unit,
    onNavigateToSeedDetail: (Long) -> Unit,
    viewModel: SeedsViewModel = hiltViewModel()
) {
    val seeds by viewModel.seeds.collectAsState()
    val favoriteSeeds by viewModel.favoriteSeeds.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    var showFavorites by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CommonTopAppBar(
                title = "Fröbank",
                onNavigateBack = { /* Hantera tillbaka-navigering */ }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddSeed
            ) {
                Icon(Icons.Default.Add, "Lägg till frö")
            }
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
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { viewModel.setSearchQuery(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        placeholder = { Text("Sök frön...") },
                        leadingIcon = { Icon(Icons.Default.Search, "Sök") },
                        singleLine = true
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            selected = !showFavorites,
                            onClick = { showFavorites = false },
                            label = { Text("Alla frön") }
                        )
                        FilterChip(
                            selected = showFavorites,
                            onClick = { showFavorites = true },
                            label = { Text("Favoriter") }
                        )
                    }

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(
                            if (showFavorites) favoriteSeeds else seeds
                        ) { seed ->
                            SeedCard(
                                seed = seed,
                                onSeedClick = { onNavigateToSeedDetail(seed.id) },
                                onToggleFavorite = { viewModel.toggleFavorite(seed.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SeedCard(
    seed: Seed,
    onSeedClick: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    Card(
        onClick = onSeedClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = seed.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    if (seed.species.isNotEmpty()) {
                        Text(
                            text = seed.species,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    if (seed.variety.isNotEmpty()) {
                        Text(
                            text = seed.variety,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                IconButton(
                    onClick = onToggleFavorite
                ) {
                    Icon(
                        if (seed.isFavorite) Icons.Default.Favorite
                        else Icons.Default.FavoriteBorder,
                        "Favorit"
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Antal: ${seed.quantity} ${seed.unit}",
                    style = MaterialTheme.typography.bodyMedium
                )
                if (seed.germinationTime > 0) {
                    Text(
                        text = "Groningstid: ${seed.germinationTime} dagar",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
} 