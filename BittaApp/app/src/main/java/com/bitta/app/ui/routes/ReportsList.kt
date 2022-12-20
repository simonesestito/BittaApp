package com.bitta.app.ui.routes

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bitta.app.R
import com.bitta.app.model.Report
import com.bitta.app.ui.composables.AppSkeleton
import com.bitta.app.ui.composables.LoadingIndicator
import com.bitta.app.ui.composables.ReportCard
import com.bitta.app.viewmodel.ReportsViewModel

@Composable
fun ReportsList(
    dispenserId: Int,
    onBack: () -> Unit,
    onNewReport: () -> Unit,
    reportsViewModel: ReportsViewModel = viewModel(),
) {
    reportsViewModel.loadReportsForDispenser(dispenserId)

    val loading by reportsViewModel.loadingStatus.observeAsState(true)
    val issues by reportsViewModel.issues.observeAsState(emptyList())
    val brokenReports by reportsViewModel.brokenReports.observeAsState(emptyList())
    val userReports by reportsViewModel.reports.observeAsState(emptyList())

    AppSkeleton(
        title = stringResource(R.string.dispenser_reports_route_title),
        subtitle = stringResource(R.string.products_route_subtitle, dispenserId),
        onBackRoute = onBack,
        // TODO: Add Extended FAB for new report, only if brokenReports is empty
    ) { padding ->
        if (loading) {
            LoadingIndicator(R.string.dispenser_reports_loading_indicator)
        } else {
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(brokenReports) {
                    ReportCard(report = it)
                }

                reportsWithTitle(issues, R.string.report_issues_list_header)
                reportsWithTitle(userReports, R.string.report_user_reports_list_header)
            }
        }
    }
}

private fun LazyListScope.reportsWithTitle(reports: List<Report>, @StringRes title: Int) {
    if (reports.isNotEmpty()) {
        item {
            Text(
                stringResource(title),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimensionResource(R.dimen.app_medium_spacing)),
                textAlign = TextAlign.Center,
            )
        }

        items(reports) {
            ReportCard(report = it)
        }
    }
}