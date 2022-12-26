package com.bitta.app.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.bitta.app.R
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun ButtonsRow(
    modifier: Modifier = Modifier,
    mainAxisAlignment: FlowMainAxisAlignment = FlowMainAxisAlignment.End,
    content: @Composable () -> Unit,
) {
    FlowRow(
        modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(R.dimen.card_content_padding)),
        mainAxisAlignment = mainAxisAlignment,
    ) {
        content()
    }
}