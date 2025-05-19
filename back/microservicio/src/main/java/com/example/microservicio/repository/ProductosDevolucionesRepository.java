package com.example.microservicio.repository;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.microservicio.model.ProductosDevoluciones;

@Repository
public interface ProductosDevolucionesRepository extends JpaRepository<ProductosDevoluciones, Long> {
    @Query("SELECT pd FROM ProductosDevoluciones pd WHERE pd.devolucion.id = :idDevolucion")
    ArrayList<ProductosDevoluciones> findByDevolucionId(Long idDevolucion);

    @Query("SELECT pd FROM ProductosDevoluciones pd WHERE pd.devolucion.id_devolucion = :id")
    Optional<ArrayList<ProductosDevoluciones>> getProductosDevoluciones(Long id);

    @Query("SELECT pd FROM ProductosDevoluciones pd WHERE pd.producto.id_producto = :id")
    Optional<ArrayList<ProductosDevoluciones>> getProductosDevolucionesByProductoId(Long id);
}