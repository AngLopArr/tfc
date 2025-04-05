package com.aracne.ui.components

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aracne.R
import com.aracne.model.MainViewModel
import com.aracne.ui.navigation.Destinations

/**
 * Composable que determina el aspecto del top app bar de la aplicación. Este presenta, en las pantallas principales, un botón para cerrar
 * sesión en la aplicación, en pantallas secundarias, un botón para retroceder a la pantalla anterior, en todas las pantallas, el logo de la
 * U-tad, y, en todas las pantallas si el usuario es de tipo USER, un botón que permite abrir el menú de notificaciones del usuario.
 *
 * Variables:
 * - contexto: variable que contiene el contexto de la aplicación. Necesario para iniciar el LoginActivity, es decir, para realizar el cierre
 *   de sesión.
 *
 * Composables:
 * - CenterAlignedTopAppBar: Composable que establece la distribución de los diferentes elementos que se introduzcan en el top bar de la aplicación.
 *   En este caso, la alineación de los elementos será central.
 *   Dentro de este composable tendremos diferentes elementos dependiendo del usuario activo de la aplicación y la pantalla en la que este se
 *   encuentre:
 *   · En caso de que el usuario se encuentre en una de las pantallas principales, es decir, aquellas accesibles a través del bottom navigation bar,
 *     en el sector izquierdo del top app bar, se mostrará un botón que leerá 'LOG OUT', que permitirá al usuario cerrar sesión y volver a la pantalla
 *     de acceso.
 *   · En caso de que el usuario se encuentre en una de las pantallas secundarias, es decir, aquellas no accesibles a través del bottom navigation bar,
 *     es decir, accesibles a través de otros elementos de la aplicación, en lugar de mostrarse el botón 'LOG OUT' se mostrará un botón que presenta un
 *     icono de una flecha que permitirá al usuario volver a la pantalla anterior a la actual.
 *   · En caso de que el usuario sea de tipo USER, se encuentre en la pantalla que se encuentre, tendrá en la sección derecha del top app bar un botón
 *     que presenta un icono de una campana que, al clicarlo, desplegará el menú de notificaciones del usuario. Esta campana mostrará en su interior el
 *     número correspondiente a las notificaciones que tiene el usuario y estará vacía en caso de que este no tenga ninguna.
 *   Y tendremos un elemento común, el título:
 *   · Una imagen que presenta el logo de la U-tad y que se sitúa en el centro del top app bar.
 *
 * @param onNotificationButtonClick: función que, al ser ejecutada, abre el menú de notificaciones del usuario. Esta función se ejecuta cuando el
 * usuario clica en la campana que presenta el top app bar del usuario USER.
 * @param mainViewModel: instancia de la clase MainViewModel. Contiene el estado y los datos propios de la aplicación. En este caso proporciona las
 * notificaciones del usuario y el método mostrarFormulario() (este método se utiliza cada vez que el usuario utiliza el botón para retroceder a la
 * pantalla anterior, ya que, en caso de que esta pantalla sea la correspondiente al formulario para realizar un préstamo, el valor del atributo
 * formulario_prestamo deberá volverse false al salir del mismo, ya que, si esto no se hiciera y el usuario accediese a la pantalla de detalles
 * de un material, se mostraría el formulario de préstamo y esto no debería pasar)
 * @param navController: objeto de la clase NavController encargado de coordinar la navegación de la aplicación. Cuando el usuario quiera volver a
 * la pantalla anterior encontrándose en una pantalla secundaria y le de al botón correspondiente al retroceso, aquel que presenta un icono de una
 * flecha en la sección izquierda del top app bar, la función popStackBack del navController, le redigirá a la pantalla anterior.
 * @param currentScreen: parámetro que contiene el valor de la ruta actual del usuario, es decir, la pantalla en la que se encuentra. Este valor se
 * utiliza para determinar qué botón ha de mostrarse en la sección izquierda del top app bar.
 * @param onClickLogOut: función que permite al usuario cerrar sesión y volver a la pantalla de acceso. Requiere de un intent que indique la actividad
 * que deberá iniciarse al ejecutar dicha función, en este caso, LoginActivity.
 *
 * Métodos
 * .getNotificaciones -> Función que obtiene las notificaciones para el usuario. Esta función se ejecuta dentro del ámbito de `viewModelScope` para garantizar que
 * la operación se ejecute de manera asíncrona y esté vinculada al ciclo de vida del ViewModel.
 * .popBackStack() -> Es un metodo de Navigation que se usa para volver a la pantalla anterior en la pila de navegación.
 * .logout() -> Llama al repositorio para realizar el logout y actualiza el estado.
 *
 * Códgio
 *  Primer if
 *  if(currentScreen != Destinations.INCIDENCIAS && currentScreen != Destinations.CATEGORIAS && currentScreen != Destinations.PRESTAMOS){ -> Se verifica si la pantalla
 *  actual no es ninguna de las pantallas específicas (INCIDENCIAS, CATEGORIAS, PRESTAMOS). Si la pantalla no es ninguna de las mencionadas, se muestra un botón con un
 *  ícono de retroceso. Al hacer clic en el botón, se llama a popBackStack() para navegar a la pantalla anterior.
 *  else { -> Si la pantalla es alguna de las especificadas (INCIDENCIAS, CATEGORIAS, PRESTAMOS), se muestra un botón de "LOG OUT".
 *  Al hacer clic en el botón de "LOG OUT", se ejecuta la función de logout del ViewModel. Luego, se crea un Intent para redirigir al usuario a la pantalla de inicio de sesión.
 *
 * Segundo if
 * if (mainViewModel.role.collectAsState().value == stringResource(id = R.string.user)) { -> Se verifica si el rol del usuario es "user". Si el rol es "user", se muestra un botón
 * de notificaciones.
 * if (mainViewModel.notificaciones.isNotEmpty()) { -> Se verifica si el usuario tiene notificaciones pendientes. Si el usuario tiene notificaciones, se muestra un badge encima del ícono.
 *
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(mainViewModel: MainViewModel = hiltViewModel(), navController: NavController, currentScreen: String) {
    /*mainViewModel.getNotificaciones()*/
    CenterAlignedTopAppBar(
        title = {
            Image(painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(id = R.string.app_name),
            modifier = Modifier.size(60.dp).padding(PaddingValues(0.dp, 6.dp, 0.dp, 6.dp)))
        },
        navigationIcon = {
            if(currentScreen != Destinations.PRODUCTOS && currentScreen != Destinations.CART && currentScreen != Destinations.PROFILE){
                Box(modifier = Modifier.padding(PaddingValues(7.dp, 0.dp, 0.dp, 0.dp)).clickable {
                        /*navController.popBackStack()*/
                    }
                ){
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Fecha para volver",
                        tint = Color.White,
                        modifier = Modifier.size(33.dp).background(colorResource(id = R.color.purple_001), shape = CircleShape).padding(6.dp)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = colorResource(R.color.purple_002))
    )
}
