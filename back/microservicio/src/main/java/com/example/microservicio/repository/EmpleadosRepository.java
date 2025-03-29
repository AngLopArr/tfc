package com.example.microservicio.repository;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.microservicio.model.Empleados;

@Repository
public interface EmpleadosRepository extends JpaRepository<Empleados, Long> {
    Optional<Empleados> findByEmail(String email);

    @Query("SELECT e FROM Empleados e ORDER BY e.id_empleado LIMIT 5 OFFSET :offset")
    Optional<ArrayList<Empleados>> get10Employees(int offset);
}