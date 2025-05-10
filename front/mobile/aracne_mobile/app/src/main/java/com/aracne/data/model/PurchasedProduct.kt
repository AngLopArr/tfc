package com.aracne.data.model

data class PurchasedProduct(
    val id: Long,
    val producto: Product,
    val cantidad: Int,
    val talla: String,
    var devolver: Boolean?
)
