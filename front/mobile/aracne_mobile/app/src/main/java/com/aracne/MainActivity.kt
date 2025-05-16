package com.aracne

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.aracne.di.dataStore
import com.aracne.model.MainViewModel
import com.aracne.ui.components.CustomBottomNavBar
import com.aracne.ui.components.CustomTopAppBar
import com.aracne.ui.navigation.AppNavGraph
import com.aracne.ui.navigation.Destinations
import com.aracne.ui.theme.AracneTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.math.log

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AracneTheme {
                val navController = rememberNavController()
                val mainViewModel: MainViewModel = hiltViewModel()
                LaunchedEffect (Unit) {
                    lifecycleScope.launch {
                        val userId = mainViewModel.getId()
                        val name = mainViewModel.getName()

                        if (userId == 0L && name == "") {
                            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                            finish()
                        } else {
                            setContent {
                                MainApp(navController) { intent -> startActivity(intent) }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainApp(navController: NavHostController, logout: (Intent) -> Unit){
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
    ) { innerPadding -> AppNavGraph(navController, innerPadding, logout) }
}