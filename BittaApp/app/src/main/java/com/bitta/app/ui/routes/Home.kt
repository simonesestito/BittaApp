package com.bitta.app.ui.routes

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.whenResumed
import com.bitta.app.R
import com.bitta.app.SnackbarInfo
import com.bitta.app.ui.composables.AppSkeleton
import com.bitta.app.ui.composables.DispenserCard
import com.bitta.app.ui.composables.LoadingIndicator
import com.bitta.app.viewmodel.DispenserViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.isActive

@Composable
fun Home(
    dispenserViewModel: DispenserViewModel = viewModel(),
    onDispenserSelected: (Int) -> Unit,
    onNewReport: (Int) -> Unit,
    onShowReports: (Int) -> Unit,
    snackbarChannel: Channel<SnackbarInfo>,
) {
    AppSkeleton(title = stringResource(id = R.string.dispensers_route_title)) { padding, snackbarHostState ->
        val dispensers by dispenserViewModel.dispensers.observeAsState(listOf())

        // Register listening for SnackBar events
        val resources = LocalContext.current.resources
        val lifecycleOwner = LocalLifecycleOwner.current
        LaunchedEffect(snackbarHostState, resources) {
            while (isActive) {
                Log.d("REPORT", "Waiting whenResumed on $this")
                val snackbar = lifecycleOwner.lifecycle.whenResumed {
                    Log.d("REPORT", "Resumed, receiving on ${this@LaunchedEffect} -> $this")
                    snackbarChannel.receive()
                }
                Log.d("REPORT", "Received on $this")
                try {
                    val snackbarResult = snackbarHostState.showSnackbar(
                        resources.getString(snackbar.message),
                        snackbar.actionLabel?.let(resources::getString),
                        duration = SnackbarDuration.Long,
                    )
                    Log.d("REPORT", "Shown on $this")

                    if (snackbarResult == SnackbarResult.ActionPerformed) {
                        snackbar.onAction?.invoke()
                    }
                } catch (err: Throwable) {
                    Log.e("REPORT", err.toString())
                }
            }
        }

        if (dispensers.isEmpty()) {
            LoadingIndicator(textId = R.string.dispensers_loading_indicator)
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(dispensers) {
                    DispenserCard(
                        dispenser = it,
                        onDispenserSelected = onDispenserSelected,
                        onNewReport = onNewReport,
                        onShowReports = onShowReports,
                    )
                }
            }
        }
    }
}