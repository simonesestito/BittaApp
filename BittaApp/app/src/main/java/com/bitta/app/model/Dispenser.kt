package com.bitta.app.model

import com.bitta.app.datasource.DataSource
import com.bitta.app.datasource.reportsByDispenser

data class Dispenser(
    val id: Int,
    val address: String,
    val locationDescription: String,
) {
    val workingStatus: WorkingStatus
        get() {
            val reports = DataSource.reportsByDispenser(id)
            return if (reports.isEmpty())
                WorkingStatus.OK
            else if (reports.firstOrNull { it.kind == ReportKind.TECHNICAL_ACTION } == null)
                WorkingStatus.NOT_WORKING
            else
                WorkingStatus.WITH_REPORTS
        }
}

enum class WorkingStatus {
    OK,
    WITH_REPORTS,
    NOT_WORKING,
}