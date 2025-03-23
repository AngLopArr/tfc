package com.example.microservicio.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.microservicio.model.ProductosDevoluciones;

@Repository
public interface ProductosDevolucionesRepository extends JpaRepository<ProductosDevoluciones, Long> {
    @Query("SELECT pd FROM ProductosDevoluciones pd WHERE pd.devolucion.id = :idDevolucion")
    ArrayList<ProductosDevoluciones> findByDevolucionId(Long idDevolucion);
}