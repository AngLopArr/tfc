package com.aracne.data.model

data class ProductInCart(
    val id: Long?,
    val cliente: Client?,
    val producto: Product?,
    var talla: String,
    var cantidad: Int,
    val fechaAgregado: String?
)
