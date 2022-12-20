package com.bitta.app.datasource

import com.bitta.app.model.Product

/**
 * Fake products to retrieve
 */
val DataSource.products: List<Product>
    get() = listOf(
        Product(
            id = 1,
            name = "Cioccolata",
            description = "Cioccolata calda",
            price = 0.7,
            ingredients = listOf("latte", "fecola di patate", "zucchero", "cacao amaro"),
            nutritionalValues = listOf(
                "Calorie" to "77",
                "Proteine" to "3,5g",
                "Carboidrati" to "11g",
                "Caffeina" to "2mg",
                "Grassi" to "2,3g",
                "Colesterolo" to "8mg"
            )
        ),
        Product(
            id = 2,
            name = "Patatine",
            description = "Patatine in busta",
            price = 1.2,
            ingredients = listOf("patate"),
            nutritionalValues = listOf(
                "Calorie" to "274",
                "Proteine" to "3,28g",
                "Carboidrati" to "24,87g",
                "Zuccheri" to "2g",
                "Grassi" to "18,74g",
                "Sale" to "0,66g",
            ),
        ),
        Product(
            id = 3,
            name = "Kinder Bueno",
            description = "Kinder bueno gusto classico",
            price = 1.0,
            ingredients = listOf("latte", "burro", "soia", "zucchero", "olio di palma", "cacao"),
            nutritionalValues = listOf(
                "Calorie" to "122",
                "Proteine" to "8,6g",
                "Carboidrati" to "49,5g",
                "Zuccheri" to "41,2g",
                "Grassi" to "37,3g",
                "Sale" to "0,272g"
            )
        ),
        Product(
            id = 4,
            name = "Taralli",
            description = "Taralli all'olio di oliva",
            price = 0.8,
            ingredients = listOf("farina", "vino bianco", "olio extra vergine di oliva", "sale"),
            nutritionalValues = listOf(
                "Calorie" to "470",
                "Proteine" to "8,3g",
                "Carboidrati" to "67,8g",
                "Zuccheri" to "1,8g",
                "Grassi" to "18,1g",
                "Sale" to "0,00g"
            )
        ),
        Product(
            id = 5,
            name = "Succo alla pera",
            description = "Succo alla pera marca Yoga",
            price = 0.7,
            ingredients = listOf("pere", "zucchero", "acqua", "limoni"),
            nutritionalValues = listOf(
                "Calorie" to "57",
                "Proteine" to "o,2g",
                "Carboidrati" to "13,5g",
                "Zuccheri" to "12,60g",
                "Grassi" to "0,1g",
                "Sale" to "0,00g"
            )
        ),
        Product(
            id = 6,
            name = "Kitkat",
            description = "Kitkat gusto classico",
            price = 1.0,
            ingredients = listOf(
                "pasta di cacao",
                "zucchero",
                "farina di frumento",
                "grassi vegetali",
                "burro anidro",
                "burro di cacao"
            ),
            nutritionalValues = listOf(
                "Calorie" to "518",
                "Proteine" to "7g",
                "Carboidrati" to "65g",
                "Zuccheri" to "49g",
                "Grassi" to "26g",
                "Sale" to "0,00",
                "Caffeina" to "14mg"
            )
        ),
        Product(
            id = 7,
            name = "the gusto pesca",
            description = "the freddo al gusto pesca",
            price = 0.8,
            ingredients = listOf(
                "Acqua", "Pesche gialle", "Zucchero", "Succo di limone", "Menta", "Tè verde"
            ),
            nutritionalValues = listOf(
                "Calorie" to "24",
                "Proteine" to "0,00g",
                "Carboidrati" to "5,8g",
                "Zuccheri" to "5,8g",
                "Grassi" to "0,00g",
                "Sale" to "0,00"
            )
        ),
        Product(
            id = 8,
            name = "Tramezzino",
            description = "Tramezzino farcito",

            price = 1.2,
            ingredients = listOf("pane", "prosciutto", "insalata", "formaggio"),
            nutritionalValues = listOf(
                "Calorie" to "234",
                "Proteine" to "10,9g",
                "Carboidrati" to "23,2g",
                "Zuccheri" to "3,00g",
                "Grassi" to "10,7g",
                "Sale" to "1,3g"
            )
        ),
        Product(
            id = 9,
            name = "Crostini",
            description = "Crostini san carlo dorati",
            price = 0.9,
            ingredients = listOf("farina", "olio vegetale", "sale", "lievito"),
            nutritionalValues = listOf(
                "Calorie" to "556",
                "Proteine" to "8,00g",
                "Carboidrati" to "51g",
                "Zuccheri" to "1,8g",
                "Grassi" to "35g",

                "Sale" to "0,00g"
            )
        ),
        Product(
            id = 10,
            name = "Ginseng",
            description = "Caffè di ginseng",
            price = 0.6,
            ingredients = listOf(
                "estratto di ginseng", "crema di latte", "caffè istantaneo", "zucchero"
            ),
            nutritionalValues = listOf(
                "Calorie" to "25",
                "Proteine" to "0,00g",
                "Carboidrati" to "5,00g",
                "Zuccheri" to "4,1g",
                "Grassi" to "0,5g",
                "Sale" to "0,08g"
            )
        ),
        Product(
            id = 11,
            name = "Acqua naturale",
            description = "Bottiglia di acqua naturale da 0.5l",
            price = 0.5,
            ingredients = listOf("Acqua"),
            nutritionalValues = listOf(),
        ),
        Product(
            id = 12,
            name = "Acqua frizzante",
            description = "Bottiglia di acqua leggermente gassata da 0.5l",
            price = 0.5,
            ingredients = listOf("Acqua"),
            nutritionalValues = listOf(),
        ),
        Product(
            id = 13,
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