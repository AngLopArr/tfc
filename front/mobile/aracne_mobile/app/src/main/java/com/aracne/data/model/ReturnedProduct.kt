package com.aracne.data.model

data class ReturnedProduct(
    val id: Long,
    val producto: Product,
    val cantidadDevuelta: Int,
    val talla: String
)
