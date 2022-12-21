package com.bitta.app.ui.routes

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bitta.app.R
import com.bitta.app.model.Report
import com.bitta.app.ui.composables.AppIcons
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
        subtitle = stringResource(R.string.dispenser_argument_route_subtitle, dispenserId),
        onBackRoute = onBack,
        floatingActionButton = {
            if (!loading && brokenReports.isEmpty()) {
                val fabLabel = stringResource(R.string.reports_list_fab_label)
                ExtendedFloatingActionButton(
                    onClick = onNewReport,
                    icon = { Icon(AppIcons.Flag, contentDescription = fabLabel) },
                    text = { Text(fabLabel) },
                )
            }
        }
    ) { padding ->
        if (loading) {
            LoadingIndicator(R.string.dispenser_reports_loading_indicator)
        } else if (issues.size + brokenReports.size + userReports.size == 0) {
            // No issues of any kind
            // Empty view
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val text = stringResource(R.string.reports_list_empty_view)
                Icon(AppIcons.Check, contentDescription = text)
                Text(
                    modifier = Modifier.padding(dimensionResource(R.dimen.loading_indicator_padding)),
                    text = text,
                )
            }
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