package com.aracne

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.aracne.ui.navigation.LoginAppNavGraph
import com.aracne.ui.screens.LoginScreen
import com.aracne.ui.theme.AracneTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AracneTheme {
                val navController = rememberNavController()
                LoginApp(navController) { intent -> startActivity(intent) }
            }
        }
    }
}

@Composable
fun LoginApp(navController: NavHostController, login: (Intent) -> Unit){
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding -> LoginAppNavGraph(navController, innerPadding, login) }
}