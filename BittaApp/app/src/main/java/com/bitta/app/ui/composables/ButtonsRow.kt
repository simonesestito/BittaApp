package com.bitta.app.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.bitta.app.R

@Composable
fun ButtonsRow(content: @Composable RowScope.() -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(R.dimen.card_content_padding)),
        horizontalArrangement = Arrangement.End,
    ) {
        content()
    }
}