package com.bitta.app

import com.bitta.app.model.Dispenser
import com.bitta.app.model.Product
import com.bitta.app.model.WorkingStatus

object DataSource {
    val dispensers = listOf(
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

    val products = listOf(
        Product(
            id = 1,
            name = "Acqua naturale",
            description = "Bottiglia di acqua naturale da 0.5l",
            price = 0.5,
            ingredients = listOf("Acqua"),
            nutritionalValues = listOf(),
        ),
        Product(
            id = 2,
            name = "Acqua frizzante",
            description = "Bottiglia di acqua leggermente gassata da 0.5l",
            price = 0.5,
            ingredients = listOf("Acqua"),
            nutritionalValues = listOf(),
        ),
        Product(
            id = 3,
            name = "Caffè classico",
            description = "Caffè espresso, classico",
            price = 0.5,
            ingredients = listOf("Acqua", "Caffè in grani", "Zucchero"),
            nutritionalValues = listOf(
                "Calorie" to "10",
                "Proteine" to "0g",
                "Carboidrati" to "0g",
                "Zuccheri" to "10g",
                "Grassi" to "0,9g",
                "Sale" to "0g",
            ),
        ),
    )
}