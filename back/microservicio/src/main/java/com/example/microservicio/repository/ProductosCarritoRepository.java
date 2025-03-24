package com.example.microservicio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.microservicio.model.ProductosCarrito;

@Repository
public interface ProductosCarritoRepository extends JpaRepository<ProductosCarrito, Long> {
    @Modifying
    @Query("DELETE FROM ProductosCarrito pc WHERE pc.cliente.id_cliente = :clienteId")
    void eliminarProductosDelCarrito(Long clienteId);

    @Query("SELECT pc FROM ProductosCarrito pc WHERE pc.cliente.id_cliente = :idCliente")
    Optional<List<ProductosCarrito>> findProductosByClienteId(Long idCliente);

    @Query("SELECT pc FROM ProductosCarrito pc WHERE pc.cliente.id_cliente = :idCliente AND pc.producto.id_producto = :idProducto AND pc.talla = :talla")
    Optional<ProductosCarrito> findByClienteIdProductoIdAndTalla(Long idCliente, Long idProducto, String talla);
}