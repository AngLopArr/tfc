package com.aracne.ui.navigation

import android.content.Intent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import com.aracne.ui.screens.PurchasesScreen
import com.aracne.ui.screens.ReturnsScreen
import com.aracne.ui.screens.ShoppingCartScreen

@Composable
fun AppNavGraph(navController: NavHostController, innerPadding: PaddingValues, logout: (Intent) -> Unit, mainViewModel: MainViewModel){
    mainViewModel.getPurchases()
    mainViewModel.getReturns()
    NavHost(navController = navController, startDestination = Destinations.PRODUCTOS, modifier = Modifier.padding(innerPadding)){
        composable(Destinations.PRODUCTOS) {
            ProductsScreen(navController, mainViewModel)
        }
        composable(Destinations.CART) {
            ShoppingCartScreen(mainViewModel, navController)
        }
        composable(Destinations.PROFILE) {
            ProfileScreen(navController, mainViewModel, logout)
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
            ChangePasswordScreen(mainViewModel)
        }
        composable(route = Destinations.PURCHASES) {
            PurchasesScreen(mainViewModel, navController)
        }
        composable(route = Destinations.RETURNS) {
            ReturnsScreen(mainViewModel)
        }
    }
}
