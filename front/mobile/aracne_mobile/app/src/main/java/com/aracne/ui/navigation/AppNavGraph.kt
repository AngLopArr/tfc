package com.aracne.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.aracne.model.MainViewModel
import com.aracne.ui.screens.ChangePasswordScreen
import com.aracne.ui.screens.ModifyProductInCartScreen
import com.aracne.ui.screens.ProductScreen
import com.aracne.ui.screens.ProductsScreen
import com.aracne.ui.screens.ProfileScreen
import com.aracne.ui.screens.ShoppingCartScreen

/**
 * Composable que define el contenido y la navegación de la aplicación, es decir, cada pantalla correspondiente a cada una de las rutas establecidas
 * para esta.
 *
 * Composables:
 * - NavHostComposable: este composable recibirá siete parámetros. Su contenido dependerá del usuario activo de la aplicación.
 *   · Si el usuario es student,
 *     - navController: objeto de la clase NavController encargado de coordinar la navegación de la aplicación.
 *     - innerPadding: objeto PaddingValues que define el padding del contenido principal teniendo en cuenta el tamaño de las barras
 *       superior e inferior para evitar el solapamiento de contenido.
 *     - mainViewModel: instancia de la clase MainViewModel. Contiene el estado y los datos propios de la aplicación.
 *     - mainScreen: ruta correspondiente a la pantalla pricipal del usuario student, es decir, la pantalla correspondiente a los préstamos
 *       activos del mismo.
 *     - secondaryScreen: ruta correspondiente a la pantalla secundaria del usuario student, es decir, la pantalla que presenta las diferentes
 *       categorías de materiales en el inventario.
 *     - mainScreenComposable: composable correspondiente a la pantalla principal del usuario student, es decir, el composable que ha de mostrarse
 *       cuando se navegue a la ruta pertinente.
 *     - secondaryScreenComposable: composable correspondiente a la pantalla secundaria del usuario student, es decir, el composable que ha de
 *       mostrarse cuando se navegue a la ruta pertinente.
 *   · Si el usuario es profesor,
 *     - navController: objeto de la clase NavController encargado de coordinar la navegación de la aplicación.
 *     - innerPadding: objeto PaddingValues que define el padding del contenido principal teniendo en cuenta el tamaño de las barras
 *       superior e inferior para evitar el solapamiento de contenido.
 *     - mainViewModel: instancia de la clase MainViewModel. Contiene el estado y los datos propios de la aplicación.
 *     - mainScreen: ruta correspondiente a la pantalla pricipal del usuario profesor, es decir, la pantalla correspondiente a los préstamos
 *       activos del mismo.
 *     - secondaryScreen: ruta correspondiente a la pantalla secundaria del usuario profesor, es decir, la pantalla que presenta las diferentes
 *       categorías de materiales en el inventario.
 *     - mainScreenComposable: composable correspondiente a la pantalla principal del usuario profesor, es decir, el composable que ha de mostrarse
 *       cuando se navegue a la ruta pertinente.
 *     - secondaryScreenComposable: composable correspondiente a la pantalla secundaria del usuario profesor, es decir, el composable que ha de
 *       mostrarse cuando se navegue a la ruta pertinente.
 *
 * @param navController: objeto de la clase NavController encargado de coordinar la navegación de la aplicación.
 * @param innerPadding: objeto PaddingValues que define el padding del contenido principal teniendo en cuenta el tamaño de las barras superior
 * e inferior para evitar el solapamiento de contenido.
 * @param mainViewModel: instancia de la clase MainViewModel. Contiene el estado y los datos propios de la aplicación.
 */
@Composable
fun AppNavGraph(navController: NavHostController, innerPadding: PaddingValues, mainViewModel: MainViewModel = hiltViewModel()){
    /*LaunchedEffect(Unit) {
        mainViewModel.getCategories()
    }*/
    NavHost(navController = navController, startDestination = Destinations.PRODUCTOS, modifier = Modifier.padding(innerPadding)){
        composable(Destinations.PRODUCTOS) {
            ProductsScreen(navController, mainViewModel)
        }
        composable(Destinations.CART) {
            ShoppingCartScreen(mainViewModel, navController)
        }
        composable(Destinations.PROFILE) {
            ProfileScreen(navController, mainViewModel)
        }
        composable(route = Destinations.PRODUCTO) {
            ProductScreen(navController, mainViewModel)
        }
        composable(route = "${Destinations.MODIFYPROD}/{id_producto_carrito}", arguments = listOf(navArgument("id_producto_carrito") { type = NavType.LongType })) {
                parametros ->
            val producto = parametros.arguments?.getLong("id_producto_carrito") ?: 0
            ModifyProductInCartScreen(productoId = producto, navController = navController, mainViewModel = mainViewModel)
        }
        composable(route = Destinations.CHANGEPASSWORD) {
            ChangePasswordScreen(navController, mainViewModel)
        }
        /*
        composable(route = "${Destinatns.MATERIAL}/{id_material}", arguments = listOf(navArgument("id_material") { type = NavType.IntType })) {
                parametros ->
            val material = parametros.arguments?.getInt("id_material") ?: 1
        }
        composable(route = "${Destinations.PRESTAMO}/{id_material}", arguments = listOf(navArgument("id_material") { type = NavType.IntType })) {
                parametros ->
            val material = parametros.arguments?.getInt("id_material") ?: 1
        }
        composable(route = "${Destinations.FORMULARIO_INCIDENCIAS}/{id_prestamo}", arguments = listOf(navArgument("id_prestamo") { type = NavType.IntType })){
            parametros ->
            val idPrestamo = parametros.arguments?.getInt("id_prestamo") ?: 0
        }*/
    }
}
