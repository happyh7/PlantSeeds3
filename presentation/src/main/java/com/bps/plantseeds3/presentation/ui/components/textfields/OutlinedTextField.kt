package com.bps.plantseeds3.presentation.ui.components.textfields

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.bps.plantseeds3.presentation.ui.components.modifiers.noRippleClickable

@Composable
fun OutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    isError: Boolean = false,
    supportingText: String? = null,
    trailingIcon: ImageVector? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    textColor: Color = MaterialTheme.colorScheme.onSurface
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = label?.let { { Text(text = it) } },
        placeholder = placeholder?.let { { Text(text = it) } },
        enabled = enabled,
        readOnly = readOnly,
        isError = isError,
        supportingText = supportingText?.let { { Text(text = it) } },
        trailingIcon = trailingIcon?.let {
            {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    modifier = if (onTrailingIconClick != null) {
                        Modifier.noRippleClickable(onClick = onTrailingIconClick)
                    } else {
                        Modifier
                    }
                )
            }
        },
        textStyle = MaterialTheme.typography.bodyLarge.copy(color = textColor),
        colors = MaterialTheme.colorScheme.run {
            OutlinedTextFieldDefaults.colors(
                focusedTextColor = textColor,
                unfocusedTextColor = textColor,
                disabledTextColor = onSurfaceVariant,
                errorTextColor = error,
                focusedBorderColor = primary,
                unfocusedBorderColor = outline,
                disabledBorderColor = outline,
                errorBorderColor = error,
                focusedLabelColor = primary,
                unfocusedLabelColor = onSurfaceVariant,
                disabledLabelColor = onSurfaceVariant,
                errorLabelColor = error,
                focusedPlaceholderColor = onSurfaceVariant,
                unfocusedPlaceholderColor = onSurfaceVariant,
                disabledPlaceholderColor = onSurfaceVariant,
                errorPlaceholderColor = error,
                focusedSupportingTextColor = onSurfaceVariant,
                unfocusedSupportingTextColor = onSurfaceVariant,
                disabledSupportingTextColor = onSurfaceVariant,
                errorSupportingTextColor = error
            )
        }
    )
} 