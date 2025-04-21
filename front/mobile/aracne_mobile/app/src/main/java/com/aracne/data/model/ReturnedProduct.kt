package com.aracne.data.model

data class ReturnedProduct(
    val id: Long,
    val producto: Product,
    val cantidad: Int,
    val talla: String
)
