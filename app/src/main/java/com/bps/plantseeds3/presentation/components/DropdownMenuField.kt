package com.bps.plantseeds3.presentation.components

import android.util.Log
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun DropdownMenuField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    options: List<String>,
    modifier: Modifier = Modifier,
    isRequired: Boolean = false
) {
    val TAG = "DropdownMenuField"
    var expanded by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    LaunchedEffect(isPressed) {
        if (isPressed) {
            Log.d(TAG, "Tryckte på dropdown: $label")
            expanded = true
        }
    }

    Log.d(TAG, "Visar dropdown: $label")
    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = { },
            readOnly = true,
            label = { 
                Text(
                    text = if (isRequired) "$label *" else label,
                    textAlign = TextAlign.Start
                )
            },
            trailingIcon = {
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Visa alternativ")
            },
            modifier = Modifier.fillMaxWidth(),
            interactionSource = interactionSource,
            isError = isRequired && value.isBlank()
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { 
                Log.d(TAG, "Stänger dropdown: $label")
                expanded = false 
            }
        ) {
            Log.d(TAG, "Visar ${options.size} alternativ i dropdown: $label")
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        Log.d(TAG, "Valde alternativ: $option")
                        onValueChange(option)
                        expanded = false
                    }
                )
            }
        }
    }
} 