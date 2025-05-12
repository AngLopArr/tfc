package com.example.microservicio.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Query("SELECT d FROM Devoluciones d ORDER BY d.fechaDevolucion LIMIT :limit OFFSET :offset")
    Optional<ArrayList<Devoluciones>> getDevoluciones(int limit, int offset);

    @Query("SELECT d FROM Devoluciones d WHERE d.fechaDevolucion BETWEEN :fechaInicio AND :fechaFin ORDER BY d.fechaDevolucion LIMIT :limit OFFSET :offset")
    Optional<ArrayList<Devoluciones>> getDevolucionesByDate(LocalDateTime fechaInicio, LocalDateTime fechaFin, int limit, int offset);

    @Query("SELECT COUNT(d.id_devolucion) FROM Devoluciones d")
    int getTotalDevoluciones();

    @Query("SELECT COUNT(d.id_devolucion) FROM Devoluciones d WHERE d.fechaDevolucion BETWEEN :fechaInicio AND :fechaFin")
    int getTotalDevolucionesByDate(LocalDateTime fechaInicio, LocalDateTime fechaFin);
}