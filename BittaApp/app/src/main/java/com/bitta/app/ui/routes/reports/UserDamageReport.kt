package com.bitta.app.ui.routes.reports

import androidx.compose.runtime.Composable
import com.bitta.app.model.UserReportKind

@Composable
fun UserDamageReport(
    dispenserId: Int,
    onBack: () -> Unit,
    onReportSent: (() -> Unit) -> Unit,
) {
    UserGenericNoInfoReport(UserReportKind.DAMAGED_DISPENSER, dispenserId, onBack, onReportSent)
}
