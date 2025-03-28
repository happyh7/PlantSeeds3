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
fun CompanionsSection(
    state: AddEditSeedState,
    onUpdateCompanionPlants: (String) -> Unit,
    onUpdateAvoidPlants: (String) -> Unit
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
                text = stringResource(R.string.companions),
                style = MaterialTheme.typography.titleLarge
            )
            
            CapitalizedTextField(
                value = state.companionPlants,
                onValueChange = onUpdateCompanionPlants,
                label = stringResource(R.string.companion_plants)
            )
            
            CapitalizedTextField(
                value = state.avoidPlants,
                onValueChange = onUpdateAvoidPlants,
                label = stringResource(R.string.avoid_plants)
            )
        }
    }
} 