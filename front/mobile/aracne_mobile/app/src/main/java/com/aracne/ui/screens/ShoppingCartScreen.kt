package com.aracne.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.aracne.R
import com.aracne.data.model.ProductInCart
import com.aracne.model.MainViewModel
import com.aracne.ui.navigation.Destinations

@Composable
fun ShoppingCartScreen(mainViewModel: MainViewModel, navController: NavHostController){
    mainViewModel.getCarrito()

    if(mainViewModel.carrito.isNotEmpty()){
        Column () {
            Row(){
                Text(
                    "Total actual: ",
                    modifier = Modifier.padding(PaddingValues(15.dp, 15.dp, 0.dp, 5.dp)),
                    style = TextStyle(
                        fontSize = 17.sp,
                        lineHeight = 21.sp
                    )
                )
                Text(
                    "${mainViewModel.getTotalCarrito()} €",
                    modifier = Modifier.padding(PaddingValues(0.dp, 15.dp, 15.dp, 5.dp)),
                    style = TextStyle(
                        fontSize = 17.sp,
                        lineHeight = 21.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            LazyColumn(
                modifier = Modifier.padding(15.dp, 0.dp, 15.dp, 15.dp)
            ) {
                items(mainViewModel.carrito) { item ->
                    Card(
                        modifier = Modifier.padding(0.dp, 10.dp).fillMaxWidth(),
                        colors = CardColors(
                            containerColor = MaterialTheme.colorScheme.background,
                            contentColor = Color.Black,
                            disabledContainerColor = MaterialTheme.colorScheme.background,
                            disabledContentColor = Color.Black
                        )
                    ) {
                        Row() {
                            Box(
                                modifier = Modifier
                                    .height(100.dp)
                                    .width(100.dp)
                                    .fillMaxWidth()
                                    .clipToBounds()
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(context = LocalContext.current)
                                        .data(item.producto?.image ?: "")
                                        .build(),
                                    contentDescription = item.producto?.name ?: "",
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(12.dp))
                                        .fillMaxWidth(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                            Column() {
                                Column() {
                                    Text(
                                        "${item.producto?.name ?: ""} x${item.cantidad}",
                                        modifier = Modifier.padding(PaddingValues(10.dp, 5.dp, 10.dp, 10.dp)),
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            lineHeight = 21.sp
                                        )
                                    )
                                }
                                Row() {

                                }
                            }
                        }
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.padding(15.dp).fillMaxWidth()
            ) {
                BotonAnadirCarrito("Añadir al carrito") {

                }
            }
        }
    }
    else{
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text("No hay ningún producto en tu carrito todavía.",
                modifier = Modifier.padding(PaddingValues(45.dp, 0.dp, 0.dp, 0.dp)),
                style = TextStyle(
                    fontSize = 24.sp,
                    lineHeight = 25.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }
}