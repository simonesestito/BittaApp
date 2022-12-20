package com.bitta.app.ui.routes

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bitta.app.R
import com.bitta.app.model.Product
import com.bitta.app.ui.composables.AppSkeleton
import com.bitta.app.ui.composables.LoadingIndicator
import com.bitta.app.ui.composables.ProductCard
import com.bitta.app.viewmodel.ProductsViewModel

@Composable
fun ProductsSearch(
    dispenserId: Int,
    onBack: () -> Unit,
    onProductPurchase: (Product) -> Unit,
    onProductInfo: (Product) -> Unit,
    productsViewModel: ProductsViewModel = viewModel(),
) {
    val title = stringResource(R.string.products_route_title)
    val subtitle = stringResource(R.string.products_route_subtitle, dispenserId)
    AppSkeleton(title, subtitle, onBack) { padding ->
        val products by productsViewModel.products.observeAsState(listOf())

        if (products.isEmpty()) {
            LoadingIndicator(textId = R.string.dispenser_products_loading_indicator)
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                item {
                    // TODO: Search bar
                }

                item {
                    // TODO: info label
                }

                items(products) {
                    ProductCard(
                        it,
                        onProductPurchase = onProductPurchase,
                        onProductInfo = onProductInfo,
                    )
                }
            }
        }
    }
}