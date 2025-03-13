package com.example.microservicio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.microservicio.model.Productos;

// Repositorio correspondiente a las operaciones contra la tabla contenedora de los usuarios en la base de datos, contiene tanto operaciones genéricas 
// por defecto como un método específico creado para el microservicio
@Repository
public interface UserRepository extends JpaRepository<Productos, Long> {
    // Este método devuelve el usuario correspondiente a un username concreto
    Productos findByUsername(String username);
}