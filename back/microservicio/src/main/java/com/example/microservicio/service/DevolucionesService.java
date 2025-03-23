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

    public boolean reenviarAlCliente(Long idDevolucion, boolean reenviar){
        Devoluciones devolucion = devolucionesRepository.findById(idDevolucion).orElse(null);
        if(devolucion != null){
            devolucion.setDevolverProductosACliente(reenviar);
            return true;
        }
        else{
            return false;
        }
    }
}