package com.example.microservicio.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Devolucion {
    private Long id_devolucion;

    private Long id_pedido;

    private LocalDateTime fechaDevolucion;

    private String motivoDevolucion;

    private boolean aceptada;

    private ArrayList<ProductoDevolucion> productosDevueltos;

    public Devolucion(Long id_devolucion, Long id_pedido, LocalDateTime fechaDevolucion, String motivoDevolucion, boolean aceptada) {
        this.id_devolucion = id_devolucion;
        this.id_pedido = id_pedido;
        this.fechaDevolucion = fechaDevolucion;
        this.motivoDevolucion = motivoDevolucion;
        this.aceptada = aceptada;
    }

    public Long getId_devolucion() {
        return id_devolucion;
    }

    public void setId_devolucion(Long id_devolucion) {
        this.id_devolucion = id_devolucion;
    }

    public Long getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(Long id_pedido) {
        this.id_pedido = id_pedido;
    }

    public LocalDateTime getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDateTime fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public String getMotivoDevolucion() {
        return motivoDevolucion;
    }

    public void setMotivoDevolucion(String motivoDevolucion) {
        this.motivoDevolucion = motivoDevolucion;
    }

    public boolean isAceptada() {
        return aceptada;
    }

    public void setAceptada(boolean aceptada) {
        this.aceptada = aceptada;
    }

    public ArrayList<ProductoDevolucion> getProductosDevueltos() {
        return productosDevueltos;
    }

    public void setProductosDevueltos(ArrayList<ProductoDevolucion> productosDevueltos) {
        this.productosDevueltos = productosDevueltos;
    }
}
