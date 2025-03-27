package com.bps.plantseeds3.presentation.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign

@Composable
fun CapitalizedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    enabled: Boolean = true,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next
) {
    val capitalizeText: (String) -> String = remember {
        { text ->
            if (text.isEmpty()) text
            else text.split(". ").joinToString(". ") { sentence ->
                if (sentence.isEmpty()) sentence
                else sentence.replaceFirstChar { it.uppercase() }
            }
        }
    }

    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            onValueChange(capitalizeText(newValue))
        },
        label = { Text(label) },
        modifier = modifier,
        enabled = enabled,
        isError = isError,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction,
            capitalization = androidx.compose.ui.text.input.KeyboardCapitalization.Sentences
        )
    )
} 