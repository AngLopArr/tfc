package com.example.microservicio.model;

public class ProductoPedido {
    private Long id_pedidos_productos;

    private Producto producto;

    private int cantidad;

    private String talla;

    public Long getId_pedidos_productos() {
        return id_pedidos_productos;
    }

    public void setId_pedidos_productos(Long id_pedidos_productos) {
        this.id_pedidos_productos = id_pedidos_productos;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
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
