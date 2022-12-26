package com.bitta.app.model

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val ingredients: List<String>,
    val nutritionalValues: List<Pair<String, String>>,
)

data class ReportedProduct(
    val product: Product,
    val lastReport: ProductReport?,
)