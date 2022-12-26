package com.bitta.app.ui.composables

import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.bitta.app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeletableTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    isError: MutableState<Boolean> = mutableStateOf(false),
    supportingText: @Composable (() -> Unit)? = null,
    label: @Composable () -> Unit,
    leadingIcon: @Composable () -> Unit,
    imeAction: ImeAction = ImeAction.Default,
    onImeSend: (KeyboardActionScope.() -> Unit)? = null,
) {
    val onValueChangeCallback: (String) -> Unit = {
        isError.value = false
        onValueChange(it)
    }

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChangeCallback,
        isError = isError.value,
        supportingText = supportingText,
        label = label,
        leadingIcon = leadingIcon,
        trailingIcon = {
            if (value.isNotBlank()) {
                IconButton(onClick = { onValueChangeCallback("") }) {
                    Icon(
                        AppIcons.Close,
                        stringResource(R.string.search_bar_clear_label),
                    )
                }
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = imeAction),
        keyboardActions = KeyboardActions(
            onSend = onImeSend ?: {},
        )
    )
}