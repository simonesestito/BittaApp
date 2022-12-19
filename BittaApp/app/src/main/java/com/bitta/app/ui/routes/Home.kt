package com.bitta.app.ui.routes

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bitta.app.R
import com.bitta.app.ui.composables.AppSkeleton
import com.bitta.app.ui.composables.DispenserCard
import com.bitta.app.ui.composables.LoadingIndicator
import com.bitta.app.viewmodel.DispenserViewModel

@Composable
fun Home(dispenserViewModel: DispenserViewModel = viewModel()) {
    AppSkeleton(title = stringResource(id = R.string.dispensers_route_title)) { innerPadding ->
        val dispensers by dispenserViewModel.dispensers.observeAsState(listOf())

        if (dispensers.isEmpty()) {
            LoadingIndicator(textId = R.string.dispensers_loading_indicator)
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                items(dispensers) {
                    DispenserCard(it)
                }
            }
        }
    }
}