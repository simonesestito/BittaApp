package com.bitta.app.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
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
        sheetShape = RoundedCornerShape(dimensionResource(R.dimen.bottom_sheet_corner_radius)),
        sheetBackgroundColor = MaterialTheme.colorScheme.surface,
        sheetContent = {
            Column(
                modifier = Modifier.padding(dimensionResource(R.dimen.app_large_spacing)),
            ) {
                // Since ModalBottomSheetLayout is from Material2,
                // text color needs to be propagated this way.
                // Background color is set as a parameter in the Composable.
                CompositionLocalProvider(
                    LocalContentColor provides MaterialTheme.colorScheme.onSurface
                ) {
                    Text(
                        title,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge,
                        color = LocalContentColor.current,
                    )
                    Spacer(Modifier.height(dimensionResource(R.dimen.app_large_spacing)))
                    Text(description, color = LocalContentColor.current)
                    ButtonsRow {
                        buttons {
                            scope.launch { state.hide() }
                        }
                    }
                    Spacer(Modifier.height(dimensionResource(R.dimen.app_large_spacing)))
                }
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