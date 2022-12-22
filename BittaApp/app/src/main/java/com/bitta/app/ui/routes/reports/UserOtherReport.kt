package com.bitta.app.ui.routes.reports

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.bitta.app.R
import com.bitta.app.model.UserReportKind
import com.bitta.app.ui.composables.AppIcons
import com.bitta.app.ui.composables.DeletableTextField

@Composable
fun UserOtherReport(
    dispenserId: Int,
    onBack: () -> Unit,
    onReportSent: (() -> Unit) -> Unit,
) {
    val issueDescription = stringResource(UserReportKind.OTHER.descriptionId)
    val inputState = remember { mutableStateOf("") }
    var query by inputState
    var error by remember { mutableStateOf(false) }

    ReportDetailsSkeleton(
        kind = UserReportKind.OTHER,
        inputTitle = R.string.report_other_reason_input_title,
        dispenserId = dispenserId,
        onBack = onBack,
        onReportSent = onReportSent,
        onSend = {
            if (query.isBlank()) {
                // Input is required
                error = true
                null
            } else {
                // Input given!
                issueDescription
            }
        },
    ) { _ ->
        DeletableTextField(
            modifier = Modifier
                .padding(
                    vertical = dimensionResource(R.dimen.input_vertical_padding),
                    horizontal = dimensionResource(R.dimen.input_horizontal_padding),
                )
                .fillMaxWidth(),
            value = query,
            onValueChange = { query = it },
            label = { Text(stringResource(R.string.report_other_reason_input_label)) },
            leadingIcon = {
                Icon(AppIcons.EditNote, null)
            },
            isError = error,
            supportingText = if (error) ({
                Text(stringResource(R.string.input_required_error_label))
            }) else null
        )
    }
}