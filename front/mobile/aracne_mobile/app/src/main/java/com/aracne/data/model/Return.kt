package com.aracne.data.model

import java.util.Date

data class Return (
    val id_devolucion: Long,
    val pedido: Purchase,
    val cliente: Client,
    val fechaDevolucion: Date,
    val motivoDevolucion: String,
    val aceptada: Boolean?,
    val devolverProductosACliente: Boolean?,
    val productosDevueltos: List<Product>
)