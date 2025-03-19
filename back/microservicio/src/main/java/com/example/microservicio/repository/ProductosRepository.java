package com.example.microservicio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.microservicio.model.Productos;

@Repository
public interface ProductosRepository extends JpaRepository<Productos, Long> {
    Productos findByName(String name);
}