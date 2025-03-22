package com.example.microservicio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.microservicio.model.Productos;

@Repository
public interface ProductosRepository extends JpaRepository<Productos, Long> {
    Optional<Productos> findByName(String name);
}