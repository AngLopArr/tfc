package com.aracne.ui.screens

import android.content.Intent
import android.util.Log
import android.view.MotionEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.aracne.MainActivity
import com.aracne.R
import com.aracne.model.MainViewModel
import com.aracne.ui.components.ShopDialog
import com.aracne.ui.navigation.Destinations
import kotlin.math.log

@Composable
fun LoginScreen(mainViewModel: MainViewModel, navController: NavHostController, login: (Intent) -> Unit){
    var credential by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var botonClicked by remember { mutableStateOf(false) }
    var mostrarDialog by remember { mutableStateOf(false) }
    var dialogText by remember { mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect (botonClicked) {
        if(botonClicked){
            val cliente = mainViewModel.login(username = username, email = email, password = password)

            if (cliente != null && cliente.success == true){
                mainViewModel.saveId(cliente.id ?: 0)
                mainViewModel.saveName(cliente.name ?: "")
                login(Intent(context, MainActivity::class.java))
            }
            else {
                mostrarDialog = true
                dialogText = "Las credenciales no son correctas"
            }
        }
        botonClicked = false
    }

    if(mostrarDialog){
        ShopDialog({ mostrarDialog = false }, { mostrarDialog = false }, "Login", dialogText)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                // Replace with your image id
                painterResource(id = R.drawable.background),
                contentScale = ContentScale.FillBounds
            )
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.big_logo),
                contentDescription = "logo",
                modifier = Modifier
                    .size(440.dp)
                    .padding(20.dp, 45.dp, 20.dp, 0.dp)
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .padding(25.dp, 0.dp, 25.dp, 45.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(R.color.white),
                )
            ){
                Spacer(modifier = Modifier.height(30.dp))
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ){
                    OutlinedTextField(
                        value = credential,
                        onValueChange = { credential = it },
                        placeholder = { Text("Nombre de usuario o email",
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
                        value = password,
                        onValueChange = { password = it },
                        placeholder = { Text("Contraseña",
                            style = TextStyle(
                                fontSize = 14.sp
                            )) },
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
                        BotonLogin("Login") {
                            if(credential.contains("@")){
                                email = credential
                                username = ""
                            }
                            else {
                                username = credential
                                email = ""
                            }
                            botonClicked = true
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
                        Text(text = "¿No tienes cuenta? ",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = colorResource(R.color.gray)
                            )
                        )
                        Text(text = "¡Crea una!",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = colorResource(R.color.purple_000)
                            ),
                            modifier = Modifier.clickable {
                                navController.navigate(Destinations.REGISTRY)
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BotonLogin(contenido: String, onClick: () -> Unit){
    var isPressed by remember { mutableStateOf(false) }
    val backgroundColor = if (isPressed) R.color.purple_001 else R.color.purple_002
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .width(150.dp)
            .height(40.dp)
            .background(
                color = colorResource(backgroundColor),
                shape = RoundedCornerShape(12.dp)
            )
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        isPressed = true
                        true
                    }

                    MotionEvent.ACTION_UP,
                    MotionEvent.ACTION_CANCEL -> {
                        isPressed = false
                        onClick()
                        true
                    }

                    else -> false
                }
            }
    )
    {
        Text(
            text = contenido,
            style = TextStyle(
                fontSize = 15.sp,
                color = colorResource(R.color.purple_000)
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun coloresTextFieldLogin(): TextFieldColors {
    return TextFieldDefaults.textFieldColors(
        containerColor = colorResource(id = R.color.light_gray),
        focusedTextColor = colorResource(R.color.gray),
        unfocusedTextColor = colorResource(R.color.gray),
        unfocusedPlaceholderColor = colorResource(R.color.gray),
        focusedPlaceholderColor = colorResource(R.color.gray),
        cursorColor = colorResource(R.color.gray),
    )
}