package com.example.microservicio.repository;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.microservicio.model.Productos;

@Repository
public interface ProductosRepository extends JpaRepository<Productos, Long> {
    Optional<Productos> findByName(String name);

    @Query("SELECT p FROM Productos p ORDER BY p.id_producto LIMIT 5 OFFSET :offset")
    Optional<ArrayList<Productos>> get10Productos(int offset);
}