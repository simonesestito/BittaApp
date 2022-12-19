package com.bitta.app.ui.composables

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bitta.app.R
import com.bitta.app.mix
import com.bitta.app.model.WorkingStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DispenserWorkingIndicator(workingStatus: WorkingStatus) {
    val (icon: ImageVector, color: Color, @StringRes stringId: Int) = when (workingStatus) {
        WorkingStatus.OK -> Triple(
            AppIcons.Check,
            Color(0xFF1C6E15),
            R.string.dispenser_working_status_ok
        )
        WorkingStatus.WITH_REPORTS -> Triple(
            AppIcons.Warning,
            Color(0xFFA38903),
            R.string.dispenser_working_status_with_reports
        )
        WorkingStatus.NOT_WORKING -> Triple(
            AppIcons.Error,
            Color.Red,
            R.string.dispenser_working_status_not_working
        )
    }
    val label = stringResource(id = stringId)

    AssistChip(
        modifier = Modifier
            .padding(0.dp)
            .padding(bottom = 2.dp)
            .height(32.dp),
        label = { Text(label) },
        leadingIcon = { Icon(icon, contentDescription = label) },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = color.mix(MaterialTheme.colorScheme.surface, 0.1f),
            leadingIconContentColor = color,
        ),
        onClick = { /*TODO*/ }
    )
}