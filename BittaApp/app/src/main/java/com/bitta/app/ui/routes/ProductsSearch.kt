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

typealias OnShowBottomSheetProduct = (ReportedProduct) -> Unit

@Composable
fun ProductsSearch(
    dispenserId: Int,
    onBack: () -> Unit,
    onProductPurchase: (Product) -> Unit,
    onProductInfo: (Product) -> Unit,
    productsViewModel: ProductsViewModel = viewModel(),
) {
    val title = stringResource(R.string.products_route_title)
    val subtitle = stringResource(R.string.dispenser_argument_route_subtitle, dispenserId)

    ProductsBottomSheetWrapper(/* TODO: Show Google Pay */ onProductPurchase) { onShowReport ->
        AppSkeleton(title, subtitle, onBack) { padding ->
            val loading by productsViewModel.loading.observeAsState(true)
            val products by productsViewModel.products.observeAsState(listOf())
            val query by productsViewModel.query.observeAsState("")

            // Set new dispenser ID
            productsViewModel.search(dispenserId, "")

            if (loading) {
                LoadingIndicator(textId = R.string.dispenser_products_loading_indicator)
            } else {
                ProductsColumn(
                    padding = padding,
                    query = query,
                    products = products,
                    onProductInfo = onProductInfo,
                    onProductPurchase = onProductPurchase,
                    productsViewModel = productsViewModel,
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
    onProductPurchase: (Product) -> Unit,
    onShowReport: OnShowBottomSheetProduct,
    productsViewModel: ProductsViewModel,
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
            ProductCard(
                it,
                onProductPurchase = onProductPurchase,
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
    onProductPurchase: (Product) -> Unit,
    content: @Composable (OnShowBottomSheetProduct) -> Unit,
) {
    var product by remember { mutableStateOf<ReportedProduct?>(null) }

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
            Button(onClick = { onClose(); onProductPurchase(product?.product!!) }) {
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