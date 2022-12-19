package com.bitta.app.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.bitta.app.R
import com.bitta.app.model.Dispenser

@Composable
fun DispenserCard(dispenser: Dispenser) {
    AppCard {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column {
                Text("#${dispenser.id}", style = MaterialTheme.typography.titleLarge)
                Text(dispenser.address)
            }
            // TODO: Add working indicator
        }
        Text(text = dispenser.locationDescription)
        ButtonsRow {
            OutlinedButton(
                modifier = Modifier.padding(end = dimensionResource(R.dimen.app_small_spacing)),
                onClick = { /*TODO*/ },
            ) {
                AppButtonContent(
                    icon = AppIcons.Flag,
                    label = R.string.dispenser_report_issue_button_label,
                )
            }
            Button(
                modifier = Modifier.padding(end = dimensionResource(R.dimen.app_small_spacing)),
                onClick = { /*TODO*/ },
            ) {
                AppButtonContent(
                    icon = AppIcons.List,
                    label = R.string.dispenser_show_products_button_label,
                )
            }
        }
    }
}
