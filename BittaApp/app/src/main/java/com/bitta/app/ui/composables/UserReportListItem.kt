package com.bitta.app.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.outlined.ArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.bitta.app.model.UserReportKind

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserReportListItem(
    reportKind: UserReportKind,
    onTypeSelected: (UserReportKind) -> Unit,
) {
    ListItem(modifier = Modifier.clickable { onTypeSelected(reportKind) },
        headlineText = { Text(stringResource(reportKind.labelId)) },
        supportingText = { Text(stringResource(reportKind.descriptionId)) },
        trailingContent = {
            Icon(
                AppIcons.ArrowRight,
                contentDescription = null,
            )
        },
        leadingContent = {
            Icon(
                reportKind.icon,
                contentDescription = stringResource(reportKind.labelId),
            )
        })
}