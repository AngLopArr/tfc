package com.example.microservicio.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microservicio.model.Devolucion;
import com.example.microservicio.model.Devoluciones;
import com.example.microservicio.model.Producto;
import com.example.microservicio.model.ProductoDevolucion;
import com.example.microservicio.model.ProductosDevoluciones;
import com.example.microservicio.repository.DevolucionesRepository;
import com.example.microservicio.repository.ProductosDevolucionesRepository;

@Service
public class DevolucionesService {
    @Autowired 
    DevolucionesRepository devolucionesRepository;

    @Autowired
    ProductosDevolucionesRepository productosDevolucionesRepository;

    public Devoluciones getDevolucion(Long id){
        return devolucionesRepository.findById(id).orElse(null);
    }

    public Devoluciones createDevolucion(Devoluciones devolucion){
        devolucion.setFechaDevolucion(LocalDateTime.now());
        devolucion.setEstado("procesando");
        return devolucionesRepository.save(devolucion);
    }

    public boolean cambiarEstadoDevolucion(Long idDevolucion, String estado){
        Devoluciones devolucion = devolucionesRepository.findById(idDevolucion).orElse(null);
        if(devolucion != null){
            devolucion.setEstado(estado);
            return true;
        }
        else{
            return false;
        }
    }

    public ArrayList<Devolucion> getDevolucionesById(Long id){
        ArrayList<Devoluciones> devolucionesSinFormato = (ArrayList<Devoluciones>) devolucionesRepository.findDevolucionesByClienteId(id).orElse(null);
        ArrayList<Devolucion> devoluciones = new ArrayList<>();
        if(devolucionesSinFormato != null){
            for (int i = 0; i < devolucionesSinFormato.size(); i++) {
                devoluciones.add(changeDevolucionToSend(devolucionesSinFormato.get(i).getId_devolucion()));
            }
        }
        return devoluciones;
    }

    public Devolucion changeDevolucionToSend(Long idDevolucion){
        Devoluciones devolucion = getDevolucion(idDevolucion);
        ArrayList<ProductosDevoluciones> productosDevoluciones = productosDevolucionesRepository.getProductosDevoluciones(idDevolucion).orElse(null);
        Devolucion devolucionEnviar = new Devolucion(devolucion.getId_devolucion(), devolucion.getPedido().getId_pedido(), devolucion.getFechaDevolucion(), devolucion.getEstado());
        ArrayList<ProductoDevolucion> productosDevolucion = new ArrayList<>();
        for (int i = 0; i <productosDevoluciones.size(); i++) {
            ProductoDevolucion productoDevolucion = new ProductoDevolucion();
            productoDevolucion.setId(productosDevoluciones.get(i).getId());
            productoDevolucion.setProducto(new Producto(productosDevoluciones.get(i).getProducto().getId_producto(), productosDevoluciones.get(i).getProducto().getName(), productosDevoluciones.get(i).getProducto().getPrice(), productosDevoluciones.get(i).getProducto().getS(), productosDevoluciones.get(i).getProducto().getM(), productosDevoluciones.get(i).getProducto().getL(), productosDevoluciones.get(i).getProducto().getXL(), productosDevoluciones.get(i).getProducto().getImage()));
            productoDevolucion.setTalla(productosDevoluciones.get(i).getTalla());
            productoDevolucion.setCantidadDevuelta(productosDevoluciones.get(i).getCantidadDevuelta());
            productosDevolucion.add(productoDevolucion);
        }
        devolucionEnviar.setProductosDevueltos(productosDevolucion);
        return devolucionEnviar;
    }
}