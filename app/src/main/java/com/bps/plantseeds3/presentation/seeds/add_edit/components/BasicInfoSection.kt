package com.bps.plantseeds3.presentation.seeds.add_edit.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bps.plantseeds3.domain.model.PlantCategory
import com.bps.plantseeds3.presentation.seeds.add_edit.AddEditSeedEvent
import com.bps.plantseeds3.presentation.seeds.add_edit.AddEditSeedState

@Composable
fun BasicInfoSection(
    state: AddEditSeedState,
    onEvent: (AddEditSeedEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Grundläggande information",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = state.name,
            onValueChange = { onEvent(AddEditSeedEvent.OnNameChange(it)) },
            label = { Text("Namn") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = state.scientificName,
            onValueChange = { onEvent(AddEditSeedEvent.OnScientificNameChange(it)) },
            label = { Text("Vetenskapligt namn") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = state.species,
            onValueChange = { onEvent(AddEditSeedEvent.OnSpeciesChange(it)) },
            label = { Text("Art") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = state.variety,
            onValueChange = { onEvent(AddEditSeedEvent.OnVarietyChange(it)) },
            label = { Text("Sort") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        Text(
            text = "Kategori",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        PlantCategory.values().forEach { category ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                RadioButton(
                    selected = state.category == category,
                    onClick = { onEvent(AddEditSeedEvent.OnCategoryChange(category)) }
                )
                Text(
                    text = category.displayName,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        OutlinedTextField(
            value = state.description,
            onValueChange = { onEvent(AddEditSeedEvent.OnDescriptionChange(it)) },
            label = { Text("Beskrivning") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            minLines = 3
        )
    }
} 