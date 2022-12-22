package com.bitta.app.ui.routes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.TipsAndUpdates
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.bitta.app.ui.composables.*
import com.bitta.app.viewmodel.ProductsViewModel

@OptIn(ExperimentalMaterial3Api::class)
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

    AppSkeleton(title, subtitle, onBack) { padding ->
        val products by productsViewModel.products.observeAsState(listOf())
        val query by productsViewModel.query.observeAsState("")

        if (products.isEmpty() && query.isBlank()) {
            LoadingIndicator(textId = R.string.dispenser_products_loading_indicator)
        } else {
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
                        onValueChange = productsViewModel::search,
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
    }
}