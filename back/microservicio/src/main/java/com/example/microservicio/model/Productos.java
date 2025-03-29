package com.example.microservicio.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

// Clase que representa cada uno de los usuarios de la aplicación
@Entity
@Table(name = "inventario")
public class Productos {
	// Id del usuario
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_producto;

    // Nombre del usuario, este ha de ser único
    @Column(unique = true)
    private String name;
    
    // Contraseña del usuario
    private double price;

    private int s;

    private int m;

    private int l;

    private int xl;

    @Column(length = 2048)
    private String image;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductosCarrito> carritos = new ArrayList<>();

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductosDevoluciones> pedidos;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductosDevoluciones> devoluciones;

    // Getters and Setters
    public Long getId_producto() {
        return id_producto;
    }

    public void setId_producto(Long id_producto) {
        this.id_producto = id_producto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getS() {
        return s;
    }

    public void setS(int s) {
        this.s = s;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getL() {
        return l;
    }

    public void setL(int l) {
        this.l = l;
    }

    public int getXL() {
        return xl;
    }

    public void setXL(int xl) {
        this.xl = xl;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<ProductosCarrito> getCarritos() {
        return carritos;
    }

    public void setCarritos(List<ProductosCarrito> carritos) {
        this.carritos = carritos;
    }

    public List<ProductosDevoluciones> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<ProductosDevoluciones> pedidos) {
        this.pedidos = pedidos;
    }

    public List<ProductosDevoluciones> getDevoluciones() {
        return devoluciones;
    }

    public void setDevoluciones(List<ProductosDevoluciones> devoluciones) {
        this.devoluciones = devoluciones;
    }
}