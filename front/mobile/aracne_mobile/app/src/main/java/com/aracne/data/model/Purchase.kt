package com.aracne.data.model

import java.util.Date

data class Purchase (
    val id_pedido: Long,
    val fechaPedido: String,
    val totalPedido: Double,
    val estado: String,
    val productos: List<PurchasedProduct>,
    var mostrar: Boolean = false
)