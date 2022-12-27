package com.bitta.app.ui.routes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bitta.app.R
import com.bitta.app.model.Product
import com.bitta.app.model.ReportedProduct
import com.bitta.app.ui.composables.*
import com.bitta.app.viewmodel.ProductsViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

typealias OnShowBottomSheetProduct = (ReportedProduct) -> Unit

@Composable
fun ProductsSearch(
    dispenserId: Int,
    snackbarChannel: Channel<String>,
    onBack: () -> Unit,
    onProductPurchased: () -> Unit,
    onProductInfo: (Product) -> Unit,
    productsViewModel: ProductsViewModel = viewModel(),
) {
    val title = stringResource(R.string.products_route_title)
    val subtitle = stringResource(R.string.dispenser_argument_route_subtitle, dispenserId)
    val coroutineScope = rememberCoroutineScope()

    DisposableEffect(LocalLifecycleOwner.current, productsViewModel) {
        productsViewModel.onPurchaseCompleted = { error ->
            if (error == null) {
                onProductPurchased()
            } else {
                // Show error snackbar
                coroutineScope.launch {
                    snackbarChannel.send(error)
                }
            }
        }
        onDispose {
            productsViewModel.onPurchaseCompleted = null
        }
    }

    LaunchedEffect(productsViewModel) {
        // Set new dispenser ID
        productsViewModel.search(dispenserId, "")
    }

    ProductsBottomSheetWrapper { onShowReport ->
        AppSkeleton(title, subtitle, onBack) { padding, snackbarHost ->
            val loading by productsViewModel.loading.observeAsState(true)
            val products by productsViewModel.products.observeAsState(listOf())
            val query by productsViewModel.query.observeAsState("")

            LaunchedEffect(snackbarHost) {
                while (isActive) {
                    val snackbarText = snackbarChannel.receive()
                    snackbarHost.showSnackbar(snackbarText)
                }
            }

            if (loading) {
                LoadingIndicator(textId = R.string.dispenser_products_loading_indicator)
            } else {
                ProductsColumn(
                    padding = padding,
                    query = query,
                    products = products,
                    onProductInfo = onProductInfo,
                    dispenserId = dispenserId,
                    onShowReport = onShowReport,
                )
            }
        }
    }
}

@Composable
private fun ProductsColumn(
    padding: PaddingValues,
    query: String,
    products: List<ReportedProduct>,
    onProductInfo: (Product) -> Unit,
    onShowReport: OnShowBottomSheetProduct,
    productsViewModel: ProductsViewModel = viewModel(),
    dispenserId: Int,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        item {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = dimensionResource(R.dimen.app_small_spacing)),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val purchaseTipString =
                    stringResource(R.string.products_list_payment_in_queue_icon_label)
                Icon(
                    AppIcons.TipsAndUpdates,
                    contentDescription = purchaseTipString,
                )
                Spacer(Modifier.width(dimensionResource(R.dimen.app_small_spacing)))
                Text(purchaseTipString, style = MaterialTheme.typography.bodyMedium)
            }
        }

        item {
            DeletableTextField(
                modifier = Modifier
                    .padding(
                        vertical = dimensionResource(R.dimen.app_medium_spacing),
                        horizontal = dimensionResource(R.dimen.app_large_spacing),
                    )
                    .fillMaxWidth(),
                value = query,
                onValueChange = { query ->
                    productsViewModel.search(dispenserId, query)
                },
                label = { Text(stringResource(R.string.products_search_bar_label)) },
                leadingIcon = {
                    Icon(
                        AppIcons.Search, stringResource(R.string.products_search_bar_label)
                    )
                },
                imeAction = ImeAction.Search,
            )
        }

        items(products) {
            val context = LocalContext.current
            ProductCard(
                it,
                onProductPurchase = { product -> productsViewModel.buyProduct(product, context) },
                onProductInfo = onProductInfo,
                onShowReportWarning = onShowReport,
            )
        }

        item {
            if (products.isEmpty()) {
                Text(
                    stringResource(R.string.products_search_not_found_description),
                    modifier = Modifier
                        .padding(vertical = dimensionResource(R.dimen.app_large_spacing))
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ProductsBottomSheetWrapper(
    productsViewModel: ProductsViewModel = viewModel(),
    content: @Composable (OnShowBottomSheetProduct) -> Unit,
) {
    var product by remember { mutableStateOf<ReportedProduct?>(null) }
    val context = LocalContext.current

    ModalBottomSheet(
        title = stringResource(R.string.product_report_warning_title),
        description = stringResource(
            R.string.product_report_warning_description,
            product?.lastReport?.dateString.orEmpty(),
            product?.product?.name.orEmpty(),
        ),
        buttons = { onClose ->
            if (product == null) onClose()

            OutlinedButton(onClick = onClose) {
                AppButtonContent(AppIcons.Close, R.string.product_purchase_cancel_button)
            }
            Spacer(Modifier.width(dimensionResource(R.dimen.button_spacing)))
            Button(onClick = {
                onClose()
                productsViewModel.buyProduct(product?.product!!, context)
            }) {
                AppButtonContent(AppIcons.Payments, R.string.product_do_purchase_with_report_button)
            }
        },
    ) { openBottomSheet ->
        content { productToShow ->
            product = productToShow
            openBottomSheet()
        }
    }
}