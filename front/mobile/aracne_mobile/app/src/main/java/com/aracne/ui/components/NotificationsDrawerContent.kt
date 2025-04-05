package com.aracne.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aracne.R
import com.aracne.model.MainViewModel

/**
 * Composable encargado de definir el contenido del menú de notificaciones del usuario. Este contiene un título 'Notificaciones', un divisor entre
 * el título y el resto del contenido, y una serie de tarjetas que representan cada una de las notificaciones que tenga el usuario.
 *
 * Composables:
 * - NotificationsLazyColumn: composable encargado de generar y contener cada una de las tarjetas de notificación. Recibe dos parámetros:
 *   · notifications: objeto List que contiene una serie de instancias de la data class Notification. Cada instancia representa
 *     una notificación para el usuario. La lista se toma del mainViewModel, siendo esta el contenido del atributo notificaciones
 *     de este objeto.
 *   · onClickRemoveNotification: función lambda que toma una instancia de la data class Notification y la elimina de la lista
 *     notificaciones del mainViewModel. Esta función utiliza el método removeNotification del objeto mainViewModel para cumplir
 *     dicha función. Al ser notificaciones en mainViewModel un atributo mutable, su modificación provoca la recomposición de la
 *     aplicación. Este composable, por tanto, se verá repintado y dejará de mostrar aquella notificación que se acabe de eliminar.
 *
 * @param mainViewModel: instancia de la clase MainViewModel. Contiene el estado y los datos propios de la aplicación.
 */
@Composable
fun NotificationsDrawerContent(mainViewModel: MainViewModel = hiltViewModel()){
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .widthIn(max = 300.dp)
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        CompositionLocalProvider(value = LocalLayoutDirection provides LayoutDirection.Ltr) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Notificaciones",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 13.dp),
                    color = if(isSystemInDarkTheme()) { Color.White } else { Color.Black })
                HorizontalDivider(thickness = 1.dp)
            }
        }
    }
}

/**
 * Composable encargado de contener y generar cada una de las tarjetas representativas de las notificaciones del usuario. Se encarga de
 * recorrer la lista notifications que recibe por parámetro y pasarle cada instancia al composable NotificationCardComposable, encargado
 * de generar cada tarjeta con la información de cada notificación.
 *
 * Composables:
 * - NotificationCardComposable: composable encargado de generar una tarjeta con la información de cada instancia de la data class
 *   Notification que se le pase. Recibe dos parámetros:
 *   · notification: instancia de la data class Notification que contiene la información de la notificación que el composable ha de mostrar.
 *   · onClickRemoveNotification: función que permite eliminar una notificación del sistema. Esta función se ejecuta cuando se clica la cruz
 *     presente en la parte superior derecha de la tarjeta.
 *
 * @param notifications: objeto List que contiene una serie de instancias de la data class Notification. Cada instancia representa
 * una notificación para el usuario.
 * @param onClickRemoveNotification: función que toma una instancia de la data class Notification y la elimina de la lista notificaciones
 * del mainViewModel.
 */
/*@Composable
fun NotificationsLazyColumn(notifications: List<Notification>, onClickRemoveNotification: (Notification) -> Unit){
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(notifications) { notification ->
            NotificationCardComposable (notification = notification, onClickRemoveNotification =  { onClickRemoveNotification(notification) })
        }
    }

}*/

/**
 * Composable encargado generar una tarjeta con la información de cada instancia de la data class Notification que se le pase. Este presenta
 * una sección superior que contiene el tipo de notificación que se está mostrando, por ejemplo, un aviso por retraso en la devolución de un
 * material, y una parte inferior que contiene el cuerpo de la notificación, es decir, la descripción de la misma.
 *
 * @param notification: instancia de la data class Notification que contiene la información de la notificación que el composable ha de mostrar.
 * @param onClickRemoveNotification: función que permite eliminar una notificación del sistema. Esta función se ejecuta cuando se clica la cruz
 * presente en la parte superior derecha de la tarjeta.
 */
/*@Composable
fun NotificationCardComposable(notification: Notification, onClickRemoveNotification: (Notification) -> Unit){
    Spacer(modifier = Modifier.height(13.dp))
    Card{
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 8.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        )
                    )
                    .background(color = colorResource(id = R.color.blue_200))
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = notification.tipo,
                    style = TextStyle(
                        textAlign = TextAlign.Justify,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
                Box(
                    modifier = Modifier.clickable { onClickRemoveNotification(notification) }
                )
                {
                    Icon(Icons.Filled.Close,
                        contentDescription = stringResource(id = R.string.app_name),
                        modifier = Modifier.size(18.dp),
                        tint = Color.White
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    text = notification.contenido,
                    style = TextStyle(
                        textAlign = TextAlign.Justify,
                        fontSize = 16.sp
                    )
                )
            }
        }
    }
}*/