package com.bitta.app.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.bitta.app.R
import com.bitta.app.model.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditableDropdownMenu(
    listItems: List<Product>,
    queryState: MutableState<String>,
    errorState: MutableState<Boolean>,
    onSend: KeyboardActionScope.() -> Unit,
) {
    var query by queryState
    var error by errorState
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = Modifier
            .padding(
                vertical = dimensionResource(R.dimen.input_vertical_padding),
                horizontal = dimensionResource(R.dimen.input_horizontal_padding),
            )
            .fillMaxWidth(),
        expanded = expanded,
        onExpandedChange = {},
    ) {
        DeletableTextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            value = query,
            onValueChange = { query = it; expanded = true; error = false },
            isError = errorState,
            supportingText = { if (error) Text(stringResource(R.string.products_search_error)) },
            label = { Text(stringResource(R.string.products_search_bar_label)) },
            leadingIcon = {
                Icon(
                    AppIcons.Search, stringResource(R.string.products_search_bar_label)
                )
            },
            imeAction = ImeAction.Send,
            onImeSend = onSend,
        )

        // filter options based on text field value
        val filteringOptions = listItems.filter { it.name.contains(query, ignoreCase = true) }

        if (filteringOptions.isNotEmpty()) {
            ExposedDropdownMenu(
                modifier = Modifier.fillMaxWidth(),
                expanded = expanded,
                onDismissRequest = { },
            ) {
                filteringOptions.forEach { selectionOption ->
                    DropdownMenuItem(
                        onClick = {
                            query = selectionOption.name
                            expanded = false
                        },
                        text = {
                            Text(selectionOption.name)
                        },
                    )
                }
            }
        }
    }
}