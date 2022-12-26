package com.bitta.app.ui.routes.reports

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.bitta.app.R
import com.bitta.app.datasource.DataSource
import com.bitta.app.datasource.addReport
import com.bitta.app.datasource.removeLastReport
import com.bitta.app.model.Report
import com.bitta.app.model.ReportKind
import com.bitta.app.model.UserReportKind
import com.bitta.app.ui.composables.AppIcons
import com.bitta.app.ui.composables.AppSkeleton

private fun sendReport(description: String, dispenserId: Int) {
    DataSource.addReport(
        Report(
            description = description,
            kind = ReportKind.USER_REPORT,
            dispenserId = dispenserId,
            dateString = "Adesso",
        )
    )
}

@Composable
internal fun ReportDetailsSkeleton(
    kind: UserReportKind,
    dispenserId: Int,
    @StringRes inputTitle: Int,
    onBack: () -> Unit,
    onReportSent: (() -> Unit) -> Unit,
    onSend: (String) -> String?,
    inputState: MutableState<String> = remember { mutableStateOf("") },
    content: @Composable ColumnScope.(MutableState<String>, () -> Unit) -> Unit,
) {
    val dispenserSubtitle = stringResource(R.string.dispenser_argument_route_subtitle, dispenserId)
    val reportKindSubtitle = stringResource(kind.labelId)

    val onSendingReport = {
        val description = onSend(inputState.value)
        if (description != null) {
            sendReport(description, dispenserId)
            onReportSent {
                // Cancel action
                DataSource.removeLastReport()
            }
        }
    }

    AppSkeleton(
        title = stringResource(R.string.new_report_route_title),
        subtitle = "$reportKindSubtitle - $dispenserSubtitle",
        onBackRoute = onBack,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                modifier = Modifier.imePadding(),
                icon = { Icon(AppIcons.Send, contentDescription = null) },
                text = { Text(stringResource(R.string.report_fab_send_label)) },
                onClick = onSendingReport,
            )
        },
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = stringResource(inputTitle),
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(Modifier.height(dimensionResource(R.dimen.app_small_spacing)))
            content(inputState, onSendingReport)
        }
    }
}