package com.bitta.app.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.bitta.app.R

@Composable
fun AppCard(content: @Composable ColumnScope.() -> Unit) {
    OutlinedCard(
        modifier = Modifier
            .padding(
                horizontal = dimensionResource(id = R.dimen.app_large_spacing),
                vertical = dimensionResource(id = R.dimen.cards_list_item_spacing),
            )
            .fillMaxWidth(),
    ) {
        Column(Modifier.padding(dimensionResource(id = R.dimen.card_content_padding))) {
            content()
        }
    }
}

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