package com.example.microservicio.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microservicio.model.Devoluciones;
import com.example.microservicio.repository.DevolucionesRepository;

@Service
public class DevolucionesService {
    @Autowired 
    DevolucionesRepository devolucionesRepository;

    public Devoluciones createDevolucion(Devoluciones devolucion){
        devolucion.setFechaDevolucion(LocalDateTime.now());
        devolucionesRepository.save(devolucion);
        return devolucion;
    }

    public String cambiarEstadoDevolucion(boolean estado, Long idDevolucion){
        Devoluciones devolucion = devolucionesRepository.findById(idDevolucion).orElse(null);
        if(devolucion != null){
            devolucion.setAceptada(estado);
            return "Estado modificado con éxito.";
        }
        else{
            return "La devolución indicada no existe.";
        }
    }

    public String reenviarAlCliente(boolean reenviar, Long idDevolucion){
        Devoluciones devolucion = devolucionesRepository.findById(idDevolucion).orElse(null);
        if(devolucion != null){
            devolucion.setDevolverProductosACliente(reenviar);
            return "La operación se ha procesado con éxito.";
        }
        else{
            return "La devolución indicada no existe.";
        }
    }
}