package com.bitta.app.ui.routes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.bitta.app.R
import com.bitta.app.ui.composables.*
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterialApi::class)
@Composable
fun PostPurchaseDelivery(
    dispenserId: Int,
    onCancelPurchase: () -> Unit,
    onProductDelivered: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    // TODO: Even closing and re-opening the app must persist this screen

    BackHandler {
        coroutineScope.launch {
            bottomSheetState.show()
        }
    }

    ModalBottomSheet(state = bottomSheetState,
        title = stringResource(R.string.post_purchase_cancel_confirmation_title),
        description = stringResource(R.string.post_purchase_cancel_confirmation_description),
        cancelButton = { close ->
            Button(
                onClick = { close(); onCancelPurchase(); }, colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                )
            ) {
                AppButtonContent(
                    AppIcons.CreditCardOff, label = R.string.post_purchase_cancel_confirm_refund
                )
            }
        },
        confirmButton = { close ->
            Button(onClick = close) {
                AppButtonContent(
                    AppIcons.ShoppingCartCheckout,
                    label = R.string.post_purchase_cancel_continue_delivery
                )
            }
        }) { open ->
        PostPurchaseDeliveryContent(dispenserId, onProductDelivered, open)
    }
}

@Composable
private fun PostPurchaseDeliveryContent(
    dispenserId: Int,
    onProductDelivered: () -> Unit,
    openBottomSheet: OnOpenCallback,
) {
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

            ButtonsRow(
                Modifier.padding(dimensionResource(R.dimen.button_spacing)),
                FlowMainAxisAlignment.Start,
            ) {
                TextButton(onClick = openBottomSheet) {
                    AppButtonContent(
                        AppIcons.CreditCardOff,
                        R.string.product_purchase_cancel_delivery_button,
                    )
                }
            }
        }
    }
}