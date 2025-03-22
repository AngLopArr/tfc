package com.example.microservicio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.microservicio.model.Clientes;

@Repository
public interface ClientesRepository extends JpaRepository<Clientes, Long> {
    Optional<Clientes> findByUsername(String username);

    Optional<Clientes> findByEmail(String email);
}