package com.bitta.app.ui.routes.reports

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.bitta.app.R
import com.bitta.app.datasource.DataSource
import com.bitta.app.datasource.simpleProducts
import com.bitta.app.model.UserReportKind
import com.bitta.app.ui.composables.EditableDropdownMenu

@Composable
fun UserProductReport(
    dispenserId: Int,
    onBack: () -> Unit,
    onReportSent: (() -> Unit) -> Unit,
) {
    val productReportLabel = stringResource(UserReportKind.PRODUCT_DELIVERY.descriptionId)
    val products = DataSource.simpleProducts
    val errorState = remember { mutableStateOf(false) }

    ReportDetailsSkeleton(
        kind = UserReportKind.PRODUCT_DELIVERY,
        inputTitle = R.string.report_product_input_title,
        dispenserId = dispenserId,
        onBack = onBack,
        onReportSent = onReportSent,
        onSend = { query ->
            val product = products.firstOrNull {
                it.name.equals(query, ignoreCase = true)
            }
            errorState.value = product == null
            product?.let { "$productReportLabel: ${it.name}" }
        },
    ) { selectedProductState, onSend ->
        EditableDropdownMenu(DataSource.simpleProducts, selectedProductState, errorState) {
            onSend()
        }
    }
}
