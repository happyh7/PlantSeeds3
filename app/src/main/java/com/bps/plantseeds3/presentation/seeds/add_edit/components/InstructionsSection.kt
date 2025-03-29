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
fun InstructionsSection(
    state: AddEditSeedState,
    onUpdateSowingInstructions: (String) -> Unit,
    onUpdateGrowingInstructions: (String) -> Unit,
    onUpdateHarvestingInstructions: (String) -> Unit
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
                text = stringResource(R.string.instructions),
                style = MaterialTheme.typography.titleLarge
            )
            
            CapitalizedTextField(
                value = state.sowingInstructions,
                onValueChange = onUpdateSowingInstructions,
                label = stringResource(R.string.sowing_instructions)
            )
            
            CapitalizedTextField(
                value = state.growingInstructions,
                onValueChange = onUpdateGrowingInstructions,
                label = stringResource(R.string.growing_instructions)
            )
            
            CapitalizedTextField(
                value = state.harvestingInstructions,
                onValueChange = onUpdateHarvestingInstructions,
                label = stringResource(R.string.harvest_instructions)
            )
        }
    }
} 