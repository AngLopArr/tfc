package com.example.microservicio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.microservicio.model.Devoluciones;

@Repository
public interface DevolucionesRepository extends JpaRepository<Devoluciones, Long> {
    @Query("SELECT d FROM Devoluciones d WHERE d.cliente.id_cliente = :idCliente")
    Optional<List<Devoluciones>> findDevolucionesByClienteId(Long idCliente);
}