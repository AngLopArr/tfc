package com.example.microservicio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.microservicio.model.Pedidos;

@Repository
public interface PedidosRepository extends JpaRepository<Pedidos, Long> {
    @Query("SELECT p FROM Pedidos p WHERE p.cliente.id_cliente = :idCliente")
    Optional<List<Pedidos>> findPedidosByClienteId(Long idCliente);
}