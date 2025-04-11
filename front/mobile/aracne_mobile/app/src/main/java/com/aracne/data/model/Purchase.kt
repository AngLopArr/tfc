package com.aracne.data.model

import java.util.Date

data class Purchase (
    val id_pedido: Long,
    val cliente: Client,
    val fechaPedido: Date,
    val totalPedido: Double,
    val productos: List<Product>
)