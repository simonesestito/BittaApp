package com.bitta.app.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
) {
    var query by queryState
    val error by errorState
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = Modifier
            .padding(
                vertical = dimensionResource(R.dimen.app_medium_spacing),
                horizontal = dimensionResource(R.dimen.app_large_spacing),
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
            onValueChange = { query = it; expanded = true },
            isError = error,
            supportingText = { if (error) Text(stringResource(R.string.products_search_error)) },
            label = { Text(stringResource(R.string.products_search_bar_label)) },
            leadingIcon = {
                Icon(
                    AppIcons.Search, stringResource(R.string.products_search_bar_label)
                )
            },
            imeAction = ImeAction.Search,
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