package com.bitta.app.ui.routes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.bitta.app.R
import com.bitta.app.ui.composables.AppSkeleton
import com.bitta.app.ui.composables.ButtonsRow
import com.bitta.app.ui.composables.CameraPreview

@Composable
fun PostPurchaseDelivery(
    dispenserId: Int,
    onCancelPurchase: () -> Unit,
    onProductDelivered: () -> Unit,
) {
    // TODO: Even closing and re-opening the app must persist this screen

    // TODO: BackHandler to cancel

    AppSkeleton(
        title = stringResource(R.string.post_purchase_delivery_route_title),
        subtitle = stringResource(R.string.dispenser_argument_route_subtitle, dispenserId),
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding),
            Arrangement.SpaceBetween,
            Alignment.CenterHorizontally,
        ) {
            Text("AAAAAAAAA") // TODO: Add filled card with success icon and message

            CameraPreview(
                Modifier
                    .padding(horizontal = dimensionResource(R.dimen.app_large_spacing))
                    .aspectRatio(1f)
                    .clickable(onClick = onProductDelivered /* Fake QR scan, for prototype demo */)
            )

            ButtonsRow {
                // TODO: Add left cancel button, with confirmation
            }
        }
    }
}