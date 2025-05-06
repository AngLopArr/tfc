package com.aracne.ui.screens

import android.view.MotionEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.aracne.R
import com.aracne.model.MainViewModel
import com.aracne.ui.components.ShopDialog
import com.aracne.ui.navigation.Destinations
import java.util.Locale

@Composable
fun ShoppingCartScreen(mainViewModel: MainViewModel, navController: NavHostController){
    mainViewModel.getCarrito()
    var itemDelete by remember { mutableLongStateOf(0) }
    var deleteBotonClicked by remember { mutableStateOf(false) }
    var vaciarCarritoBotonClicked by remember { mutableStateOf(false) }
    var realizarPedidoBotonClicked by remember { mutableStateOf(false) }
    var mostrarDialog by remember { mutableStateOf(false) }
    var dialogText by remember { mutableStateOf("") }
    var dialogFunction by remember { mutableStateOf({}) }
    var dialogTitle by remember { mutableStateOf("") }

    LaunchedEffect(deleteBotonClicked) {
        if(deleteBotonClicked){
            dialogText = "¿Está seguro de que desea eliminar este producto de su carrito?"
            mostrarDialog = true
            dialogFunction = { mainViewModel.deleteProductFromCart(itemDelete); mostrarDialog = false }
            dialogTitle = "Eliminar producto"
        }
        deleteBotonClicked = false
    }

    LaunchedEffect(vaciarCarritoBotonClicked) {
        if(vaciarCarritoBotonClicked){
            dialogText = "¿Está seguro de que desea vaciar su carrito?"
            mostrarDialog = true
            dialogFunction = { mainViewModel.emptyCart(); mostrarDialog = false }
            dialogTitle = "Vaciar carrito"
        }
        vaciarCarritoBotonClicked = false
    }

    LaunchedEffect(realizarPedidoBotonClicked) {
        if(realizarPedidoBotonClicked){
            dialogText = "¿Está seguro de que desea finalizar su pedido?"
            mostrarDialog = true
            dialogFunction = { mainViewModel.makePurchase(); mostrarDialog = false }
            dialogTitle = "Finalizar pedido"
        }
        realizarPedidoBotonClicked = false
    }

    if(mostrarDialog){
        ShopDialog({ mostrarDialog = false }, dialogFunction, dialogTitle, dialogText)
    }

    if(mainViewModel.carrito.isNotEmpty()){
        Column {
            Row {
                Text(
                    "Total actual: ",
                    modifier = Modifier.padding(PaddingValues(15.dp, 15.dp, 0.dp, 5.dp)),
                    style = TextStyle(
                        fontSize = 17.sp,
                        lineHeight = 21.sp
                    )
                )
                Text(
                    "${
                        if(mainViewModel.getTotalCarrito().toString().split('.')[1].length == 1){
                            mainViewModel.getTotalCarrito().toString().padEnd(mainViewModel.getTotalCarrito().toString().length + 1, '0')
                        }
                        else{
                            mainViewModel.getTotalCarrito().toString()
                        }
                        
                    } €",
                    modifier = Modifier.padding(PaddingValues(0.dp, 15.dp, 15.dp, 5.dp)),
                    style = TextStyle(
                        fontSize = 17.sp,
                        lineHeight = 21.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.padding(15.dp, 5.dp, 0.dp, 5.dp).fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp)
                ) {
                    BotonRealizarCompra ("Vaciar carrito") {
                        vaciarCarritoBotonClicked = true
                    }
                }
                Row(
                    modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)
                ) {
                    BotonRealizarCompra ("Realizar pedido") {
                        realizarPedidoBotonClicked = true
                    }
                }

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
                        Row (
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Box(
                                modifier = Modifier
                                    .height(120.dp)
                                    .width(120.dp)
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
                            Column(
                                modifier = Modifier.fillMaxHeight(),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxWidth().height(100.dp)
                                ) {
                                    Text(
                                        AnnotatedString.fromHtml(
                                            "<span>${item.producto?.name ?: ""} <b>x${item.cantidad}</b></span>"),
                                        modifier = Modifier.padding(PaddingValues(12.dp, 0.dp, 10.dp, 0.dp)),
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            lineHeight = 19.sp
                                        )
                                    )
                                    Text(
                                        "${item.producto?.price} €",
                                        modifier = Modifier.padding(PaddingValues(12.dp, 2.dp, 10.dp, 0.dp)),
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            lineHeight = 19.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    )
                                    Text(
                                        "Talla ${item.talla.toUpperCase(Locale.ROOT)}",
                                        modifier = Modifier.padding(PaddingValues(12.dp, 2.dp, 10.dp, 0.dp)),
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            lineHeight = 19.sp
                                        )
                                    )
                                }
                                Row(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalAlignment = Alignment.Bottom
                                ){
                                    Row(
                                        modifier = Modifier.padding(12.dp, 0.dp, 0.dp, 0.dp)
                                    ) {
                                        BotonEditarProducto("Modificar producto"){
                                            navController.navigate(Destinations.MODIFYPROD + "/" + item.id)
                                        }
                                    }
                                    Row(
                                        modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)
                                    ) {
                                        BotonEliminarProducto(R.drawable.bin) {
                                            if(item.id != null){
                                                itemDelete = item.id
                                                deleteBotonClicked = true
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
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


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BotonRealizarCompra(contenido: String, onClick: () -> Unit){
    var isPressed by remember { mutableStateOf(false) }
    val backgroundColor = if (isPressed) R.color.purple_001 else R.color.purple_002
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .width(140.dp)
            .height(30.dp)
            .background(
                color = colorResource(backgroundColor),
                shape = RoundedCornerShape(10.dp)
            )
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        isPressed = true
                        onClick()
                        true
                    }
                    MotionEvent.ACTION_UP,
                    MotionEvent.ACTION_CANCEL -> {
                        isPressed = false
                        true
                    }

                    else -> false
                }
            }
    )
    {
        Text(
            contenido,
            style = TextStyle(
                fontSize = 15.sp
            )
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BotonEditarProducto(contenido: String, onClick: () -> Unit){
    var isPressed by remember { mutableStateOf(false) }
    val backgroundColor = if (isPressed) R.color.purple_001 else R.color.purple_002
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .width(160.dp)
            .height(20.dp)
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
            contenido,
            style = TextStyle(
                fontSize = 15.sp
            )
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BotonEliminarProducto(imagen: Int, onClick: () -> Unit){
    var isPressed by remember { mutableStateOf(false) }
    val backgroundColorCantidad = if (isPressed) R.color.purple_001 else R.color.purple_002
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.size(20.dp)
            .background(color = colorResource(backgroundColorCantidad), shape = RoundedCornerShape(12.dp))
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        isPressed = true
                        onClick()
                        true
                    }
                    MotionEvent.ACTION_UP,
                    MotionEvent.ACTION_CANCEL -> {
                        isPressed = false
                        true
                    }
                    else -> false
                }
            }
    ) {
        Image(
            painter = painterResource(imagen),
            contentDescription = "",
            modifier = Modifier.size(20.dp)
        )
    }
}