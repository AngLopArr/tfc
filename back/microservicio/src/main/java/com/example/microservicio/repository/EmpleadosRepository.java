package com.example.microservicio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.microservicio.model.Empleados;

@Repository
public interface EmpleadosRepository extends JpaRepository<Empleados, Long> {
    Empleados findByEmail(String email);
}