package com.bitta.app.ui.routes.reports

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.bitta.app.R
import com.bitta.app.model.UserReportKind
import com.bitta.app.ui.composables.AppIcons
import com.bitta.app.ui.composables.DeletableTextField

@Composable
fun UserGenericNoInfoReport(
    kind: UserReportKind,
    dispenserId: Int,
    onBack: () -> Unit,
    onReportSent: (() -> Unit) -> Unit,
) {
    val issueDescription = stringResource(kind.descriptionId)

    ReportDetailsSkeleton(
        kind = kind,
        inputTitle = R.string.report_other_notes_input_title,
        dispenserId = dispenserId,
        onBack = onBack,
        onReportSent = onReportSent,
        onSend = { issueDescription },
    ) { inputState ->
        var query by inputState
        DeletableTextField(
            modifier = Modifier
                .padding(
                    vertical = dimensionResource(R.dimen.input_vertical_padding),
                    horizontal = dimensionResource(R.dimen.input_horizontal_padding),
                )
                .fillMaxWidth(),
            value = query,
            onValueChange = { query = it },
            supportingText = { Text(stringResource(R.string.optional_input_supporting_text)) },
            label = { Text(stringResource(R.string.report_other_input_label)) },
            leadingIcon = {
                Icon(AppIcons.EditNote, null)
            },
        )
    }
}