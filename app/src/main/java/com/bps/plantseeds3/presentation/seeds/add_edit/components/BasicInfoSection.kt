package com.bps.plantseeds3.presentation.seeds.add_edit.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bps.plantseeds3.R
import com.bps.plantseeds3.presentation.components.CapitalizedTextField
import com.bps.plantseeds3.presentation.seeds.add_edit.AddEditSeedState

@Composable
fun BasicInfoSection(
    state: AddEditSeedState,
    onUpdateName: (String) -> Unit,
    onUpdateScientificName: (String) -> Unit,
    onUpdateSpecies: (String) -> Unit,
    onUpdateVariety: (String) -> Unit,
    onUpdateCategory: (String) -> Unit,
    onUpdateDescription: (String) -> Unit,
    errorMessage: String?
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(R.string.basic_info),
                style = MaterialTheme.typography.titleLarge
            )
            
            CapitalizedTextField(
                value = state.name,
                onValueChange = onUpdateName,
                label = stringResource(R.string.name),
                isError = errorMessage != null
            )
            
            if (errorMessage != null) {
                Text(
                    text = errorMessage,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
            
            CapitalizedTextField(
                value = state.scientificName,
                onValueChange = onUpdateScientificName,
                label = stringResource(R.string.scientific_name)
            )
            
            CapitalizedTextField(
                value = state.species,
                onValueChange = onUpdateSpecies,
                label = stringResource(R.string.species)
            )
            
            CapitalizedTextField(
                value = state.variety,
                onValueChange = onUpdateVariety,
                label = stringResource(R.string.variety)
            )
            
            CapitalizedTextField(
                value = state.category,
                onValueChange = onUpdateCategory,
                label = stringResource(R.string.category)
            )
            
            CapitalizedTextField(
                value = state.description,
                onValueChange = onUpdateDescription,
                label = stringResource(R.string.description)
            )
        }
    }
} 