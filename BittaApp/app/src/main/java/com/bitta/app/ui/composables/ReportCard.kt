package com.bitta.app.ui.composables

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bitta.app.model.Report

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportCard(report: Report) {
    AppCard(internalPadding = 0.dp) {
        ListItem(
            modifier = Modifier.fillMaxHeight(),
            overlineText = { Text(report.dateString) },
            headlineText = { Text(stringResource(report.kind.labelId)) },
            supportingText = {
                if (report.description.isNotBlank())
                    Text(report.description)
            },
            leadingContent = {
                // TODO: Round icon
                // TODO: Center icon vertically
                Icon(report.kind.icon, stringResource(report.kind.labelId))
            }
        )
    }
}