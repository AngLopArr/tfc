package com.aracne.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * RetrofitInstance es un singleton que configura y gestiona una instancia de Retrofit
 * para poder hacer solicitudes HTTP a una API. La función principal de esta es asegurarse
 * de que todas las solicitudes incluyan un token de autentificación en las cabeceras.
 *
 * private const val API_URL es la URL de la API que vamos a utilizar.
 *
 * private val client = OkHttpClient.Builder() -> Se crea una instancia de OkHttpClient
 * para poder configurar un cliente HTTP personalizaddo. En este caso lo configuramos con
 * tiempos de espera y un interceptor para poder agregar el token de autentificación.
 *
 * @throws Exception esta excepción salta si ocurre un error en la creación del cliete o el
 * interceptor.
 *
 * @return retorna un objeto OkHttpClient configurado con los tiempos de espera y el interceptor.
 *
 * .connectTimeout(10, TimeUnit.SECONDS) -> Tiempo máximo de conexión
 * .readTimeout(30, TimeUnit.SECONDS) -> Tiempo máximo de lectura
 * .writeTimeout(15, TimeUnit.SECONDS)-> Tiempo máximo de escritura
 *
 * val token = SessionManager.bearerToken Este se usa para autenticar al usuario que está
 * haciendo la solicitud a la API.
 *
 * if (token != null) { Aquí verifica si el token existe. Cuando el token no es nulo,
 * se añade el encabezado de autorización. Si el token es nulo, no se añade el encabezado
 * de autorización.
 *
 * chain.proceed(requestBuilder.build()) -> Construye la solicitud modificada con requestBuilder.build()
 * y se pasa al siguiente eslabón en la cadena de ejecución (chain.proceed). Este metodo envía la solicitud al
 * servidor y devuelve la respuesta.
 *
 * }.build() -> Se completa la configuración del cliente con build(), que devuelve una instancia inmutable
 * de OkHttpClient con el interceptor configurado.
 *
 *
 * val api_laboratorio: FakeStoreService by lazy { -> Instancia de Retrofit para interactuar con la API.
 * Se inicializa el lazy para evitar la creación innecesaria de la instancia.
 *  .baseUrl(API_URL) -> Define la URL base de la API
 *  .client(client) -> Asigna el cliente HTTP configurado
 *  .create(FakeStoreService::class.java) -> Crea la instancia del servicio de la API
 */
object RetrofitInstance {
    private const val API_URL = "http://10.0.2.2:8080/aracne/"

    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .addInterceptor {
                chain ->
            val requestBuilder = chain.request().newBuilder()
            chain.proceed(requestBuilder.build())
        }.build()

    val api_shop: ShopService by lazy{
        Retrofit.Builder()
            .baseUrl(API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ShopService::class.java)
    }
}