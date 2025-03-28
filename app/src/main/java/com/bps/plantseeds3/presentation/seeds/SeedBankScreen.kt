package com.bps.plantseeds3.presentation.seeds

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bps.plantseeds3.R
import com.bps.plantseeds3.presentation.components.CapitalizedTextField
import com.bps.plantseeds3.presentation.navigation.Screen
import com.bps.plantseeds3.presentation.seeds.components.SeedItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeedBankScreen(
    navController: NavController,
    viewModel: SeedBankViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var showSortMenu by remember { mutableStateOf(false) }
    var showCategoryMenu by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Fröbank") },
                actions = {
                    IconButton(
                        onClick = { showSortMenu = true }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Sort,
                            contentDescription = "Sortera"
                        )
                    }
                    IconButton(
                        onClick = { navController.navigate(Screen.AddEditSeed.route) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Lägg till frö"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { 
                    searchQuery = it
                    viewModel.onEvent(SeedBankEvent.SearchSeeds(it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Sök frön...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
            )

            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.filteredSeeds) { seed ->
                        SeedItem(
                            seed = seed,
                            onDeleteClick = {
                                viewModel.onEvent(SeedBankEvent.DeleteSeed(seed))
                            },
                            onEditClick = {
                                navController.navigate("add_edit_seed?seedId=${seed.id}")
                            }
                        )
                    }
                }

                if (state.filteredSeeds.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (searchQuery.isBlank()) 
                                "Inga frön tillagda än" 
                            else 
                                "Inga frön matchar sökningen",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }

        DropdownMenu(
            expanded = showSortMenu,
            onDismissRequest = { showSortMenu = false }
        ) {
            DropdownMenuItem(
                text = { Text("Namn (A-Ö)") },
                onClick = {
                    viewModel.onEvent(SeedBankEvent.SortSeeds(SortOrder.NAME_ASC))
                    showSortMenu = false
                }
            )
            DropdownMenuItem(
                text = { Text("Namn (Ö-A)") },
                onClick = {
                    viewModel.onEvent(SeedBankEvent.SortSeeds(SortOrder.NAME_DESC))
                    showSortMenu = false
                }
            )
            DropdownMenuItem(
                text = { Text("Art (A-Ö)") },
                onClick = {
                    viewModel.onEvent(SeedBankEvent.SortSeeds(SortOrder.SPECIES_ASC))
                    showSortMenu = false
                }
            )
            DropdownMenuItem(
                text = { Text("Art (Ö-A)") },
                onClick = {
                    viewModel.onEvent(SeedBankEvent.SortSeeds(SortOrder.SPECIES_DESC))
                    showSortMenu = false
                }
            )
            DropdownMenuItem(
                text = { Text("Kategori (A-Ö)") },
                onClick = {
                    viewModel.onEvent(SeedBankEvent.SortSeeds(SortOrder.CATEGORY_ASC))
                    showSortMenu = false
                }
            )
            DropdownMenuItem(
                text = { Text("Kategori (Ö-A)") },
                onClick = {
                    viewModel.onEvent(SeedBankEvent.SortSeeds(SortOrder.CATEGORY_DESC))
                    showSortMenu = false
                }
            )
        }
    }
} 