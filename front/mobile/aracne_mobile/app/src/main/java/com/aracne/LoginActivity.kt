package com.aracne

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import com.aracne.ui.screens.LoginScreen
import com.aracne.ui.theme.AracneTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Clase que maneja el ciclo de vida y configura el contenido visual de la pantalla de inicio de sesión.
 *
 * Composables:
 * - LoginScreen: Representa la pantalla de acceso y recibe un callback:
 *   - onClickAcceder: Función lambda que recibe un `Intent` y lo ejecuta con `startActivity(intent)`,
 *     permitiendo la navegación a otra actividad.
 */
@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AracneTheme {
                LoginScreen()
            }
        }
    }
}