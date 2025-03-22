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
    @Query("DELETE FROM Carrito c WHERE c.cliente.id = :clienteId")
    void eliminarProductosDelCarrito(Long clienteId);

    @Query("SELECT c FROM Carrito c WHERE c.cliente.id = :idCliente")
    Optional<List<ProductosCarrito>> findProductosByClienteId(Long idCliente);

    @Query("SELECT c FROM Carrito c WHERE c.cliente.id = :idCliente AND c.producto.id = :idProducto AND c.talla = :talla")
    Optional<ProductosCarrito> findByClienteIdProductoIdAndTalla(Long idCliente, Long idProducto, String talla);
}