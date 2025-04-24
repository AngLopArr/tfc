package com.aracne.model

import android.util.Log
import androidx.compose.runtime.getValue
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

    fun getInitialProducts(){
        viewModelScope.launch {
            try {
                var respuesta = shopRepository.get4Products(1)
                if (respuesta != null) {
                    productos = respuesta
                    respuesta = shopRepository.get4Products(2)
                    if (respuesta != null) {
                        productos += respuesta
                        /*respuesta = shopRepository.get4Products(3)
                        if (respuesta != null) {
                            productos += respuesta.chunked(2)
                        } else {
                            println("Error.")
                        }*/
                    } else {
                        println("Error.")
                    }
                } else {
                    println("Error.")
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    fun addProducts(group: Int) {
        viewModelScope.launch {
            try {
                val respuesta = shopRepository.get4Products(group)
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

    fun addToCart(idProducto: Long, producto: ProductInCart) {
        viewModelScope.launch {
            try {
                val respuesta = shopRepository.addProductToCart(idCliente, idProducto, producto)

                if (respuesta != null) {
                    carrito += respuesta
                } else {
                    println("Error.")
                }
                Log.d("RESPUESTA", respuesta.toString())
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }
}


