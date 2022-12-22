package com.bitta.app.ui.routes.reports

import androidx.compose.runtime.Composable
import com.bitta.app.model.UserReportKind

@Composable
fun UserChangeReport(
    dispenserId: Int,
    onBack: () -> Unit,
    onReportSent: (() -> Unit) -> Unit,
) {
    UserGenericNoInfoReport(UserReportKind.MISSING_CHANGE, dispenserId, onBack, onReportSent)
}
