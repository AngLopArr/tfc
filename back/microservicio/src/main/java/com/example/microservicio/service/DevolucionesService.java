package com.example.microservicio.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microservicio.model.Devolucion;
import com.example.microservicio.model.Devoluciones;
import com.example.microservicio.model.Producto;
import com.example.microservicio.model.ProductoDevolucion;
import com.example.microservicio.repository.DevolucionesRepository;

@Service
public class DevolucionesService {
    @Autowired 
    DevolucionesRepository devolucionesRepository;

    public Devoluciones getDevolucion(Long id){
        return devolucionesRepository.findById(id).orElse(null);
    }

    public Devoluciones createDevolucion(Devoluciones devolucion){
        devolucion.setFechaDevolucion(LocalDateTime.now());
        devolucionesRepository.save(devolucion);
        return devolucion;
    }

    public boolean cambiarEstadoDevolucion(Long idDevolucion, boolean estado){
        Devoluciones devolucion = devolucionesRepository.findById(idDevolucion).orElse(null);
        if(devolucion != null){
            devolucion.setAceptada(estado);
            return true;
        }
        else{
            return false;
        }
    }

    public Devolucion changeDevolucionToSend(Long idDevolucion){
        Devoluciones devolucion = getDevolucion(idDevolucion);
        Devolucion devolucionEnviar = new Devolucion(devolucion.getId_devolucion(), devolucion.getPedido().getId_pedido(), devolucion.getFechaDevolucion(), devolucion.getMotivoDevolucion(), devolucion.isAceptada());
        ArrayList<ProductoDevolucion> productosDevolucion = new ArrayList<>();
        for (int i = 0; i < devolucion.getProductosDevueltos().size(); i++) {
            ProductoDevolucion productoDevolucion = new ProductoDevolucion();
            productoDevolucion.setId(devolucion.getProductosDevueltos().get(i).getId());
            productoDevolucion.setProducto(new Producto(devolucion.getProductosDevueltos().get(i).getProducto().getId_producto(), devolucion.getProductosDevueltos().get(i).getProducto().getName(), devolucion.getProductosDevueltos().get(i).getProducto().getPrice(), devolucion.getProductosDevueltos().get(i).getProducto().getS(), devolucion.getProductosDevueltos().get(i).getProducto().getM(), devolucion.getProductosDevueltos().get(i).getProducto().getL(), devolucion.getProductosDevueltos().get(i).getProducto().getXL(), devolucion.getProductosDevueltos().get(i).getProducto().getImage()));
            productoDevolucion.setTalla(devolucion.getProductosDevueltos().get(i).getTalla());
            productoDevolucion.setCantidadDevuelta(devolucion.getProductosDevueltos().get(i).getCantidadDevuelta());
            productosDevolucion.add(productoDevolucion);
        }
        devolucionEnviar.setProductosDevueltos(productosDevolucion);
        return devolucionEnviar;
    }
}