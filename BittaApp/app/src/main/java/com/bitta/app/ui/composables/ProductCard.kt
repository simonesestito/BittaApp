package com.bitta.app.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.bitta.app.R
import com.bitta.app.model.Product
import com.bitta.app.toStringAsFixed

@Composable
fun ProductCard(
    product: Product,
    onProductPurchase: (Product) -> Unit,
    onProductInfo: (Product) -> Unit,
) {
    AppCard {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(product.name, style = MaterialTheme.typography.titleLarge)
            Text(
                "${product.price.toStringAsFixed(2)}€",
                style = MaterialTheme.typography.titleLarge,
            )
        }

        Text(product.description)

        ButtonsRow {
            OutlinedIconButton(
                modifier = Modifier.padding(end = dimensionResource(R.dimen.button_spacing)),
                onClick = { onProductInfo(product) },
                colors = IconButtonDefaults.outlinedIconButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary,
                ),
            ) {
                Icon(
                    AppIcons.Info,
                    contentDescription = stringResource(R.string.product_info_button_label),
                )
            }

            Button(onClick = { onProductPurchase(product) }) {
                AppButtonContent(
                    icon = AppIcons.Payments,
                    label = R.string.product_purchase_button_label,
                )
            }
        }
    }
}