package com.aracne.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.aracne.R
import com.aracne.ui.navigation.Destinations
import org.jetbrains.annotations.Async

@Composable
fun ProductsScreen(navController: NavHostController){
    var busqueda by remember { mutableStateOf("") }
    val precio = 14.55
    val urls = arrayOf("https://www.marie-claire.es/wp-content/uploads/sites/2/2023/04/04/642bf68e97173.jpeg",
        "https://www.marie-claire.es/wp-content/uploads/sites/2/2023/04/04/642bf68e97173.jpeg")

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        OutlinedTextField(
            value = busqueda,
            onValueChange = { busqueda = it },
            placeholder = { Text("Buscar productos") },
            modifier = Modifier.fillMaxWidth().padding(PaddingValues(15.dp, 15.dp, 15.dp, 0.dp)).border(width = 3.dp,
                color = colorResource(id = R.color.purple_001), shape = RoundedCornerShape(3.5.dp)),
            colors = coloresTextFieldLogin(),
            trailingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.lupa),
                    contentDescription = stringResource(id = R.string.products),
                    modifier = Modifier.size(32.dp).padding(0.dp, 0.dp,4.dp, 0.dp).clickable {
                        // realizar busqueda api
                    }
                )
            }
        )
        Spacer(modifier = Modifier.size(5.dp))
        LazyColumn(modifier = Modifier.fillMaxSize().padding(PaddingValues(0.dp, 0.dp, 0.dp, 10.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            items(urls) {
                    item ->
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth().padding(PaddingValues(15.dp, 10.dp, 15.dp, 5.dp))
                ){
                    Column(
                        modifier = Modifier.fillMaxWidth(0.48f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ){
                        AsyncImage(
                            model = ImageRequest.Builder(context = LocalContext.current)
                                .data(item)
                                .build(),
                            contentDescription = "",
                            modifier = Modifier.clip(RoundedCornerShape(12.dp)).fillMaxWidth().clickable {
                                navController.navigate("producto/" + 1)
                            }
                        )
                        Text("Falda de cuero color café",
                            modifier = Modifier.padding(PaddingValues(3.dp, 8.dp, 5.dp, 5.dp)),
                            style = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 21.sp
                            ))
                        Text("$precio €",
                            modifier = Modifier.padding(PaddingValues(3.dp, 0.dp, 5.dp, 3.dp)),
                            style = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 20.sp,
                                fontWeight = FontWeight.Bold
                            ))
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth(0.925f)
                    ){
                        AsyncImage(
                            model = ImageRequest.Builder(context = LocalContext.current)
                                .data(item)
                                .build(),
                            contentDescription = "",
                            modifier = Modifier.clip(RoundedCornerShape(12.dp)).fillMaxWidth().clickable {
                                // realizar busqueda api
                            }
                        )
                        Text("Falda de cuero color",
                            modifier = Modifier.padding(PaddingValues(3.dp, 8.dp, 5.dp, 5.dp)),
                            style = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 21.sp
                            ))
                        Text("$precio €",
                            modifier = Modifier.padding(PaddingValues(3.dp, 0.dp, 5.dp, 3.dp)),
                            style = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 20.sp,
                                fontWeight = FontWeight.Bold
                            ))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun coloresTextFieldLogin(): TextFieldColors {
    return TextFieldDefaults.textFieldColors(
        containerColor = colorResource(id = R.color.purple_001),
        focusedTextColor = colorResource(R.color.purple_003),
        unfocusedTextColor = colorResource(R.color.purple_003),
        unfocusedPlaceholderColor = colorResource(R.color.purple_003),
        focusedPlaceholderColor = colorResource(R.color.purple_003),
        cursorColor = colorResource(R.color.purple_003),
    )
}
