package com.bitta.app.ui.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.bitta.app.R

@Composable
fun AppDivider() {
    Divider(
        Modifier.padding(
            vertical = dimensionResource(R.dimen.app_small_spacing),
            horizontal = dimensionResource(R.dimen.app_large_spacing),
        )
    )
}