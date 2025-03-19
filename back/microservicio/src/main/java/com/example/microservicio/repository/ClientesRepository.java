package com.example.microservicio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.microservicio.model.Clientes;

@Repository
public interface ClientesRepository extends JpaRepository<Clientes, Long> {
    Clientes findByUsername(String username);

    Clientes findByEmail(String email);
}