package com.aracne.data.model

import java.util.Date

data class ProductInCart(
    val id: Long?,
    val cliente: Client?,
    val producto: Product?,
    var talla: String,
    var cantidad: Int,
    val fechaAgregado: String?
)
