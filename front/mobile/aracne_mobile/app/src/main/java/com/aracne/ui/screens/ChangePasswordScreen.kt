package com.aracne.ui.screens

import android.view.MotionEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.aracne.R
import com.aracne.model.MainViewModel

@Composable
fun ChangePasswordScreen(navController: NavHostController, mainViewModel: MainViewModel){
    var passwordAnterior by remember { mutableStateOf("") }
    var passwordNueva by remember { mutableStateOf("") }
    var passwordNuevaRepeticion by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        OutlinedTextField(
            value = passwordAnterior,
            onValueChange = { passwordAnterior = it },
            placeholder = { Text("Contraseña actual",
                style = TextStyle(
                    fontSize = 14.sp
                )
            ) },
            modifier = Modifier.fillMaxWidth().padding(PaddingValues(15.dp, 15.dp, 15.dp, 0.dp)).border(width = 5.dp,
                color = colorResource(id = R.color.purple_002), shape = RoundedCornerShape(3.5.dp)
            ).height(50.dp),
            textStyle = TextStyle(fontSize = 14.sp),
            colors = coloresTextFieldCambioPassword()
        )
        OutlinedTextField(
            value = passwordNueva,
            onValueChange = { passwordNueva = it },
            placeholder = { Text("Nueva contraseña",
                style = TextStyle(
                    fontSize = 14.sp
                )
            ) },
            modifier = Modifier.fillMaxWidth().padding(PaddingValues(15.dp, 15.dp, 15.dp, 0.dp)).border(width = 5.dp,
                color = colorResource(id = R.color.purple_002), shape = RoundedCornerShape(3.5.dp)
            ).height(50.dp),
            textStyle = TextStyle(fontSize = 14.sp),
            colors = coloresTextFieldCambioPassword()
        )
        OutlinedTextField(
            value = passwordNuevaRepeticion,
            onValueChange = { passwordNuevaRepeticion = it },
            placeholder = { Text("Repita su nueva contraseña",
                style = TextStyle(
                    fontSize = 14.sp
                )
            ) },
            modifier = Modifier.fillMaxWidth().padding(PaddingValues(15.dp, 15.dp, 15.dp, 0.dp)).border(width = 5.dp,
                color = colorResource(id = R.color.purple_002), shape = RoundedCornerShape(3.5.dp)
            ).height(50.dp),
            textStyle = TextStyle(fontSize = 14.sp),
            colors = coloresTextFieldCambioPassword()
        )
        Spacer(modifier = Modifier.height(25.dp))
        BotonCambioPassword("Cambiar contraseña") {

        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BotonCambioPassword(contenido: String, onClick: () -> Unit){
    var isPressed by remember { mutableStateOf(false) }
    val backgroundColor = if (isPressed) R.color.purple_001 else R.color.purple_002
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.width(170.dp).height(40.dp).background(
            color = colorResource(backgroundColor),
            shape = RoundedCornerShape(12.dp)).pointerInteropFilter {
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
fun coloresTextFieldCambioPassword(): TextFieldColors {
    return TextFieldDefaults.textFieldColors(
        containerColor = colorResource(id = R.color.purple_002),
        focusedTextColor = colorResource(R.color.purple_004),
        unfocusedTextColor = colorResource(R.color.purple_004),
        unfocusedPlaceholderColor = colorResource(R.color.purple_004),
        focusedPlaceholderColor = colorResource(R.color.purple_004),
        cursorColor = colorResource(R.color.purple_004)
    )
}