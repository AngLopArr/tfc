package com.aracne.ui.screens

import android.util.Log
import android.view.MotionEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.input.pointer.pointerInteropFilter
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
import com.aracne.data.model.Password
import com.aracne.data.model.ProductInCart
import com.aracne.model.MainViewModel
import com.aracne.ui.components.BotonProductoCantidad
import com.aracne.ui.components.BotonProductoTalla
import com.aracne.ui.components.ProductoCantidad
import com.aracne.ui.components.ShopDialog
import com.aracne.ui.navigation.Destinations

@Composable
fun ProductScreen(navController: NavHostController, mainViewModel: MainViewModel){
    var selectedIndex by remember { mutableIntStateOf(0) }
    var selectedStock by remember { mutableIntStateOf(mainViewModel.product.s) }
    val tallas = listOf("S", "M", "L", "XL")
    var cantidad by remember { mutableIntStateOf(1) }
    var respuesta: ProductInCart?
    var botonClicked by remember { mutableStateOf(false) }
    var mostrarDialog by remember { mutableStateOf(false) }
    var dialogText by remember { mutableStateOf("") }

    LaunchedEffect(botonClicked) {
        if(botonClicked){
            respuesta = mainViewModel.addToCart(mainViewModel.product.id_producto, ProductInCart(null, null, null, tallas[selectedIndex], cantidad, null))
            if(respuesta != null){
                dialogText = "El producto se ha añadido al carrito con éxito"
                mostrarDialog = true
            }
            else {
                dialogText = "El producto no se ha podido añadir al carrito, inténtalo de nuevo más tarde"
                mostrarDialog = true
            }
        }
        botonClicked = false
    }

    if(mostrarDialog){
        ShopDialog({ mostrarDialog = false; navController.navigate(Destinations.PRODUCTOS) }, { mostrarDialog = false; navController.navigate(Destinations.PRODUCTOS) }, "Añadir producto", dialogText)
    }

    LazyColumn(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxHeight()
    ) {
        items(listOf(mainViewModel.product)){
            item ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(PaddingValues(15.dp))
            ) {
                Box(
                    modifier = Modifier
                        .height(500.dp)
                        .fillMaxWidth()
                        .clipToBounds()
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(item.image)
                            .build(),
                        contentDescription = item.name,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        item.name,
                        modifier = Modifier
                            .width(300.dp)
                            .padding(PaddingValues(5.dp, 12.dp, 0.dp, 5.dp)),
                        style = TextStyle(
                            fontSize = 18.sp,
                            lineHeight = 21.sp
                        )
                    )
                    Text(
                        "${
                            if(item.price.toString().split('.')[1].length == 1){
                                item.price.toString().padEnd(item.price.toString().length + 1, '0')
                            }
                            else{
                                item.price.toString()
                            }
                        } €",
                        modifier = Modifier.padding(PaddingValues(0.dp, 12.dp, 5.dp, 5.dp)),
                        style = TextStyle(
                            fontSize = 18.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                Row() {
                    Text(
                        "$selectedStock unidades",
                        modifier = Modifier.padding(PaddingValues(5.dp, 0.dp, 5.dp, 5.dp)),
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 21.sp
                        )
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.height(130.dp)
            ){
                Row(
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth()
                        .padding(PaddingValues(15.dp, 0.dp)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ){
                    tallas.forEachIndexed { index, talla ->
                        BotonProductoTalla(
                            contenido = talla,
                            isPressed = selectedIndex == index,
                            selectTalla = {
                                selectedIndex = index
                                selectedStock = when (selectedIndex) {
                                    0 -> item.s
                                    1 -> item.m
                                    2 -> item.l
                                    else -> item.xl
                                }
                                cantidad = 1
                            }
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .height(60.dp)
                        .fillMaxWidth()
                        .padding(PaddingValues(15.dp, 0.dp, 15.dp, 10.dp)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ){
                        BotonProductoCantidad(R.drawable.decrease) {
                            if (cantidad > 1) {
                                cantidad--
                            }
                        }
                        ProductoCantidad(cantidad)
                        BotonProductoCantidad(R.drawable.increase) {
                            if (cantidad < selectedStock) {
                                cantidad++
                            }
                        }
                    }
                    Row {
                        BotonAnadirCarrito("Añadir al carrito") {
                            if (selectedStock != 0) {
                                botonClicked = true
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BotonAnadirCarrito(contenido: String, onClick: () -> Unit){
    var isPressed by remember { mutableStateOf(false) }
    val backgroundColor = if (isPressed) R.color.purple_001 else R.color.purple_002
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .width(140.dp)
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
            "Añadir al carrito",
            style = TextStyle(
                fontSize = 15.sp
            )
        )
    }
}