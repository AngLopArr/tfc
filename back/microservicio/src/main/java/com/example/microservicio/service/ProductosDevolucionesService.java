package com.example.microservicio.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microservicio.model.Devoluciones;
import com.example.microservicio.model.ProductosDevoluciones;
import com.example.microservicio.model.ProductosPedidos;
import com.example.microservicio.repository.ProductosDevolucionesRepository;

@Service
public class ProductosDevolucionesService {
    @Autowired 
    ProductosDevolucionesRepository productosDevolucionesRepository;

    public String añadirProductosDevolucion(Devoluciones devolucion, ArrayList<ProductosPedidos> productosPedido){
        ArrayList<ProductosDevoluciones> productosDevolucion = new ArrayList<>();
        for (int i = 0; i < productosPedido.size(); i++) {
            ProductosDevoluciones producto = new ProductosDevoluciones();
            producto.setDevolucion(devolucion);
            producto.setProducto(productosPedido.get(i).getProducto());
            producto.setCantidadDevuelta(productosPedido.get(i).getCantidad());
            producto.setTalla(productosPedido.get(i).getTalla());
            productosDevolucion.add(producto);
        }
        productosDevolucionesRepository.saveAll(productosDevolucion);
        return "Se han añadido los productos correctamente.";
    }
}