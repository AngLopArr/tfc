package com.aracne.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.aracne.R
import com.aracne.model.MainViewModel
import com.aracne.ui.components.ShopDialog
import com.aracne.ui.navigation.Destinations

@Composable
fun RegistryScreen(mainViewModel: MainViewModel, navController: NavHostController){
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repetirPassword by remember { mutableStateOf("") }
    var botonClicked by remember { mutableStateOf(false) }
    var mostrarDialog by remember { mutableStateOf(false) }
    var dialogFunction by remember { mutableStateOf({}) }
    var dialogText by remember { mutableStateOf("") }

    LaunchedEffect (botonClicked) {
        if(botonClicked){
            val emailExists = mainViewModel.emailExists(email)
            val usernameExists = mainViewModel.usernameExists(username)
            if(emailExists?.exists != true){
                if(usernameExists?.exists != true){
                    val cliente = mainViewModel.register(username = username, email = email, password = password, nombre = nombre)
                    if (cliente?.success == true){
                        email = ""
                        username = ""
                        nombre = ""
                        password = ""
                        repetirPassword = ""
                        mostrarDialog = true
                        dialogFunction = { mostrarDialog = false; navController.navigate(Destinations.LOGIN) }
                        dialogText = "El registro se ha realizado con éxito"
                    }
                    else {
                        mostrarDialog = true
                        dialogFunction = { mostrarDialog = false }
                        dialogText = "Ha ocurrido un error a la hora de gestionar el registro. Inténtelo de nuevo más tarde"
                    }
                }
                else{
                    mostrarDialog = true
                    dialogFunction = { mostrarDialog = false }
                    dialogText = "El nombre de usuario indicado ya se encuentra asociado a una cuenta"
                }
            }
            else{
                mostrarDialog = true
                dialogFunction = { mostrarDialog = false }
                dialogText = "El email indicado ya se encuentra asociado a una cuenta"
            }
        }
        botonClicked = false
    }

    if(mostrarDialog){
        ShopDialog(dialogFunction, dialogFunction, "Registro", dialogText)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.background),
                contentScale = ContentScale.FillBounds
            )
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(550.dp)
                    .padding(25.dp, 0.dp, 25.dp, 45.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(R.color.white),
                )
            ){
                Spacer(modifier = Modifier.height(30.dp))
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Text(
                        text = "Crea una cuenta",
                        style = TextStyle(
                            fontSize = 30.sp,
                            color = colorResource(R.color.purple_000),
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = { Text("Email",
                            style = TextStyle(
                                fontSize = 14.sp
                            )
                        ) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(PaddingValues(15.dp, 15.dp, 15.dp, 0.dp))
                            .border(
                                width = 5.dp,
                                color = colorResource(id = R.color.light_gray),
                                shape = RoundedCornerShape(3.5.dp)
                            )
                            .height(50.dp),
                        textStyle = TextStyle(fontSize = 14.sp),
                        colors = coloresTextFieldLogin()
                    )
                    OutlinedTextField(
                            value = username,
                    onValueChange = { username = it },
                    placeholder = { Text("Nombre de usuario",
                        style = TextStyle(
                            fontSize = 14.sp
                        )
                    ) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(PaddingValues(15.dp, 10.dp, 15.dp, 0.dp))
                        .border(
                            width = 5.dp,
                            color = colorResource(id = R.color.light_gray),
                            shape = RoundedCornerShape(3.5.dp)
                        )
                        .height(50.dp),
                    textStyle = TextStyle(fontSize = 14.sp),
                    colors = coloresTextFieldLogin()
                    )
                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        placeholder = { Text("Nombre",
                            style = TextStyle(
                                fontSize = 14.sp
                            )
                        ) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(PaddingValues(15.dp, 10.dp, 15.dp, 0.dp))
                            .border(
                                width = 5.dp,
                                color = colorResource(id = R.color.light_gray),
                                shape = RoundedCornerShape(3.5.dp)
                            )
                            .height(50.dp),
                        textStyle = TextStyle(fontSize = 14.sp),
                        colors = coloresTextFieldLogin()
                    )
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = { Text("Contraseña",
                            style = TextStyle(
                                fontSize = 14.sp
                            )
                        ) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(PaddingValues(15.dp, 10.dp, 15.dp, 0.dp))
                            .border(
                                width = 5.dp,
                                color = colorResource(id = R.color.light_gray),
                                shape = RoundedCornerShape(3.5.dp)
                            )
                            .height(50.dp),
                        textStyle = TextStyle(fontSize = 14.sp),
                        colors = coloresTextFieldLogin()
                    )
                    OutlinedTextField(
                        value = repetirPassword,
                        onValueChange = { repetirPassword = it },
                        placeholder = { Text("Repetir contraseña",
                        style = TextStyle(
                            fontSize = 14.sp
                        )
                    ) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(PaddingValues(15.dp, 10.dp, 15.dp, 0.dp))
                        .border(
                            width = 5.dp,
                            color = colorResource(id = R.color.light_gray),
                            shape = RoundedCornerShape(3.5.dp)
                        )
                        .height(50.dp),
                    textStyle = TextStyle(fontSize = 14.sp),
                    colors = coloresTextFieldLogin()
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        BotonLogin("Registrarse") {
                            if(username != "" && email != "" && nombre != "" && password != "" && repetirPassword != ""){
                                if(email.contains("@")){
                                    if(password == repetirPassword){
                                        botonClicked = true
                                    }
                                    else{
                                        mostrarDialog = true
                                        dialogFunction = { mostrarDialog = false }
                                        dialogText = "Las contraseñas no coinciden"
                                    }
                                }
                                else {
                                    mostrarDialog = true
                                    dialogFunction = { mostrarDialog = false }
                                    dialogText = "El formato del email es incorrecto"
                                }
                            }
                            else{
                                mostrarDialog = true
                                dialogFunction = { mostrarDialog = false }
                                dialogText = "Faltan campos por rellenar"
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(text = "¿Ya tienes cuenta? ",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = colorResource(R.color.gray)
                            )
                        )
                        Text(text = "¡Accede con ella!",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = colorResource(R.color.purple_000)
                            ),
                            modifier = Modifier.clickable {
                                navController.navigate(Destinations.LOGIN)
                            }
                        )
                    }
                }
            }
        }
    }
}