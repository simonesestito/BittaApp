package com.bitta.app.ui.routes

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.bitta.app.R
import com.bitta.app.ui.composables.*

@Composable
fun DeliverySuccess(
    dispenserId: Int,
    onDone: () -> Unit,
    onReport: (dispenserId: Int) -> Unit,
) {
    BackHandler(onBack = onDone)

    AppSkeleton(
        title = stringResource(R.string.post_delivery_route_title),
        subtitle = stringResource(R.string.dispenser_argument_route_subtitle, dispenserId),
        onBackRoute = onDone,
    ) { padding ->
        Box(Modifier.fillMaxSize()) {
            SuccessFilledCard(
                modifier = Modifier.align(Alignment.Center),
                title = stringResource(R.string.delivery_success_message_title),
                description = stringResource(R.string.delivery_success_message_description),
            )

            ButtonsRow(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(dimensionResource(R.dimen.app_large_spacing))
                    .padding(bottom = padding.calculateBottomPadding())
            ) {
                TextButton(onClick = { onReport(dispenserId) }) {
                    AppButtonContent(AppIcons.Flag, R.string.dispenser_report_issue_button_label)
                }
                Spacer(Modifier.width(dimensionResource(R.dimen.button_spacing)))
                Button(onClick = onDone) {
                    AppButtonContent(AppIcons.Check, R.string.purchase_and_delivery_done_button)
                }
            }
        }
    }
}