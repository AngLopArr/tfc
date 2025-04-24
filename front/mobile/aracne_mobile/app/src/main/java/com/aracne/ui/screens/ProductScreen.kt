package com.aracne.ui.screens

import android.view.MotionEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.aracne.R
import com.aracne.data.model.ProductInCart
import com.aracne.model.MainViewModel
import com.aracne.ui.components.BotonProductoCantidad
import com.aracne.ui.components.BotonProductoTalla
import com.aracne.ui.components.ProductoCantidad
import com.aracne.ui.navigation.Destinations

@Composable
fun ProductScreen(productoId: Long, navController: NavHostController, mainViewModel: MainViewModel){
    mainViewModel.getProduct(productoId)
    var selectedIndex by remember { mutableIntStateOf(0) }
    var selectedStock by remember { mutableIntStateOf(mainViewModel.product.s) }
    val tallas = listOf("S", "M", "L", "XL")
    var cantidad by remember { mutableIntStateOf(1) }

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxHeight()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(PaddingValues(15.dp))
        ) {
            Box(
                modifier = Modifier
                    .height(500.dp)
                    .fillMaxWidth()
                    .clipToBounds()
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(mainViewModel.product.image)
                        .build(),
                    contentDescription = mainViewModel.product.name,
                    modifier = Modifier.clip(RoundedCornerShape(12.dp)).fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    mainViewModel.product.name,
                    modifier = Modifier.padding(PaddingValues(5.dp, 12.dp, 5.dp, 5.dp)),
                    style = TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 21.sp
                    )
                )
                Text(
                    "${mainViewModel.product.price} €",
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
            modifier = Modifier.height(150.dp)
        ){
            Row(
                modifier = Modifier.height(50.dp).fillMaxWidth().padding(PaddingValues(15.dp, 0.dp)),
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
                                    0 -> mainViewModel.product.s
                                    1 -> mainViewModel.product.m
                                    2 -> mainViewModel.product.l
                                    else -> mainViewModel.product.xl
                                }
                        }
                    )
                }
            }
            Row(
                modifier = Modifier.height(60.dp).fillMaxWidth().padding(PaddingValues(15.dp, 0.dp, 15.dp, 10.dp)),
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
                        mainViewModel.addToCart(mainViewModel.product.id_producto, ProductInCart(null, null, null, tallas[selectedIndex], cantidad, null))
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
        modifier = Modifier.width(140.dp).height(40.dp).background(
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
            "Añadir al carrito",
            style = TextStyle(
                fontSize = 15.sp
            )
        )
    }
}