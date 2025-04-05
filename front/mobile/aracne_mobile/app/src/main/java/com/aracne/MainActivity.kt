package com.aracne

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.zxing.integration.android.IntentIntegrator
import com.aracne.model.MainViewModel
import com.aracne.ui.components.CustomBottomNavBar
import com.aracne.ui.components.CustomTopAppBar
import com.aracne.ui.components.NotificationsDrawerContent
import com.aracne.ui.navigation.AppNavGraph
import com.aracne.ui.navigation.Destinations
import com.aracne.ui.theme.AracneTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Clase que maneja el ciclo de vida y configura el contenido visual de la sección principal de la aplicación.
 *
 * Variables:
 *   · navController: instancia de la clase NavController encargada de coordinar la navegación de la aplicación.
 * - Si el usuario activo es USER, tenemos otras dos variables:
 *   · drawerState: variable encargada de almacenar el estado del menú de notificaciones del usuario (desplegado o no)
 *   · scope: instancia de CoroutineScope que nos permite crear una corrutina aislada de la actividad principal encargada, en este caso,
 *     de desplegar el menú de las notificaciones del usuario.
 *
 * Composables:
 * - Si el usuario activo es alumno,
 *   · ModalNavigationDrawer: este composable se encarga de crear un menú de navegación desplegable que, en nuestro caso, hemos usado como
 *     un menú para mostrarle al usuario sus notificaciones. El contenido de este composable ha de ser el contenido general que queremos que
 *     se muestre en la aplicación, en este caso, el composable App.
 *   · App: composable contenido por el anterior, define el contenido que va a visualizar el usuario alumno. Hemos de pasarle seis parámetros:
 *     - navController: instancia de la clase NavController encargada de coordinar la navegación de la aplicación.
 *     - mainViewModel: instancia de la clase MainViewModel. Esta contiene el estado y todos los datos propios de la aplicación.
 *     - mainScreen: ruta de la pantalla principal del usuario alumno, siendo esta pantalla aquella que muestra los préstamos activos del usuario.
 *     - secondaryScreen: ruta de la pantalla secundaria del usuario alumno, siendo esta pantalla aquella que muestra las diferentes categorias
 *       de materiales presentes en el inventario.
 *     - onNotificationsButtonClick: función que modifica el estado de la variable drawerState de tal manera que, cuando se clique en el elemento
 *       que tome esta función, el menú de notificaciones se desplegará. Para volver a cerrarlo el usuario simplemente deberá clicar fuera del menú
 *       (este comportamiento se encuentra definido por defecto en el composable ModalNavigationDrawer).
 *     - onClickLogOut: función que toma un intent (en este caso siempre va a ser un intent definido para iniciar la actividad LoginActivity) e
 *       inicia aquella actividad definido en el mismo.
 * - Si el usuario activo es profesor, dado que este no puede tener notificaciones, solo cuenta con el composable App.
 *   · App: composable que define el contenido que va a visualizar el usuario profesor. Hemos de pasarle cinco parámetros:
 *     - navController: instancia de la clase NavController encargada de coordinar la navegación de la aplicación.
 *     - mainViewModel: instancia de la clase MainViewModel. Esta contiene el estado y todos los datos propios de la aplicación.
 *     - mainScreen: ruta de la pantalla principal del usuario profesor, siendo esta pantalla aquella que muestra las diferentes categorias
 *       de materiales presentes en el inventario.
 *     - secondaryScreen: ruta de la pantalla secundaria del usuario alumno, siendo esta pantalla aquella que muestra las incidencias activas a
 *       revisar por los administradores (coordinadores o profesores con permisos).
 *     - onClickLogOut: función que toma un intent (en este caso siempre va a ser un intent definido para iniciar la actividad LoginActivity) e
 *       inicia aquella actividad definido en el mismo.
 *
 */
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