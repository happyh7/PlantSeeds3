package com.bps.plantseeds3.presentation.seeds

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bps.plantseeds3.R
import com.bps.plantseeds3.domain.model.Seed
import com.bps.plantseeds3.domain.model.PlantCategory
import com.bps.plantseeds3.presentation.components.CapitalizedTextField
import com.bps.plantseeds3.presentation.navigation.Screen
import com.bps.plantseeds3.presentation.seeds.components.SeedItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeedBankScreen(
    onNavigateToAddEditSeed: (String?) -> Unit,
    viewModel: SeedBankViewModel = hiltViewModel()
) {
    val TAG = "SeedBankScreen"
    val state by viewModel.state.collectAsState()
    var showSortMenu by remember { mutableStateOf(false) }
    var showCategoryMenu by remember { mutableStateOf(false) }

    // Hämta unika kategorier från alla frön
    val categories = remember(state.seeds) {
        Log.d(TAG, "Uppdaterar kategorilista med ${state.seeds.size} frön")
        state.seeds.mapNotNull { it.category }.distinct().sorted()
    }

    LaunchedEffect(Unit) {
        Log.d(TAG, "SeedBankScreen initieras")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Fröbank") },
                actions = {
                    IconButton(onClick = { /* TODO: Implementera sökfunktion */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Sök")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { 
                    Log.d(TAG, "Navigerar till lägg till frö")
                    onNavigateToAddEditSeed("new") 
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Lägg till frö")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (state.isLoading) {
                Log.d(TAG, "Visar laddningsindikator")
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (state.error != null) {
                Log.e(TAG, "Visar felmeddelande: ${state.error}")
                Text(
                    text = state.error ?: "",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            } else if (state.filteredSeeds.isEmpty()) {
                Log.d(TAG, "Inga frön att visa")
                Text(
                    text = if (state.searchQuery.isBlank()) 
                        "Inga frön tillagda än" 
                    else 
                        "Inga frön matchar sökningen",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            } else {
                Log.d(TAG, "Visar ${state.filteredSeeds.size} frön")
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.filteredSeeds) { seed ->
                        SeedItem(
                            seed = seed,
                            onEditClick = { 
                                Log.d(TAG, "Redigerar frö: ${seed.name}")
                                onNavigateToAddEditSeed(seed.id) 
                            },
                            onDeleteClick = { 
                                Log.d(TAG, "Tar bort frö: ${seed.name}")
                                viewModel.onEvent(SeedBankEvent.DeleteSeed(seed)) 
                            }
                        )
                    }
                }
            }
        }
    }

    // Sorteringsmeny
    DropdownMenu(
        expanded = showSortMenu,
        onDismissRequest = { showSortMenu = false }
    ) {
        DropdownMenuItem(
            text = { Text("Namn (A-Ö)") },
            onClick = {
                Log.d(TAG, "Sorterar efter namn (A-Ö)")
                viewModel.onEvent(SeedBankEvent.SortSeeds(SortOrder.NAME_ASC))
                showSortMenu = false
            }
        )
        DropdownMenuItem(
            text = { Text("Namn (Ö-A)") },
            onClick = {
                Log.d(TAG, "Sorterar efter namn (Ö-A)")
                viewModel.onEvent(SeedBankEvent.SortSeeds(SortOrder.NAME_DESC))
                showSortMenu = false
            }
        )
        DropdownMenuItem(
            text = { Text("Art (A-Ö)") },
            onClick = {
                Log.d(TAG, "Sorterar efter art (A-Ö)")
                viewModel.onEvent(SeedBankEvent.SortSeeds(SortOrder.SPECIES_ASC))
                showSortMenu = false
            }
        )
        DropdownMenuItem(
            text = { Text("Art (Ö-A)") },
            onClick = {
                Log.d(TAG, "Sorterar efter art (Ö-A)")
                viewModel.onEvent(SeedBankEvent.SortSeeds(SortOrder.SPECIES_DESC))
                showSortMenu = false
            }
        )
        DropdownMenuItem(
            text = { Text("Kategori (A-Ö)") },
            onClick = {
                Log.d(TAG, "Sorterar efter kategori (A-Ö)")
                viewModel.onEvent(SeedBankEvent.SortSeeds(SortOrder.CATEGORY_ASC))
                showSortMenu = false
            }
        )
        DropdownMenuItem(
            text = { Text("Kategori (Ö-A)") },
            onClick = {
                Log.d(TAG, "Sorterar efter kategori (Ö-A)")
                viewModel.onEvent(SeedBankEvent.SortSeeds(SortOrder.CATEGORY_DESC))
                showSortMenu = false
            }
        )
    }

    // Kategorimeny
    DropdownMenu(
        expanded = showCategoryMenu,
        onDismissRequest = { showCategoryMenu = false }
    ) {
        DropdownMenuItem(
            text = { Text("Alla kategorier") },
            onClick = {
                Log.d(TAG, "Visar alla kategorier")
                viewModel.onEvent(SeedBankEvent.FilterByCategory(null))
                showCategoryMenu = false
            }
        )
        categories.forEach { category ->
            DropdownMenuItem(
                text = { Text(category.displayName) },
                onClick = {
                    Log.d(TAG, "Filtrerar på kategori: ${category.displayName}")
                    viewModel.onEvent(SeedBankEvent.FilterByCategory(category.displayName))
                    showCategoryMenu = false
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeedItem(
    seed: Seed,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onEditClick
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
                Text(
                    text = seed.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row {
                    IconButton(onClick = onEditClick) {
                        Icon(Icons.Default.Edit, contentDescription = "Redigera")
                    }
                    IconButton(onClick = onDeleteClick) {
                        Icon(Icons.Default.Delete, contentDescription = "Ta bort")
                    }
                }
            }
            Text(
                text = seed.scientificName ?: "",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = seed.category.displayName,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
} 