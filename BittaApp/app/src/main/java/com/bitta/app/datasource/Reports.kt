package com.bitta.app.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bitta.app.model.ProductReport
import com.bitta.app.model.Report
import com.bitta.app.model.ReportKind

private val mutableReports = MutableLiveData(
    listOf(
        // Dispenser #10003
        Report(
            description = "Il distributore non da resto",
            kind = ReportKind.AUTOMATIC_REPORT,
            dispenserId = 10003,
            dateString = "5 minuti fa",
        ),
        Report(
            description = "I numeri dal 10 al 20 potrebbero non funzionare",
            kind = ReportKind.TECHNICAL_REPORT,
            dispenserId = 10003,
            dateString = "10 minuti fa",
        ),
        Report(
            description = "Resto non dato",
            kind = ReportKind.USER_REPORT,
            dispenserId = 10003,
            dateString = "15 minuti fa",
        ),
        ProductReport(
            description = "Prodotto non erogato: Caffè classico",
            dispenserId = 10003,
            dateString = "20 minuti fa",
            productId = 13,
        ),
        Report(
            description = "Resto non dato",
            kind = ReportKind.USER_REPORT,
            dispenserId = 10003,
            dateString = "45 minuti fa",
        ),

        // Dispenser #10004
        Report(
            description = "",
            kind = ReportKind.TECHNICAL_ACTION,
            dispenserId = 10004,
            dateString = "Adesso",
        ),
        Report(
            description = "I prodotti non vengono erogati correttamente",
            kind = ReportKind.TECHNICAL_REPORT,
            dispenserId = 10004,
            dateString = "2 minuti fa",
        ),
        ProductReport(
            description = "Prodotto non erogato: Caffè classico",
            dispenserId = 10004,
            dateString = "3 minuti fa",
            productId = 13,
        ),
        Report(
            description = "Resto non dato",
            kind = ReportKind.USER_REPORT,
            dispenserId = 10004,
            dateString = "15 minuti fa",
        ),
        Report(
            description = "Resto non dato",
            kind = ReportKind.USER_REPORT,
            dispenserId = 10004,
            dateString = "45 minuti fa",
        ),
    )
)

val DataSource.reports: LiveData<List<Report>>
    get() = mutableReports

fun DataSource.addReport(report: Report) {
    mutableReports.postValue(
        listOf(
            reports.value.orEmpty(), listOf(report)
        ).flatten()
    )
}

fun DataSource.removeLastReport() {
    if (!reports.value.isNullOrEmpty()) {
        mutableReports.postValue(reports.value!!.subList(0, reports.value!!.size - 1))
    }
}