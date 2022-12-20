package com.bitta.app.datasource

import com.bitta.app.model.Product

/**
 * Fake products to retrieve
 */
val DataSource.products: List<Product>
    get() = listOf(
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
    ).sortedBy { it.name }