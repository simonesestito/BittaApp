package com.bitta.app.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Handyman
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.bitta.app.R
import com.bitta.app.ui.composables.AppIcons

data class Report(
    val description: String,
    val kind: ReportKind,
    val dispenserId: Int,
    val dateString: String, // A date should be a proper date, but in this fake world it's "since X minutes" and similar
)

enum class ReportKind {
    AUTOMATIC_REPORT, USER_REPORT, TECHNICAL_REPORT, TECHNICAL_ACTION;

    val labelId: Int
        @StringRes get() = when (this) {
            AUTOMATIC_REPORT -> R.string.automatic_report_label
            USER_REPORT -> R.string.user_report_label
            TECHNICAL_REPORT -> R.string.technical_report_label
            TECHNICAL_ACTION -> R.string.technical_action_label
        }

    val color: Color
        get() = when (this) {
            AUTOMATIC_REPORT -> Color.DarkGray
            USER_REPORT -> Color(0xFFA38903)
            TECHNICAL_REPORT, TECHNICAL_ACTION -> Color.Red
        }

    val icon: ImageVector
        get() = when (this) {
            AUTOMATIC_REPORT, USER_REPORT -> AppIcons.Warning
            TECHNICAL_REPORT -> AppIcons.Error
            TECHNICAL_ACTION -> AppIcons.Handyman
        }
}

enum class UserReportKind {
    PRODUCT_DELIVERY,
    MISSING_CHANGE,
    DAMAGED_DISPENSER,
    OTHER;

    val labelId: Int
        @StringRes get() = when (this) {
            PRODUCT_DELIVERY -> R.string.user_report_kind_product_delivery_label
            MISSING_CHANGE -> R.string.user_report_kind_missing_change_label
            DAMAGED_DISPENSER -> R.string.user_report_kind_damaged_dispenser_label
            OTHER -> R.string.user_report_kind_other_label
        }

    val descriptionId: Int
        @StringRes get() = when (this) {
            PRODUCT_DELIVERY -> R.string.user_report_kind_product_delivery_description
            MISSING_CHANGE -> R.string.user_report_kind_missing_change_description
            DAMAGED_DISPENSER -> R.string.user_report_kind_damaged_dispenser_description
            OTHER -> R.string.user_report_kind_other_description
        }

    val icon: ImageVector
        get() = when (this) {
            // TODO: User Report kind, add leading icons
            PRODUCT_DELIVERY -> AppIcons.ErrorOutline
            MISSING_CHANGE -> AppIcons.ErrorOutline
            DAMAGED_DISPENSER -> AppIcons.ErrorOutline
            OTHER -> AppIcons.ErrorOutline
        }
}