package com.aracne.data.model

data class Return (
    val id_devolucion: Long,
    val id_pedido: Long,
    val fechaDevolucion: String,
    val estado: String,
    val productosDevueltos: List<ReturnedProduct>,
    var mostrar: Boolean?
)