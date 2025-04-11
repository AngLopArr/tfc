package com.aracne.data.model

data class PurchasedProduct(
    val id_producto: Long,
    val name: String,
    val price: Double,
    val s: Int,
    val m: Int,
    val l: Int,
    val xl: Int,
    val image: String,
)
