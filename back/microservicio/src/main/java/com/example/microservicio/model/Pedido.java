package com.example.microservicio.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Pedido {
    private Long id_pedido;

    private LocalDateTime fechaPedido;

    private double totalPedido;

    private ArrayList<ProductoPedido> productos;

    public Pedido() {}

    public Pedido(Long id_pedido, LocalDateTime fechaPedido, double totalPedido) {
        this.id_pedido = id_pedido;
        this.fechaPedido = fechaPedido;
        this.totalPedido = totalPedido;
    }

    public Long getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(Long id_pedido) {
        this.id_pedido = id_pedido;
    }

    public LocalDateTime getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDateTime fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public double getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(double totalPedido) {
        this.totalPedido = totalPedido;
    }

    public ArrayList<ProductoPedido> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<ProductoPedido> productos) {
        this.productos = productos;
    }
}
