package com.aracne.data.model

import java.util.Date

data class ProductInCart(
    val id: Long,
    val cliente: Client,
    val producto: Product,
    val talla: String,
    val cantidad: Int,
    val fechaAgregado: Date
)
