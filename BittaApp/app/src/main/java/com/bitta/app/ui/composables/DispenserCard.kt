package com.bitta.app.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.RunningWithErrors
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.bitta.app.R
import com.bitta.app.model.Dispenser
import com.bitta.app.model.WorkingStatus

@Composable
fun DispenserCard(
    dispenser: Dispenser,
    onDispenserSelected: (Int) -> Unit,
    onNewReport: (Int) -> Unit,
    onShowReports: (Int) -> Unit,
) {
    AppCard(paddingValues = appCardPaddingValues(top = 0.dp)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("#${dispenser.id}", style = MaterialTheme.typography.titleLarge)
            DispenserWorkingIndicator(dispenser.workingStatus)
        }
        Text(dispenser.address)
        Text(text = dispenser.locationDescription)
        ButtonsRow {
            if (dispenser.workingStatus == WorkingStatus.OK) {
                OutlinedButton(
                    modifier = Modifier.padding(end = dimensionResource(R.dimen.button_spacing)),
                    onClick = { onNewReport(dispenser.id) },
                ) {
                    AppButtonContent(
                        icon = AppIcons.Flag,
                        label = R.string.dispenser_report_issue_button_label,
                    )
                }
            } else {
                OutlinedButton(
                    modifier = Modifier.padding(end = dimensionResource(R.dimen.button_spacing)),
                    onClick = { onShowReports(dispenser.id) },
                ) {
                    AppButtonContent(
                        icon = AppIcons.RunningWithErrors,
                        label = R.string.dispenser_see_issues_button_label,
                    )
                }
            }

            Button(
                modifier = Modifier.padding(end = dimensionResource(R.dimen.app_small_spacing)),
                onClick = { onDispenserSelected(dispenser.id) },
                enabled = dispenser.workingStatus != WorkingStatus.NOT_WORKING,
            ) {
                AppButtonContent(
                    icon = AppIcons.List,
                    label = R.string.dispenser_show_products_button_label,
                )
            }
        }
    }
}
