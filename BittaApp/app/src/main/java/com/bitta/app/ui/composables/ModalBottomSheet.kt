package com.bitta.app.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bitta.app.R
import kotlinx.coroutines.launch

typealias OnOpenCallback = () -> Unit

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ModalBottomSheet(
    state: ModalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden),
    title: String,
    description: String,
    buttons: @Composable (cancel: OnOpenCallback) -> Unit,
    viewContent: @Composable (open: OnOpenCallback) -> Unit,
) {
    val scope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        sheetState = state,
        sheetShape = RoundedCornerShape(8.dp),
        sheetContent = {
            Column(
                modifier = Modifier.padding(dimensionResource(R.dimen.app_large_spacing)),
            ) {
                Text(
                    title,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.height(dimensionResource(R.dimen.app_large_spacing)))
                Text(description)
                ButtonsRow {
                    buttons {
                        scope.launch { state.hide() }
                    }
                }
                Spacer(Modifier.height(dimensionResource(R.dimen.app_large_spacing)))
            }
        },
        content = {
            viewContent {
                scope.launch { state.show() }
            }
        },
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ModalBottomSheet(
    state: ModalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden),
    title: String,
    description: String,
    cancelButton: @Composable (cancel: OnOpenCallback) -> Unit,
    confirmButton: @Composable (cancel: OnOpenCallback) -> Unit,
    viewContent: @Composable (open: OnOpenCallback) -> Unit,
) {
    ModalBottomSheet(state, title, description, buttons = { onClose ->
        cancelButton(onClose)
        Spacer(Modifier.width(dimensionResource(R.dimen.button_spacing)))
        confirmButton(onClose)
    }, viewContent)
}