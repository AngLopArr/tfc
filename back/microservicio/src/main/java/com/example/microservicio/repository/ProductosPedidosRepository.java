package com.example.microservicio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.microservicio.model.ProductosPedidos;

@Repository
public interface ProductosPedidosRepository extends JpaRepository<ProductosPedidos, Long> {
    @Query("SELECT SUM(pp.producto.precio * pp.cantidad) FROM ProductoPedido pp WHERE pp.pedido.id = :idPedido")
    double calcularTotalPedido(Long idPedido);
}