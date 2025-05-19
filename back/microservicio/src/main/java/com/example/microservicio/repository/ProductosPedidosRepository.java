package com.example.microservicio.repository;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.microservicio.model.ProductosPedidos;

@Repository
public interface ProductosPedidosRepository extends JpaRepository<ProductosPedidos, Long> {
    @Query("SELECT SUM(pp.producto.price * pp.cantidad) FROM ProductosPedidos pp WHERE pp.pedido.id = :idPedido")
    double calcularTotalPedido(Long idPedido);

    @Query("SELECT pp FROM ProductosPedidos pp WHERE pp.pedido.id = :id")
    Optional<ArrayList<ProductosPedidos>> getProductosPedidos(Long id);

    @Query("SELECT pp FROM ProductosPedidos pp WHERE pp.producto.id_producto = :id")
    Optional<ArrayList<ProductosPedidos>> getProductosPedidosByProductoId(Long id);
}