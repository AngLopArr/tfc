package com.example.microservicio.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedidos_productos")
public class PedidosProductos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_pedidos_productos;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedidos pedido;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Productos producto;

    private int cantidad;

    private String talla;
    
    // Getters y Setters
    public Long getId_pedidos_productos() {
        return id_pedidos_productos;
    }

    public void setId_pedidos_productos(Long id_pedidos_productos) {
        this.id_pedidos_productos = id_pedidos_productos;
    }

    public Pedidos getPedido() {
        return pedido;
    }

    public void setPedido(Pedidos pedido) {
        this.pedido = pedido;
    }

    public Productos getProducto() {
        return producto;
    }

    public void setProducto(Productos producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }
}