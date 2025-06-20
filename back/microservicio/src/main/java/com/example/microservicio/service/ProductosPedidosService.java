package com.example.microservicio.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microservicio.model.Pedidos;
import com.example.microservicio.model.ProductoPedido;
import com.example.microservicio.model.ProductosCarrito;
import com.example.microservicio.model.ProductosPedidos;
import com.example.microservicio.repository.PedidosRepository;
import com.example.microservicio.repository.ProductosPedidosRepository;

@Service
public class ProductosPedidosService {
    @Autowired 
    ProductosPedidosRepository productosPedidosRepository;

    @Autowired
    PedidosRepository pedidosRepository;

    public void añadirProductosPedido(Pedidos pedido, ArrayList<ProductosCarrito> productosCarrito){
        ArrayList<ProductosPedidos> productosPedido = new ArrayList<>();
        for (int i = 0; i < productosCarrito.size(); i++) {
            ProductosPedidos producto = new ProductosPedidos();
            producto.setPedido(pedido);
            producto.setProducto(productosCarrito.get(i).getProducto());
            producto.setCantidad(productosCarrito.get(i).getCantidad());
            producto.setTalla(productosCarrito.get(i).getTalla());
            producto.setDevuelto(false);
            productosPedido.add(producto);
        }
        productosPedidosRepository.saveAll(productosPedido);
    }

    public boolean comprobarProductosEnUnPedido(Long idPedido, ArrayList<ProductoPedido> productosPedido){
        Pedidos pedido = pedidosRepository.findById(idPedido).orElse(null);
        if(pedido != null){
            int productosAComprobar = productosPedido.size();
            int productosPedidoReales = pedido.getProductos().size();
            return productosAComprobar <= productosPedidoReales;
        }
        else{
            return false;
        }
    }
}