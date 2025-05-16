package com.aracne.model

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aracne.data.model.Cantidad
import com.aracne.data.model.Client
import com.aracne.data.model.Detalle
import com.aracne.data.model.GeneralResponseSuccess
import com.aracne.data.model.Password
import com.aracne.data.model.Product
import com.aracne.data.model.ProductInCart
import com.aracne.data.model.Purchase
import com.aracne.data.model.PurchasedProduct
import com.aracne.data.model.Return
import com.aracne.data.model.ReturnedProduct
import com.aracne.data.model.Talla
import com.aracne.data.repository.PreferencesRepository
import com.aracne.data.repository.ShopRepository
import com.aracne.di.dataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val shopRepository: ShopRepository,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    private val idCliente: Long = 1
    val nameCliente: String = "Carla"
    var productos by mutableStateOf(listOf<Product>())
    var product by mutableStateOf(Product(0, "",  0.0, 0, 0, 0, 0, ""))
    var carrito by mutableStateOf(listOf<ProductInCart>())
    var pedidos by mutableStateOf(listOf<Purchase>())
    var devoluciones by mutableStateOf(listOf<Return>())
    var grupoProductosActuales by mutableIntStateOf(1)
    var total by mutableIntStateOf(0)

    suspend fun getId(): Long{
      return preferencesRepository.idFlow.first()
    }

    fun saveId(id: Long) {
        viewModelScope.launch {
            preferencesRepository.saveId(id)
        }
    }

    suspend fun getName(): String {
        return preferencesRepository.nameFlow.first()
    }

    fun saveName(name: String) {
        viewModelScope.launch {
            preferencesRepository.saveName(name)
        }
    }

    suspend fun getLoggedIn(): Boolean {
        return preferencesRepository.loggedInFlow.first()
    }

    fun saveLoggedIn(loggedin: Boolean) {
        viewModelScope.launch {
            preferencesRepository.saveLoggedIn(loggedin)
        }
    }

    suspend fun changePassword(password: Password): GeneralResponseSuccess? {
        try {
            return shopRepository.changePassword(idCliente, password)
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
        return null
    }

    fun deleteClient() {
        viewModelScope.launch {
            try {
                shopRepository.deleteCliente(idCliente)
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
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

    suspend fun addToCart(idProducto: Long, producto: ProductInCart): ProductInCart? {
        try {
            val respuesta = shopRepository.addProductToCart(idCliente, idProducto, producto)
            if(respuesta != null){
                carrito += respuesta
                return respuesta
            }
            else {
                return null
            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
        return null
    }

    fun getProductInCart(id: Long): ProductInCart{
        return carrito.filter { item -> item.id == id }[0]
    }

    suspend fun updateTallaProductInCart(id: Long, tallaAnterior: String, talla: String): GeneralResponseSuccess? {
        try {
            val respuesta = shopRepository.updateTallaProductInCart(id, Talla(tallaAnterior, talla))
            if(respuesta != null && respuesta.success){
                carrito[carrito.indexOf(carrito.find { item -> item.id == id })].talla = talla
            }
            return respuesta
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
        return null
    }

    suspend fun updateCantidadProductInCart(id: Long, cantidadAnterior: Int, cantidad: Int): GeneralResponseSuccess? {
        try {
            val respuesta = shopRepository.updateCantidadProductInCart(id, Cantidad(cantidadAnterior, cantidad))
            if(respuesta != null && respuesta.success){
                carrito[carrito.indexOf(carrito.find { item -> item.id == id })].cantidad = cantidad
            }
            return respuesta
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
        return null
    }

    suspend fun updateProductInCart(id: Long, cantidadAnterior: Int, cantidad: Int, tallaAnterior: String, talla: String): GeneralResponseSuccess? {
        try {
            val respuesta = shopRepository.updateProductInCart(id, Detalle(cantidadAnterior, cantidad, tallaAnterior, talla))
            if(respuesta != null && respuesta.success){
                carrito[carrito.indexOf(carrito.find { item -> item.id == id })].talla = talla
                carrito[carrito.indexOf(carrito.find { item -> item.id == id })].cantidad = cantidad
            }
            return respuesta
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
        return null
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

    fun getPurchases(){
        viewModelScope.launch {
            try {
                val respuesta = shopRepository.getPurchases(idCliente)
                if (respuesta != null) {
                    pedidos = respuesta
                } else {
                    println("Error.")
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    fun makeReturn(idPedido: Long, productosPedido: List<PurchasedProduct>){
        viewModelScope.launch {
            try {
                val respuesta = shopRepository.makeReturn(idCliente, idPedido, productosPedido)
                if (respuesta != null) {
                    devoluciones += respuesta
                    getPurchases()
                } else {
                    println("Error.")
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    fun prepareReturn(productosPedido: List<PurchasedProduct>): Boolean{
        return productosPedido.isNotEmpty()
    }

    fun getReturns(){
        viewModelScope.launch {
            try {
                val respuesta = shopRepository.getReturns(idCliente)
                if (respuesta != null) {
                    devoluciones = respuesta
                } else {
                    println("Error.")
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    suspend fun login(username: String = "", email: String = "", password: String): Client? {
        try {
            Log.d("TAG", "login: $username")
            val respuesta = shopRepository.login(Client(null, null, null, if (username != "") username else null, if (email != "") email else null, password))
            if(respuesta != null){
                return respuesta
            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
        return null
    }

    suspend fun register(username: String, email: String, password: String, nombre: String): GeneralResponseSuccess? {
        try {
            Log.d("TAG", "login: $username")
            val respuesta = shopRepository.register(Client(null, null, nombre, username, email, password))
            if(respuesta != null){
                return respuesta
            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
        return null
    }

    fun logout(context: Context){
        viewModelScope.launch {
            try {
                val dataStore: DataStore<Preferences> = context.dataStore
                dataStore.edit { preferences ->
                    preferences.clear()
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }
}


