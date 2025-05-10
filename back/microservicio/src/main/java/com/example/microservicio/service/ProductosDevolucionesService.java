package com.example.microservicio.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microservicio.model.Devoluciones;
import com.example.microservicio.model.ProductoPedido;
import com.example.microservicio.model.ProductosDevoluciones;
import com.example.microservicio.model.ProductosPedidos;
import com.example.microservicio.repository.ProductosDevolucionesRepository;
import com.example.microservicio.repository.ProductosPedidosRepository;

@Service
public class ProductosDevolucionesService {
    @Autowired 
    ProductosDevolucionesRepository productosDevolucionesRepository;

    @Autowired
    ProductosPedidosRepository productosPedidosRepository;

    public String añadirProductosDevolucion(Devoluciones devolucion, ArrayList<ProductoPedido> productosPedido){
        ArrayList<ProductosDevoluciones> productosDevolucion = new ArrayList<>();
        for (int i = 0; i < productosPedido.size(); i++) {
            ProductosPedidos productoPedido = productosPedidosRepository.findById(productosPedido.get(i).getId()).orElse(null);
            if(productoPedido != null){
                ProductosDevoluciones producto = new ProductosDevoluciones();
                producto.setDevolucion(devolucion);
                producto.setProducto(productoPedido.getProducto());
                producto.setCantidadDevuelta(productoPedido.getCantidad());
                producto.setTalla(productoPedido.getTalla());
                productosDevolucion.add(producto);
            }
        }
        productosDevolucionesRepository.saveAll(productosDevolucion);
        return "Se han añadido los productos correctamente.";
    }
}