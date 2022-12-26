package com.bitta.app.ui.composables

import android.content.res.Resources
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.whenResumed
import com.bitta.app.utils.SnackbarInfo
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.isActive

@Composable
fun RegisterSnackbarEvents(
    snackbarChannel: Channel<SnackbarInfo>,
    snackbarHostState: SnackbarHostState,
) {
    val resources = LocalContext.current.resources
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner, snackbarHostState, resources) {
        while (isActive) {
            snackbarChannel.showNextSnackbar(
                lifecycleOwner, snackbarHostState, resources
            )
        }
    }
}

private suspend fun Channel<SnackbarInfo>.showNextSnackbar(
    lifecycleOwner: LifecycleOwner,
    snackbarHostState: SnackbarHostState,
    resources: Resources,
) {
    val snackbar = lifecycleOwner.lifecycle.whenResumed { receive() }
    val snackbarResult = snackbarHostState.showSnackbar(
        resources.getString(snackbar.message),
        snackbar.actionLabel?.let(resources::getString),
        duration = if (snackbar.actionLabel == null) SnackbarDuration.Short else SnackbarDuration.Long,
    )
    if (snackbarResult == SnackbarResult.ActionPerformed) {
        snackbar.onAction?.invoke()
    }
}