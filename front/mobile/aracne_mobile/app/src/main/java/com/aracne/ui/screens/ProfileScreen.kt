package com.aracne.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.aracne.R
import com.aracne.model.MainViewModel
import com.aracne.ui.components.ShopDialog
import com.aracne.ui.navigation.Destinations

@Composable
fun ProfileScreen(navController: NavHostController, mainViewModel: MainViewModel){
    var botonDeleteClicked by remember { mutableStateOf(false) }
    var mostrarDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    LaunchedEffect(botonDeleteClicked) {
        if(botonDeleteClicked){
            mostrarDialog = true
        }
        botonDeleteClicked = false
    }

    if(mostrarDialog){
        ShopDialog({ mostrarDialog = false }, { mainViewModel.deleteClient(); mostrarDialog = false }, "Eliminar perfil", "¿Está seguro de que desea eliminar su perfil? Esta acción es irreversible")
    }

    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        Column (
            modifier = Modifier.padding(0.dp, 30.dp, 0.dp, 0.dp).height(330.dp).fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "¡Bienvenid@, " + mainViewModel.nameCliente + "!", style = TextStyle(
                fontSize = 24.sp
            ))
        }
        Column {
            Card(
                modifier = Modifier.padding(15.dp, 20.dp, 15.dp, 15.dp).fillMaxWidth().height(50.dp).clickable {
                    navController.navigate(Destinations.PURCHASES)
                },
                colors = CardColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = Color.Black,
                    disabledContainerColor = MaterialTheme.colorScheme.background,
                    disabledContentColor = Color.Black
                )
            ) {
                Row (
                    modifier = Modifier.fillMaxSize().background(
                        colorResource(R.color.purple_002)
                    ),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Pedidos realizados", style = TextStyle(
                        color = colorResource(R.color.purple_000),
                        fontSize = 16.sp
                    ))
                }
            }
            Card(
                modifier = Modifier.padding(15.dp, 0.dp, 15.dp, 15.dp).fillMaxWidth().height(50.dp).clickable {
                    navController.navigate(Destinations.RETURNS)
                },
                colors = CardColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = Color.Black,
                    disabledContainerColor = MaterialTheme.colorScheme.background,
                    disabledContentColor = Color.Black
                )
            ) {
                Row (
                    modifier = Modifier.fillMaxSize().background(
                        colorResource(R.color.purple_002)
                    ),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Devoluciones realizadas", style = TextStyle(
                        color = colorResource(R.color.purple_000),
                        fontSize = 16.sp
                    ))
                }
            }
            Card(
                modifier = Modifier.padding(15.dp, 0.dp, 15.dp, 15.dp).fillMaxWidth().height(50.dp).clickable {
                    navController.navigate(Destinations.CHANGEPASSWORD)
                },
                colors = CardColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = Color.Black,
                    disabledContainerColor = MaterialTheme.colorScheme.background,
                    disabledContentColor = Color.Black
                )
            ) {
                Row (
                    modifier = Modifier.fillMaxSize().background(
                        colorResource(R.color.purple_002)
                    ),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Cambio de contraseña", style = TextStyle(
                        color = colorResource(R.color.purple_000),
                        fontSize = 16.sp
                    ))
                }
            }
            Card(
                modifier = Modifier.padding(15.dp, 0.dp, 15.dp, 15.dp).fillMaxWidth().height(50.dp).clickable {
                    mainViewModel.logout(context)
                },
                colors = CardColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = Color.Black,
                    disabledContainerColor = MaterialTheme.colorScheme.background,
                    disabledContentColor = Color.Black
                )
            ) {
                Row (
                    modifier = Modifier.fillMaxSize().background(
                        colorResource(R.color.purple_002)
                    ),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Cerrar sesión", style = TextStyle(
                        color = colorResource(R.color.purple_000),
                        fontSize = 16.sp
                    ))
                }
            }
            Card(
                modifier = Modifier.padding(15.dp, 0.dp, 15.dp, 15.dp).fillMaxWidth().height(50.dp).clickable {
                    botonDeleteClicked = true
                },
                colors = CardColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = Color.Black,
                    disabledContainerColor = MaterialTheme.colorScheme.background,
                    disabledContentColor = Color.Black
                )
            ) {
                Row (
                    modifier = Modifier.fillMaxSize().background(
                        colorResource(R.color.purple_002)
                    ),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Eliminar cuenta", style = TextStyle(
                        color = Color.Red,
                        fontSize = 16.sp
                    ))
                }
            }
        }
    }
}