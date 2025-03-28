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
fun OtherDetailsSection(
    state: AddEditSeedState,
    onUpdatePlantSpacing: (String) -> Unit,
    onUpdateRowSpacing: (String) -> Unit,
    onUpdateTags: (String) -> Unit,
    onUpdateNotes: (String) -> Unit
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
                text = stringResource(R.string.other_details),
                style = MaterialTheme.typography.titleLarge
            )
            
            CapitalizedTextField(
                value = state.plantSpacing,
                onValueChange = onUpdatePlantSpacing,
                label = stringResource(R.string.plant_spacing)
            )
            
            CapitalizedTextField(
                value = state.rowSpacing,
                onValueChange = onUpdateRowSpacing,
                label = stringResource(R.string.row_spacing)
            )
            
            CapitalizedTextField(
                value = state.tags,
                onValueChange = onUpdateTags,
                label = stringResource(R.string.tags)
            )
            
            CapitalizedTextField(
                value = state.notes,
                onValueChange = onUpdateNotes,
                label = stringResource(R.string.notes)
            )
        }
    }
} 