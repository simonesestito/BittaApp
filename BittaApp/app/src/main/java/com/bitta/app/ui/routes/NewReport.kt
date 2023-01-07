package com.bitta.app.ui.routes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.bitta.app.R
import com.bitta.app.model.UserReportKind
import com.bitta.app.ui.composables.AppDivider
import com.bitta.app.ui.composables.AppSkeleton
import com.bitta.app.ui.composables.UserReportListItem

@Composable
fun NewReport(
    dispenserId: Int,
    onBack: () -> Unit,
    onTypeSelected: (UserReportKind) -> Unit,
) {
    AppSkeleton(
        title = stringResource(R.string.new_report_route_title),
        subtitle = stringResource(R.string.dispenser_argument_route_subtitle, dispenserId),
        onBackRoute = onBack,
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            for (reportKind in UserReportKind.values()) {
                UserReportListItem(reportKind, onTypeSelected)
                if (reportKind != UserReportKind.values().last()) {
                    // If this is not the last one, show separator
                    AppDivider()
                }
            }
        }
    }
}