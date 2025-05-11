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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.aracne.R
import com.aracne.model.MainViewModel
import java.util.Locale

@Composable
fun ReturnsScreen(mainViewModel: MainViewModel){
    if(mainViewModel.devoluciones.isNotEmpty()){
        Column {
            Row {
                Text(
                    "Devoluciones realizadas",
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
                items(mainViewModel.devoluciones) { item ->
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
                            modifier = Modifier.fillMaxSize().background(color = colorResource(R.color.purple_002)).padding(10.dp, 8.dp, 10.dp, 15.dp)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxHeight().padding(10.dp, 10.dp, 10.dp, 0.dp),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text("Pedido realizado " + item.fechaDevolucion.replace("T"," ").substring(0, 16),
                                    style = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 17.sp
                                    )
                                )
                                Spacer(modifier = Modifier.height(3.dp))
                                Text(
                                    if(item.estado == "procesando"){
                                        "Siendo procesada"
                                    }
                                    else if(item.estado == "rechazada" || item.estado == "aceptada"){
                                        "Procesada"
                                    }
                                    else {
                                        "Estado desconocido"
                                    }
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(200.dp)
                                ){
                                    Text("${item.productosDevueltos.size} artículos")
                                    if(item.mostrar != true){
                                        Text("Ver más", style = TextStyle(
                                            color = colorResource(R.color.purple_000),
                                            fontSize = (16.5).sp
                                        ), modifier = Modifier.clickable {
                                            mainViewModel.devoluciones = mainViewModel.devoluciones.map { devolucion ->
                                                if (devolucion.id_devolucion == item.id_devolucion) {
                                                    val devolucionCopia = devolucion.copy()
                                                    devolucionCopia.mostrar = true
                                                    devolucionCopia
                                                } else {
                                                    devolucion
                                                }
                                            }
                                        }
                                        )
                                    }
                                }
                                if(item.mostrar == true){
                                    Column(
                                        modifier = Modifier.padding(0.dp, 5.dp, 0.dp, 0.dp)
                                    ){
                                        for (product in item.productosDevueltos){
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
                                                    modifier = Modifier.fillMaxHeight(),
                                                    verticalArrangement = Arrangement.SpaceBetween
                                                ) {
                                                    Column(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .height(100.dp)
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
                                            }
                                        }
                                        Row(
                                            modifier = Modifier.fillMaxWidth().padding(0.dp, 5.dp),
                                            horizontalArrangement = Arrangement.End
                                        ){
                                            Text(item.estado.capitalize(Locale.ROOT), style = TextStyle(
                                                fontSize = 17.sp,
                                                fontWeight = FontWeight.SemiBold
                                            ))
                                        }
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.End
                                        ){
                                            Text("Ocultar", style = TextStyle(
                                                color = colorResource(R.color.purple_000),
                                                fontSize = (16.5).sp
                                            ), modifier = Modifier.clickable {
                                                    mainViewModel.devoluciones = mainViewModel.devoluciones.map { devolucion ->
                                                        if (devolucion.id_devolucion == item.id_devolucion) {
                                                            val devolucionCopia = devolucion.copy()
                                                            devolucionCopia.mostrar = false
                                                            devolucionCopia
                                                        } else {
                                                            devolucion
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
                modifier = Modifier.padding(PaddingValues(37.dp, 0.dp, 15.dp, 0.dp)),
                style = TextStyle(
                    fontSize = 24.sp,
                    lineHeight = 25.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }
}