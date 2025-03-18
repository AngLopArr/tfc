package com.example.microservicio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.microservicio.model.ProductosDevoluciones;

@Repository
public interface ProductosDevolucionesRepository extends JpaRepository<ProductosDevoluciones, Long> {}