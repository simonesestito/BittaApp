package com.bitta.app.datasource

import com.bitta.app.model.Report
import com.bitta.app.model.ReportKind

private val reports = mutableListOf(
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
    Report(
        description = "Prodotto non erogato: Caffè classico",
        kind = ReportKind.USER_REPORT,
        dispenserId = 10003,
        dateString = "20 minuti fa",
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
    Report(
        description = "Resto non dato",
        kind = ReportKind.USER_REPORT,
        dispenserId = 10004,
        dateString = "15 minuti fa",
    ),
    Report(
        description = "Prodotto non erogato: Caffè classico",
        kind = ReportKind.USER_REPORT,
        dispenserId = 10004,
        dateString = "20 minuti fa",
    ),
    Report(
        description = "Resto non dato",
        kind = ReportKind.USER_REPORT,
        dispenserId = 10004,
        dateString = "45 minuti fa",
    ),
)

fun DataSource.reportsByDispenser(dispenserId: Int) =
    reports.filter { it.dispenserId == dispenserId }

fun DataSource.addReport(report: Report) = reports.add(report)