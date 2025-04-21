package com.example.microservicio.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microservicio.model.Devoluciones;
import com.example.microservicio.model.ProductosDevoluciones;
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
}