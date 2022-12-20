package com.bitta.app.ui.routes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.ReceiptLong
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bitta.app.R
import com.bitta.app.ui.composables.AppDivider
import com.bitta.app.ui.composables.AppIcons
import com.bitta.app.ui.composables.AppSkeleton
import com.bitta.app.viewmodel.ProductsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductInfo(
    productId: Int,
    onBack: () -> Unit,
    productsViewModel: ProductsViewModel = viewModel(),
) {
    val product = productsViewModel.getProductById(productId)
    AppSkeleton(
        title = stringResource(R.string.product_info_route_title),
        subtitle = product.name,
        onBackRoute = onBack,
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            val descriptionLabel = stringResource(R.string.product_info_description_label)
            val ingredientsLabel = stringResource(R.string.product_info_ingredients_label)
            val nutritionalLabel = stringResource(R.string.product_info_nutritional_label)

            ListItem(headlineText = { Text(descriptionLabel) },
                supportingText = { Text(product.description) },
                leadingContent = { Icon(AppIcons.Description, descriptionLabel) })

            AppDivider()
            ListItem(headlineText = { Text(ingredientsLabel) },
                supportingText = { Text(product.ingredients.joinToString("\n") { "- $it" }) },
                leadingContent = { Icon(AppIcons.List, ingredientsLabel) })

            if (product.nutritionalValues.isNotEmpty()) {
                AppDivider()
                ListItem(
                    headlineText = { Text(nutritionalLabel) },
                    supportingText = { Text(product.nutritionalValues.joinToString("\n") { "- ${it.first}: ${it.second}" }) },
                    leadingContent = { Icon(AppIcons.ReceiptLong, nutritionalLabel) },
                )
            }
        }
    }
}