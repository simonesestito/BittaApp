package com.bitta.app.ui.routes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.bitta.app.R
import com.bitta.app.ui.composables.AppSkeleton

@Composable
fun ProductsSearch(dispenserId: Int, onBack: () -> Unit) {
    val title = stringResource(R.string.products_route_title, dispenserId)
    AppSkeleton(title, onBack) {
        Box(modifier = Modifier.padding(it)) {
            Text("Hi")
        }
    }
}