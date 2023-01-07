package com.bitta.app.datasource

import com.bitta.app.model.Dispenser

/**
 * Fake dispensers to retrieve
 */
val DataSource.dispensers: List<Dispenser>
    get() = listOf(
        Dispenser(
            10001,
            "Via del Castro Laurenziano, 7/a",
            "Edificio di Ingegneria - Piano Terra",
        ),
        Dispenser(
            10002,
            "Via del Castro Laurenziano, 7/a",
            "Edificio di Ingegneria - Piano 1, scala A",
        ),
        Dispenser(
            10003,
            "Via del Castro Laurenziano, 7/a",
            "Edificio di Ingegneria - Piano 1, scala B",
        ),
        Dispenser(
            10004,
            "Via del Castro Laurenziano, 7/a",
            "Edificio di Ingegneria - Piano 2",
        ),
    )