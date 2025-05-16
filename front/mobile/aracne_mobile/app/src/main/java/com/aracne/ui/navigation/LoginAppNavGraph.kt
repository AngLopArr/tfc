package com.aracne.ui.navigation

import android.content.Intent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.aracne.model.MainViewModel
import com.aracne.ui.screens.ChangePasswordScreen
import com.aracne.ui.screens.LoginScreen
import com.aracne.ui.screens.ModifyProductInCartScreen
import com.aracne.ui.screens.ProductScreen
import com.aracne.ui.screens.ProductsScreen
import com.aracne.ui.screens.ProfileScreen
import com.aracne.ui.screens.PurchasesScreen
import com.aracne.ui.screens.RegistryScreen
import com.aracne.ui.screens.ReturnsScreen
import com.aracne.ui.screens.ShoppingCartScreen

@Composable
fun LoginAppNavGraph(navController: NavHostController, innerPadding: PaddingValues, login: (Intent) -> Unit, mainViewModel: MainViewModel = hiltViewModel()){
    NavHost(navController = navController, startDestination = Destinations.LOGIN, modifier = Modifier.padding(innerPadding)){
        composable(Destinations.LOGIN) {
            LoginScreen(mainViewModel, navController, login)
        }
        composable(Destinations.REGISTRY) {
            RegistryScreen(mainViewModel, navController)
        }
    }
}
