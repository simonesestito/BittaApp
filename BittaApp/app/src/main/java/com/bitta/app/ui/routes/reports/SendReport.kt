package com.bitta.app.ui.routes.reports

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.bitta.app.R
import com.bitta.app.datasource.DataSource
import com.bitta.app.datasource.addReport
import com.bitta.app.model.Report
import com.bitta.app.model.ReportKind
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
    dispenserId: Int,
    onBack: () -> Unit,
    onReportSent: () -> Unit,
    onSend: (String) -> String?,
    content: @Composable (MutableState<String>) -> Unit,
) {
    val inputState = remember { mutableStateOf("") }

    AppSkeleton(
        title = stringResource(R.string.new_report_route_title),
        subtitle = stringResource(R.string.dispenser_argument_route_subtitle, dispenserId),
        onBackRoute = onBack,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                modifier = Modifier.imePadding(),
                icon = { Icon(AppIcons.Send, contentDescription = null) },
                text = { Text(stringResource(R.string.report_fab_send_label)) },
                onClick = {
                    val description =
                        onSend(inputState.value) ?: return@ExtendedFloatingActionButton
                    sendReport(description, dispenserId)
                    onReportSent()
                },
            )
        },
    ) { padding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            content(inputState)
        }
    }
}