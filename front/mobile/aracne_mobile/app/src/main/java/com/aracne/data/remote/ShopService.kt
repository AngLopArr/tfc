package com.aracne.data.remote

import com.aracne.data.model.Cantidad
import com.aracne.data.model.Client
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
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ShopService {
    @GET("clientes/username/{username}")
    fun usernameExists(@Path("username") username: String): Call<GeneralResponseExists>

    @GET("clientes/email/{email}")
    fun emailExists(@Path("email") email: String): Call<GeneralResponseExists>

    @POST("clientes/login")
    fun login(@Body cliente: Client): Call<Client>

    @POST("clientes/register")
    fun register(@Body cliente: Client): Call<GeneralResponseSuccess>

    @PUT("clientes/password/{id}")
    fun changePassword(@Path("id") id: Long, @Body password: Password): Call<GeneralResponseSuccess>

    @DELETE("clientes/delete/{id}")
    fun deleteCliente(@Path("id") id: Long): Call<GeneralResponseSuccess>

    @GET("inventory/products/8/{group}")
    fun getProducts(@Path("group") group: Int): Call<List<Product>>

    @GET("inventory/search/8/{group}/{name}")
    fun getProductsByName(@Path("group") group: Int, @Path("name") name: String): Call<List<Product>>

    @GET("/inventory")
    fun getTotal(): Call<Total>

    @GET("/inventory/total/{name}")
    fun getTotalByName(@Path("name") name: String): Call<Total>

    @GET("carrito/{id}")
    fun getProductsInCart(@Path("id") id: Long): Call<List<ProductInCart>>

    @POST("carrito/add/{id_cliente}/{id_producto}")
    fun addProductToCart(@Path("id_cliente") idCliente: Long, @Path("id_producto") idProducto: Long, @Body productoCarrito: ProductInCart): Call<ProductInCart>

    @PUT("carrito/update/talla/{id}")
    fun updateTallaProductInCart(@Path("id") id: Long, @Body talla: Talla): Call<GeneralResponseSuccess>

    @PUT("carrito/update/cantidad/{id}")
    fun updateCantidadProductInCart(@Path("id") id: Long, @Body cantidad: Cantidad): Call<GeneralResponseSuccess>

    @DELETE("carrito/delete/{id}")
    fun deleteProductFromCart(@Path("id") id: Long): Call<GeneralResponseSuccess>

    @DELETE("carrito/empty/{id}")
    fun emptyCart(@Path("id") id: Long): Call<GeneralResponseSuccess>

    @POST("pedidos/create/{id}")
    fun makePurchase(@Path("id") id: Long): Call<Purchase>

    @POST("devoluciones/create/{id_cliente}/{id_pedido}")
    fun makeReturn(@Path("id_cliente") idCliente: Long, @Path("id_pedido") idPedido: Long, @Body productos: List<PurchasedProduct>): Call<Return>
}