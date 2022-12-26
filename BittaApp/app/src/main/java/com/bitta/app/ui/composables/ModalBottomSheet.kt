package com.bitta.app.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

typealias OnOpenCallback = () -> Unit

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ModalBottomSheet(
    title: String,
    description: String,
    buttons: @Composable () -> Unit,
    viewContent: @Composable (OnOpenCallback) -> Unit,
) {
    val state = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        sheetState = state,
        sheetContent = {
            Column {
                Text(
                    title,
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(description)
                ButtonsRow(buttons)
            }
        },
        content = {
            viewContent {
                scope.launch { state.show() }
            }
        },
    )
}