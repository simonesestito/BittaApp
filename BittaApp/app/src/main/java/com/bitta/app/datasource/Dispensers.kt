package com.bitta.app.datasource

import com.bitta.app.model.Dispenser
import com.bitta.app.model.WorkingStatus

/**
 * Fake dispensers to retrieve
 */
val DataSource.dispensers: List<Dispenser>
    get() = listOf(
        Dispenser(
            10001,
            "Via del Castro Laurenziano, 7/a",
            "Edificio di Ingegneria - Piano Terra",
            WorkingStatus.OK,
        ),
        Dispenser(
            10002,
            "Via del Castro Laurenziano, 7/a",
            "Edificio di Ingegneria - Piano 1, scala A",
            WorkingStatus.OK,
        ),
        Dispenser(
            10003,
            "Via del Castro Laurenziano, 7/a",
            "Edificio di Ingegneria - Piano 1, scala B",
            WorkingStatus.WITH_REPORTS,
        ),
        Dispenser(
            10004,
            "Via del Castro Laurenziano, 7/a",
            "Edificio di Ingegneria - Piano 2",
            WorkingStatus.NOT_WORKING,
        ),
    )