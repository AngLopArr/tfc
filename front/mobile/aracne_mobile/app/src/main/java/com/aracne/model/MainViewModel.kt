package com.aracne.model

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aracne.data.model.Product
import com.aracne.data.model.ProductInCart
import com.aracne.data.repository.ShopRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val shopRepository: ShopRepository
) : ViewModel() {

    private val idCliente: Long = 1
    var productos by mutableStateOf(listOf<Product>())
    var product by mutableStateOf(Product(0, "",  0.0, 0, 0, 0, 0, ""))
    var carrito by mutableStateOf(listOf<ProductInCart>())
    var grupoProductosActuales by mutableIntStateOf(1)
    var total by mutableIntStateOf(0)

    fun getInitialProducts(){
        viewModelScope.launch {
            try {
                val respuesta = shopRepository.getProducts(1)
                if (respuesta != null) {
                    productos = respuesta
                } else {
                    println("Error.")
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    fun getTotal(){
        viewModelScope.launch {
            try {
                val respuesta = shopRepository.getTotal()
                if (respuesta != null) {
                    total = respuesta.total
                } else {
                    println("Error.")
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    fun addProducts() {
        viewModelScope.launch {
            try {
                grupoProductosActuales++
                val respuesta = shopRepository.getProducts(grupoProductosActuales)
                if (respuesta != null) {
                    productos += respuesta
                } else {
                    println("Error.")
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    fun searchProducts(query: String){
        viewModelScope.launch {
            try {
                val respuesta = shopRepository.getProductsByName(1, query)
                if (respuesta != null) {
                    productos = respuesta
                } else {
                    println("Error.")
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    fun getSearchedTotal(query: String){
        viewModelScope.launch {
            try {
                val respuesta = shopRepository.getTotalByName(query)
                if (respuesta != null) {
                    total = respuesta.total
                } else {
                    println("Error.")
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    fun addSearchedProducts(query: String){
        viewModelScope.launch {
            try {
                grupoProductosActuales++
                val respuesta = shopRepository.getProductsByName(grupoProductosActuales, query)
                if (respuesta != null) {
                    productos += respuesta
                } else {
                    println("Error.")
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    fun getProduct(id: Long) {
        val productoResultante = productos.find { producto -> producto.id_producto == id }
        if(productoResultante != null){
            product = productoResultante
        }
    }

    fun getCarrito(){
        viewModelScope.launch {
            try {
                val respuesta = shopRepository.getProductsInCart(idCliente)
                if (respuesta != null) {
                    carrito = respuesta
                } else {
                    println("Error.")
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    fun getTotalCarrito(): Double {
        var totalCarrito = 0.0
        for (item in carrito){
            totalCarrito += item.producto?.price ?: 0.0
        }
        return totalCarrito
    }

    fun addToCart(idProducto: Long, producto: ProductInCart) {
        viewModelScope.launch {
            try {
                shopRepository.addProductToCart(idCliente, idProducto, producto)
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }
}


