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

    @Query("SELECT p FROM Productos p ORDER BY p.id_producto LIMIT 4 OFFSET :offset")
    Optional<ArrayList<Productos>> get4Productos(int offset);

    @Query("SELECT p FROM Productos p WHERE p.name LIKE CONCAT('%', CONCAT(:name, '%')) ORDER BY p.id_producto LIMIT 5 OFFSET :offset")
    Optional<ArrayList<Productos>> get4ProductosByName(String name, int offset);

    @Query("SELECT COUNT(p.id_producto) FROM Productos p WHERE p.name LIKE CONCAT('%', CONCAT(:name, '%'))")
    int getTotalProductosByName(String name);
}