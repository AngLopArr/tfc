package com.example.microservicio.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microservicio.model.Pedidos;
import com.example.microservicio.model.ProductosPedidos;
import com.example.microservicio.repository.ProductosPedidosRepository;

@Service
public class ProductosPedidosService {
    @Autowired 
    ProductosPedidosRepository productosPedidosRepository;

    public String añadirProductosPedido(Pedidos pedido, ArrayList<ProductosPedidos> productosPedido){
        for (int i = 0; i < productosPedido.size(); i++) {
            productosPedido.get(i).setPedido(pedido);
        }
        productosPedidosRepository.saveAll(productosPedido);
        return "Se han añadido los productos correctamente.";
    }

    public boolean comprobarProductosEnUnPedido(Long idPedido, ArrayList<Long> productosPedido){
        int productosAComprobar = productosPedido.size();
        int productosPedidoReales = productosPedidosRepository.countProductosEnPedido(idPedido, productosPedido);
        return productosAComprobar == productosPedidoReales;
    }
}