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

    public String añadirProductosDevolucion(Devoluciones devolucion, ArrayList<ProductosDevoluciones> productosDevolucion){
        for (int i = 0; i < productosDevolucion.size(); i++) {
            productosDevolucion.get(i).setDevolucion(devolucion);
        }
        productosDevolucionesRepository.saveAll(productosDevolucion);
        return "Se han añadido los productos correctamente.";
    }

    public ArrayList<ProductosPedidos> changeProductosDevolucionToProductosPedido(Long idDevolucion){
        ArrayList<ProductosDevoluciones> productosDevolucion = productosDevolucionesRepository.findByDevolucionId(idDevolucion);
        ArrayList<ProductosPedidos> productosPedido = new ArrayList<>();
        for (int i = 0; i < productosDevolucion.size(); i++) {
            ProductosPedidos productoPedido = new ProductosPedidos();
            productoPedido.setProducto(productosDevolucion.get(i).getProducto());
            productoPedido.setCantidad(productosDevolucion.get(i).getCantidadDevuelta());
            productoPedido.setTalla(productosDevolucion.get(i).getTalla());
            productosPedido.add(productoPedido);
        }
        return productosPedido;
    }
}