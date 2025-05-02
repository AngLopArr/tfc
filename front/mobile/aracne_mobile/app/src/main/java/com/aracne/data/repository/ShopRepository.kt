package com.aracne.data.repository

import com.aracne.data.model.Cantidad
import com.aracne.data.model.Client
import com.aracne.data.model.Detalle
import com.aracne.data.model.GeneralResponseExists
import com.aracne.data.model.GeneralResponseSuccess
import com.aracne.data.model.Password
import com.aracne.data.model.Product
import com.aracne.data.model.ProductInCart
import com.aracne.data.model.Purchase
import com.aracne.data.model.PurchasedProduct
import com.aracne.data.model.Return
import com.aracne.data.model.Talla
import com.aracne.data.model.Total
import com.aracne.data.remote.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.http.Body
import retrofit2.http.Path

class ShopRepository {
    suspend fun usernameExists(username: String): GeneralResponseExists? {
        return withContext(Dispatchers.IO) {
            val response = RetrofitInstance.api_shop.usernameExists(username).execute()
            if(response.isSuccessful){
                response.body()
            } else{
                null
            }
        }
    }

    suspend fun emailExists(email: String): GeneralResponseExists? {
        return withContext(Dispatchers.IO) {
            val response = RetrofitInstance.api_shop.emailExists(email).execute()
            if(response.isSuccessful){
                response.body()
            } else{
                null
            }
        }
    }

    suspend fun login(cliente: Client): Client? {
        return withContext(Dispatchers.IO) {
            val response = RetrofitInstance.api_shop.login(cliente).execute()
            if(response.isSuccessful){
                response.body()
            }else{
                null
            }
        }
    }

    suspend fun register(cliente: Client): GeneralResponseSuccess? {
        return withContext(Dispatchers.IO) {
            val response = RetrofitInstance.api_shop.register(cliente).execute()
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
    }

    suspend fun changePassword(id: Long, password: Password): GeneralResponseSuccess? {
        return withContext(Dispatchers.IO) {
            val response = RetrofitInstance.api_shop.changePassword(id, password).execute()
            if(response.isSuccessful){
                response.body()
            }else{
                null
            }
        }
    }

    suspend fun deleteCliente(id: Long): GeneralResponseSuccess? {
        return withContext(Dispatchers.IO) {
            val response = RetrofitInstance.api_shop.deleteCliente(id).execute()
            if(response.isSuccessful){
                response.body()
            }else{
                null
            }
        }
    }

    suspend fun getProducts(group: Int): List<Product>? {
        return withContext(Dispatchers.IO) {
            val response = RetrofitInstance.api_shop.getProducts(group).execute()
            if(response.isSuccessful){
                response.body()
            }else{
                null
            }
        }
    }

    suspend fun getProductsByName(group: Int, name: String): List<Product>? {
        return withContext(Dispatchers.IO) {
            val response = RetrofitInstance.api_shop.getProductsByName(group, name).execute()
            if(response.isSuccessful){
                response.body()
            }else{
                null
            }
        }
    }

    suspend fun getTotal(): Total? {
        return withContext(Dispatchers.IO) {
            val response = RetrofitInstance.api_shop.getTotal().execute()
            if(response.isSuccessful){
                response.body()
            }else{
                null
            }
        }
    }

    suspend fun getTotalByName(name: String): Total? {
        return withContext(Dispatchers.IO) {
            val response = RetrofitInstance.api_shop.getTotalByName(name).execute()
            if(response.isSuccessful){
                response.body()
            }else{
                null
            }
        }
    }

    suspend fun getProductsInCart(id: Long): List<ProductInCart>? {
        return withContext(Dispatchers.IO) {
            val response = RetrofitInstance.api_shop.getProductsInCart(id).execute()
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
    }

    suspend fun addProductToCart(idCliente: Long, idProducto: Long, productoCarrito: ProductInCart): ProductInCart? {
        return withContext(Dispatchers.IO) {
            val response = RetrofitInstance.api_shop.addProductToCart(idCliente, idProducto, productoCarrito).execute()
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
    }

    suspend fun updateTallaProductInCart(id: Long, talla: Talla): GeneralResponseSuccess? {
        return withContext(Dispatchers.IO) {
            val response = RetrofitInstance.api_shop.updateTallaProductInCart(id, talla).execute()
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
    }

    suspend fun updateCantidadProductInCart(id: Long, cantidad: Cantidad): GeneralResponseSuccess? {
        return withContext(Dispatchers.IO) {
            val response = RetrofitInstance.api_shop.updateCantidadProductInCart(id, cantidad).execute()
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
    }

    suspend fun updateProductInCart(id: Long, detalle: Detalle): GeneralResponseSuccess? {
        return withContext(Dispatchers.IO) {
            val response = RetrofitInstance.api_shop.updateProductInCart(id, detalle).execute()
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
    }

    suspend fun deleteProductFromCart(id: Long): GeneralResponseSuccess? {
        return withContext(Dispatchers.IO) {
            val response = RetrofitInstance.api_shop.deleteProductFromCart(id).execute()
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
    }

    suspend fun emptyCart(id: Long): GeneralResponseSuccess? {
        return withContext(Dispatchers.IO) {
            val response = RetrofitInstance.api_shop.emptyCart(id).execute()
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
    }

    suspend fun makePurchase(id: Long): Purchase? {
        return withContext(Dispatchers.IO) {
            val response = RetrofitInstance.api_shop.makePurchase(id).execute()
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
    }

    suspend fun makeReturn(idCliente: Long, idPedido: Long, productos: List<PurchasedProduct>): Return? {
        return withContext(Dispatchers.IO) {
            val response = RetrofitInstance.api_shop.makeReturn(idCliente, idPedido, productos).execute()
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
    }
}