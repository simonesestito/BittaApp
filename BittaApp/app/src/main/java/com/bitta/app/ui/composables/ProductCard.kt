package com.bitta.app.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.bitta.app.R
import com.bitta.app.model.Product
import com.bitta.app.model.ReportedProduct
import com.bitta.app.toDp
import com.bitta.app.toStringAsFixed
import com.bitta.app.ui.routes.OnShowBottomSheetProduct
import com.bitta.app.ui.theme.Warning

typealias OnProductCallback = (Product) -> Unit

@Composable
fun ProductCard(
    reportedProduct: ReportedProduct,
    onProductPurchase: OnProductCallback,
    onShowReportWarning: OnShowBottomSheetProduct,
    onProductInfo: OnProductCallback,
) {
    val (product, _) = reportedProduct

    AppCard {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            ProductTitleWithWarning(reportedProduct, onShowReportWarning)
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

            Button(onClick = {
                onProductPurchaseClick(
                    reportedProduct,
                    onProductPurchase,
                    onShowReportWarning,
                )
            }) {
                AppButtonContent(
                    icon = AppIcons.Payments,
                    label = R.string.product_purchase_button_label,
                )
            }
        }
    }
}

@Composable
private fun ProductTitleWithWarning(
    reportedProduct: ReportedProduct,
    onShowReportWarning: OnShowBottomSheetProduct,
) {
    Row {
        Text(reportedProduct.product.name, style = MaterialTheme.typography.titleLarge)
        if (reportedProduct.lastReport != null) {
            Icon(
                modifier = Modifier
                    .height(
                        MaterialTheme.typography.titleLarge.fontSize.toDp(),
                    )
                    .align(Alignment.CenterVertically)
                    .clickable {
                        onShowReportWarning(reportedProduct)
                    }
                    .padding(start = dimensionResource(R.dimen.app_small_spacing)),
                imageVector = AppIcons.Warning,
                tint = Color.Warning,
                contentDescription = stringResource(R.string.product_report_warning_title),
            )
        }
    }
}

private fun onProductPurchaseClick(
    reportedProduct: ReportedProduct,
    onProductPurchase: OnProductCallback,
    showWarning: OnShowBottomSheetProduct,
) {
    if (reportedProduct.lastReport == null) {
        return onProductPurchase(reportedProduct.product)
    }

    // Handle warning
    showWarning(reportedProduct)
}