package com.aracne.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.aracne.R
import com.aracne.model.MainViewModel

@Composable
fun ProductsScreen(navController: NavHostController, mainViewModel: MainViewModel){
    mainViewModel.getInitialProducts()
    var busqueda by remember { mutableStateOf("") }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        OutlinedTextField(
            value = busqueda,
            onValueChange = { busqueda = it },
            placeholder = { Text("Buscar productos",
                style = TextStyle(
                    fontSize = 14.sp
                )) },
            modifier = Modifier.fillMaxWidth().padding(PaddingValues(15.dp, 15.dp, 15.dp, 0.dp)).border(width = 3.dp,
                color = colorResource(id = R.color.purple_001), shape = RoundedCornerShape(3.5.dp)).height(50.dp),
            textStyle = TextStyle(fontSize = 14.sp),
            colors = coloresTextFieldBusqueda(),
            trailingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.lupa),
                    contentDescription = stringResource(id = R.string.products),
                    modifier = Modifier.size(28.dp).padding(0.dp, 0.dp,4.dp, 0.dp).clickable {
                        // realizar busqueda api
                    }
                )
            }
        )
        Spacer(modifier = Modifier.size(5.dp))
        LazyColumn(modifier = Modifier.fillMaxSize().padding(PaddingValues(0.dp, 0.dp, 0.dp, 10.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            items(mainViewModel.productos.chunked(2)) {
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
                                .data(item[0].image)
                                .build(),
                            contentDescription = item[0].name,
                            modifier = Modifier.clip(RoundedCornerShape(12.dp)).fillMaxWidth().clickable {
                                navController.navigate("producto/" + item[0].id_producto)
                            }
                        )
                        Text(item[0].name,
                            modifier = Modifier.padding(PaddingValues(3.dp, 8.dp, 5.dp, 5.dp)),
                            style = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 21.sp
                            ))
                        Text("${item[0].price} €",
                            modifier = Modifier.padding(PaddingValues(3.dp, 0.dp, 5.dp, 3.dp)),
                            style = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 20.sp,
                                fontWeight = FontWeight.Bold
                            ))
                    }
                    if(item.getOrNull(1) != null){
                        Column(
                            modifier = Modifier.fillMaxWidth(0.925f)
                        ){
                            AsyncImage(
                                model = ImageRequest.Builder(context = LocalContext.current)
                                    .data(item[1].image)
                                    .build(),
                                contentDescription = item[1].name,
                                modifier = Modifier.clip(RoundedCornerShape(12.dp)).fillMaxWidth().clickable {
                                    navController.navigate("producto/" + item[1].id_producto)
                                }
                            )
                            Text(item[1].name,
                                modifier = Modifier.padding(PaddingValues(3.dp, 8.dp, 5.dp, 5.dp)),
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    lineHeight = 21.sp
                                ))
                            Text("${item[1].price} €",
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun coloresTextFieldBusqueda(): TextFieldColors {
    return TextFieldDefaults.textFieldColors(
        containerColor = colorResource(id = R.color.purple_001),
        focusedTextColor = colorResource(R.color.purple_003),
        unfocusedTextColor = colorResource(R.color.purple_003),
        unfocusedPlaceholderColor = colorResource(R.color.purple_003),
        focusedPlaceholderColor = colorResource(R.color.purple_003),
        cursorColor = colorResource(R.color.purple_003),
    )
}
