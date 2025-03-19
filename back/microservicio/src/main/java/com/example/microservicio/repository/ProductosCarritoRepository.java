package com.example.microservicio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.microservicio.model.Productos;
import com.example.microservicio.model.ProductosCarrito;

@Repository
public interface ProductosCarritoRepository extends JpaRepository<ProductosCarrito, Long> {
    @Modifying
    @Query("DELETE FROM Carrito c WHERE c.cliente.id = :clienteId")
    void eliminarProductosDelCarrito(@Param("clienteId") Long clienteId);

    @Query("SELECT c.producto FROM Carrito c WHERE c.cliente.id = :idCliente")
    List<Productos> findProductosByClienteId(@Param("idCliente") Long idCliente);

    @Modifying
    @Query("DELETE FROM Carrito c WHERE c.cliente.id = :clienteId AND c.producto.id = :productoId AND c.talla = :talla")
    void eliminarProductoDelCarritoPorClienteYProductoYTalla(@Param("clienteId") Long clienteId, @Param("productoId") Long productoId, @Param("talla") String talla);

    @Query("SELECT c FROM Carrito c WHERE c.cliente.id = :idCliente AND c.producto.id = :idProducto AND c.talla = :talla")
    Optional<ProductosCarrito> findByClienteIdProductoIdAndTalla(@Param("idCliente") Long idCliente, @Param("idProducto") Long idProducto, @Param("talla") String talla);
}