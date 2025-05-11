package com.aracne.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.substring
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.aracne.R
import com.aracne.data.model.Product
import com.aracne.data.model.PurchasedProduct
import com.aracne.model.MainViewModel
import com.aracne.ui.components.ShopDialog
import com.aracne.ui.navigation.Destinations
import java.util.Locale

@Composable
fun PurchasesScreen(mainViewModel: MainViewModel, navController: NavHostController){
    var productsToReturn by remember { mutableStateOf(listOf<PurchasedProduct>()) }
    var pedidoReturn by remember { mutableLongStateOf(0) }
    var returnButtonClicked by remember { mutableStateOf(false) }
    var mostrarDialog by remember { mutableStateOf(false) }

    LaunchedEffect(returnButtonClicked) {
        if(returnButtonClicked){
            mostrarDialog = true
        }
        returnButtonClicked = false
    }

    if(mostrarDialog){
        ShopDialog({ mostrarDialog = false }, { mainViewModel.makeReturn(pedidoReturn, productsToReturn); navController.navigate(Destinations.RETURNS); mostrarDialog = false }, "Procesar devolución", "¿Está seguro de que desea procesar esta devolución?")
    }

    if(mainViewModel.pedidos.isNotEmpty()){
        Column {
            Row {
                Text(
                    "Pedidos realizados",
                    modifier = Modifier.padding(PaddingValues(20.dp, 20.dp, 20.dp, 5.dp)),
                    style = TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 21.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            LazyColumn(
                modifier = Modifier.padding(15.dp, 0.dp, 15.dp, 15.dp)
            ) {
                items(mainViewModel.pedidos) { item ->
                    Card(
                        modifier = Modifier
                            .padding(0.dp, 10.dp)
                            .fillMaxWidth(),
                        colors = CardColors(
                            containerColor = MaterialTheme.colorScheme.background,
                            contentColor = Color.Black,
                            disabledContainerColor = MaterialTheme.colorScheme.background,
                            disabledContentColor = Color.Black
                        )
                    ) {
                        Row (
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = colorResource(R.color.purple_002))
                                .padding(10.dp, 8.dp, 10.dp, 15.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(10.dp, 10.dp, 10.dp, 0.dp),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text("Pedido realizado " + item.fechaPedido.replace("T"," ").substring(0, 16),
                                    style = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 17.sp
                                    ))
                                Spacer(modifier = Modifier.height(3.dp))
                                Text(
                                    if(item.estado == "procesando"){
                                        "Siendo procesado"
                                    }
                                    else if(item.estado == "entregando"){
                                        "En proceso de entrega"
                                    }
                                    else if(item.estado == "recibido"){
                                        "Entregado"
                                    }
                                    else {
                                        "Estado desconocido"
                                    }
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ){
                                    Text("${item.productos.size} artículos")
                                    if(item.mostrar != true){
                                        Text("Ver más", style = TextStyle(
                                            color = colorResource(R.color.purple_000),
                                            fontSize = (16.5).sp
                                        ), modifier = Modifier.clickable {
                                            mainViewModel.pedidos = mainViewModel.pedidos.map { pedido ->
                                                if (pedido.id_pedido == item.id_pedido) {
                                                    val pedidoCopia = pedido.copy()
                                                    pedidoCopia.mostrar = true
                                                    pedidoCopia
                                                } else {
                                                    pedido
                                                }
                                            }
                                        }
                                        )
                                    }
                                }
                                if(item.mostrar == true){
                                    Column(
                                        modifier = Modifier.fillMaxWidth().padding(0.dp, 5.dp, 0.dp, 0.dp)
                                    ){
                                        for (product in item.productos){
                                            if(product.devuelto){
                                                Spacer(modifier = Modifier.height(9.dp))
                                            }
                                            Box {
                                                Row(
                                                    modifier = Modifier.padding(4.dp, 9.dp)
                                                ){
                                                    Box(
                                                        modifier = Modifier
                                                            .height(100.dp)
                                                            .width(100.dp)
                                                            .fillMaxWidth()
                                                            .clipToBounds()
                                                    ) {
                                                        AsyncImage(
                                                            model = ImageRequest.Builder(context = LocalContext.current)
                                                                .data(product.producto.image)
                                                                .build(),
                                                            contentDescription = product.producto.name,
                                                            modifier = Modifier
                                                                .clip(RoundedCornerShape(12.dp))
                                                                .fillMaxWidth(),
                                                            contentScale = ContentScale.Crop
                                                        )
                                                    }
                                                    Column(
                                                        modifier = Modifier.fillMaxHeight().width(220.dp),
                                                        verticalArrangement = Arrangement.SpaceBetween
                                                    ) {
                                                        Column(
                                                            modifier = Modifier
                                                                .fillMaxWidth()
                                                                .height(100.dp)
                                                                .padding(0.dp, 0.dp, 5.dp, 0.dp)
                                                        ) {
                                                            Text(
                                                                AnnotatedString.fromHtml(
                                                                    "<span>${product.producto.name} <b>x${product.cantidad}</b></span>"),
                                                                modifier = Modifier.padding(PaddingValues(12.dp, 0.dp, 10.dp, 0.dp)),
                                                                style = TextStyle(
                                                                    fontSize = 16.sp,
                                                                    lineHeight = 19.sp
                                                                )
                                                            )
                                                            Text(
                                                                "${product.producto.price} €",
                                                                modifier = Modifier.padding(PaddingValues(12.dp, 2.dp, 10.dp, 0.dp)),
                                                                style = TextStyle(
                                                                    fontSize = 16.sp,
                                                                    lineHeight = 19.sp,
                                                                    fontWeight = FontWeight.SemiBold
                                                                )
                                                            )
                                                            Text(
                                                                "Talla ${product.talla.toUpperCase(Locale.ROOT)}",
                                                                modifier = Modifier.padding(PaddingValues(12.dp, 2.dp, 10.dp, 0.dp)),
                                                                style = TextStyle(
                                                                    fontSize = 16.sp,
                                                                    lineHeight = 19.sp
                                                                )
                                                            )
                                                        }
                                                    }
                                                    Column(
                                                        verticalArrangement = Arrangement.Center,
                                                        horizontalAlignment = Alignment.CenterHorizontally,
                                                        modifier = Modifier.height(100.dp)
                                                            .padding(5.dp, 0.dp, 17.dp, 0.dp)
                                                    ) {
                                                        if(!product.devuelto) {
                                                            Checkbox(
                                                                checked = product.devolver == true,
                                                                onCheckedChange = {
                                                                    mainViewModel.pedidos =
                                                                        mainViewModel.pedidos.map { pedido ->
                                                                            if (pedido.id_pedido == item.id_pedido) {
                                                                                val pedidoCopia =
                                                                                    pedido.copy(
                                                                                        productos = pedido.productos.map { productoCambiar ->
                                                                                            if (productoCambiar.id == product.id) {
                                                                                                productoCambiar.copy(
                                                                                                    devolver = !(productoCambiar.devolver
                                                                                                        ?: false)
                                                                                                )
                                                                                            } else productoCambiar
                                                                                        }
                                                                                    )
                                                                                pedidoCopia
                                                                            } else pedido
                                                                        }
                                                                },
                                                                colors = CheckboxColors(
                                                                    checkedCheckmarkColor = colorResource(
                                                                        R.color.white
                                                                    ),
                                                                    uncheckedCheckmarkColor = colorResource(
                                                                        R.color.white
                                                                    ),
                                                                    checkedBoxColor = colorResource(
                                                                        R.color.purple_001
                                                                    ),
                                                                    uncheckedBoxColor = colorResource(
                                                                        R.color.purple_002
                                                                    ),
                                                                    disabledCheckedBoxColor = colorResource(
                                                                        R.color.purple_001
                                                                    ),
                                                                    disabledUncheckedBoxColor = colorResource(
                                                                        R.color.purple_002
                                                                    ),
                                                                    disabledIndeterminateBoxColor = colorResource(
                                                                        R.color.purple_001
                                                                    ),
                                                                    checkedBorderColor = colorResource(
                                                                        R.color.purple_001
                                                                    ),
                                                                    uncheckedBorderColor = colorResource(
                                                                        R.color.purple_001
                                                                    ),
                                                                    disabledBorderColor = colorResource(
                                                                        R.color.purple_001
                                                                    ),
                                                                    disabledUncheckedBorderColor = colorResource(
                                                                        R.color.purple_001
                                                                    ),
                                                                    disabledIndeterminateBorderColor = colorResource(
                                                                        R.color.purple_001
                                                                    )
                                                                )
                                                            )
                                                        }
                                                    }
                                                }
                                                if(product.devuelto){
                                                    Box(contentAlignment = Alignment.Center, modifier = Modifier.matchParentSize().background(colorResource(R.color.light_gray).copy(alpha = 0.8f), RoundedCornerShape(12.dp))){
                                                        Text("Devuelto", style =
                                                            TextStyle(
                                                                fontWeight = FontWeight.W600,
                                                                fontSize = 18.sp,
                                                                color = colorResource(R.color.dark_gray)
                                                            )
                                                        )
                                                    }
                                                }
                                            }
                                            if(product.devuelto){
                                                Spacer(modifier = Modifier.height(9.dp))
                                            }
                                        }
                                        Row(
                                            modifier = Modifier.fillMaxWidth().padding(0.dp, 5.dp),
                                            horizontalArrangement = Arrangement.End
                                        ){
                                            Text("${item.totalPedido} €", style = TextStyle(
                                                fontSize = 17.sp,
                                                fontWeight = FontWeight.SemiBold
                                            ))
                                        }
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ){
                                            Text("Procesar devolución", style = TextStyle(
                                                color = colorResource(R.color.purple_000),
                                                fontSize = (16.5).sp
                                            ), modifier = Modifier.clickable {
                                                for(product in item.productos){
                                                    if(product.devolver == true){
                                                        productsToReturn += product
                                                    }
                                                }
                                                if(mainViewModel.prepareReturn(productsToReturn)){
                                                    pedidoReturn = item.id_pedido
                                                    returnButtonClicked = true
                                                }
                                            }
                                            )
                                            Text("Ocultar", style = TextStyle(
                                                color = colorResource(R.color.purple_000),
                                                fontSize = (16.5).sp
                                            ), modifier = Modifier.clickable {
                                                mainViewModel.pedidos = mainViewModel.pedidos.map { pedido ->
                                                    if (pedido.id_pedido == item.id_pedido) {
                                                        val pedidoCopia = pedido.copy()
                                                        pedidoCopia.mostrar = false
                                                        pedidoCopia
                                                    } else {
                                                        pedido
                                                    }
                                                }
                                            }
                                            )
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
            Text("No has realizado ningún pedido todavía.",
                fontSize = 24.sp,
                lineHeight = 25.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}