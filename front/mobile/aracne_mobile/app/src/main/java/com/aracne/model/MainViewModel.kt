package com.aracne.model

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aracne.data.model.Cantidad
import com.aracne.data.model.Detalle
import com.aracne.data.model.GeneralResponseSuccess
import com.aracne.data.model.Password
import com.aracne.data.model.Product
import com.aracne.data.model.ProductInCart
import com.aracne.data.model.Purchase
import com.aracne.data.model.Talla
import com.aracne.data.repository.ShopRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val shopRepository: ShopRepository
) : ViewModel() {

    private val idCliente: Long = 1
    val nameCliente: String = "Carla"
    var productos by mutableStateOf(listOf<Product>())
    var product by mutableStateOf(Product(0, "",  0.0, 0, 0, 0, 0, ""))
    var carrito by mutableStateOf(listOf<ProductInCart>())
    var pedidos by mutableStateOf(listOf<Purchase>())
    var grupoProductosActuales by mutableIntStateOf(1)
    var total by mutableIntStateOf(0)

    suspend fun changePassword(password: Password): GeneralResponseSuccess? {
        try {
            return shopRepository.changePassword(idCliente, password)
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
        return null
    }

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
        val df = DecimalFormat("#.##")

        for (item in carrito){
            totalCarrito += (item.producto?.price ?: 0.0) * item.cantidad
        }
        return (df.format(totalCarrito)).toDouble()
    }

    fun addToCart(idProducto: Long, producto: ProductInCart) {
        viewModelScope.launch {
            try {
                val respuesta = shopRepository.addProductToCart(idCliente, idProducto, producto)
                if(respuesta != null){
                    carrito += respuesta
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    fun getProductInCart(id: Long): ProductInCart{
        return carrito.filter { item -> item.id == id }[0]
    }

    fun updateTallaProductInCart(id: Long, tallaAnterior: String, talla: String){
        viewModelScope.launch {
            try {
                carrito[carrito.indexOf(carrito.find { item -> item.id == id })].talla = talla
                shopRepository.updateTallaProductInCart(id, Talla(tallaAnterior, talla))
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    fun updateCantidadProductInCart(id: Long, cantidadAnterior: Int, cantidad: Int){
        viewModelScope.launch {
            try {
                carrito[carrito.indexOf(carrito.find { item -> item.id == id })].cantidad = cantidad
                shopRepository.updateCantidadProductInCart(id, Cantidad(cantidadAnterior, cantidad))
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    fun updateProductInCart(id: Long, cantidadAnterior: Int, cantidad: Int, tallaAnterior: String, talla: String){
        viewModelScope.launch {
            try {
                carrito[carrito.indexOf(carrito.find { item -> item.id == id })].talla = talla
                carrito[carrito.indexOf(carrito.find { item -> item.id == id })].cantidad = cantidad
                shopRepository.updateProductInCart(id, Detalle(cantidadAnterior, cantidad, tallaAnterior, talla))
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    fun deleteProductFromCart(id: Long){
        viewModelScope.launch {
            try {
                shopRepository.deleteProductFromCart(id)
                carrito = carrito.filterNot { item -> item.id == id }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    fun makePurchase(){
        viewModelScope.launch {
            try {
                val respuesta = shopRepository.makePurchase(idCliente)
                if (respuesta != null) {
                    pedidos += respuesta
                    carrito = carrito.drop(carrito.size)
                } else {
                    println("Error.")
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    fun emptyCart(){
        viewModelScope.launch {
            try {
                val respuesta = shopRepository.emptyCart(idCliente)
                if (respuesta != null) {
                    if(respuesta.success){
                        println("El carrito se ha vaciado con éxito.")
                        carrito = carrito.drop(carrito.size)
                    }
                } else {
                    println("Error.")
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }
}


