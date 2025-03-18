package com.example.microservicio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.microservicio.model.ProductosPedidos;

@Repository
public interface ProductosPedidosRepository extends JpaRepository<ProductosPedidos, Long> {}