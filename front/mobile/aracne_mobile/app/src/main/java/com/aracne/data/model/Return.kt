package com.aracne.data.model

import java.util.Date

data class Return (
    val id_devolucion: Long,
    val id_pedido: Long,
    val fechaDevolucion: String,
    val motivoDevolucion: String,
    val aceptada: Boolean?,
    val productosDevueltos: List<ReturnedProduct>
)