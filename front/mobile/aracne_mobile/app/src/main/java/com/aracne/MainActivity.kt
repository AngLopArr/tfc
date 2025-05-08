package com.aracne

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.aracne.ui.components.CustomBottomNavBar
import com.aracne.ui.components.CustomTopAppBar
import com.aracne.ui.navigation.AppNavGraph
import com.aracne.ui.navigation.Destinations
import com.aracne.ui.theme.AracneTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AracneTheme {
                val navController = rememberNavController()
                MainApp(navController)
            }
        }
    }
}

@Composable
fun MainApp(navController: NavHostController/*, mainViewModel: MainViewModel = hiltViewModel(), onClickLogOut: (Intent) -> Unit*/){
    val currentScreen: MutableState<String> = remember { mutableStateOf(Destinations.PRODUCTOS) }
    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            currentScreen.value = destination.route ?: Destinations.PRODUCTOS
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { CustomTopAppBar(
                navController = navController,
                currentScreen = currentScreen.value
            )
        },
        bottomBar = {
            CustomBottomNavBar(
                onClickNavMain = { navController.navigate(Destinations.PRODUCTOS) },
                onClickNavSecond = { navController.navigate(Destinations.CART) },
                onClickNavThird = { navController.navigate(Destinations.PROFILE) },
                currentScreen = currentScreen.value
            )
        }
    ) { innerPadding -> AppNavGraph(navController, innerPadding) }
}