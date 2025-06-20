package com.aracne

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.aracne.ui.navigation.LoginAppNavGraph
import com.aracne.ui.theme.AracneTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AracneTheme {
                val navController = rememberNavController()
                LoginApp(navController) { intent -> startActivity(intent); finish() }
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