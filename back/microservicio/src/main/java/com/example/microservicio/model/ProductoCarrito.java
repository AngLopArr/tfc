package com.example.microservicio.model;

import java.time.LocalDateTime;

public class ProductoCarrito {
    private Long id;

    private Producto producto;

    private String talla;

    private int cantidad;

    private LocalDateTime fechaAgregado;

    public ProductoCarrito() {}

    public ProductoCarrito(Long id, Producto producto, String talla, int cantidad, LocalDateTime fechaAgregado) {
        this.id = id;
        this.producto = producto;
        this.talla = talla;
        this.cantidad = cantidad;
        this.fechaAgregado = fechaAgregado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDateTime getFechaAgregado() {
        return fechaAgregado;
    }

    public void setFechaAgregado(LocalDateTime fechaAgregado) {
        this.fechaAgregado = fechaAgregado;
    }
}
