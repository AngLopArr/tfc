package com.example.microservicio.model;

public class Producto {
    private Long id_producto;

    private String name;
    
    private double price;

    private int s;

    private int m;

    private int l;

    private int xl;

    private String image;

    public Producto(Long id_producto, String name, double price, int s, int m, int l, int xl, String image) {
        this.id_producto = id_producto;
        this.name = name;
        this.price = price;
        this.s = s;
        this.m = m;
        this.l = l;
        this.xl = xl;
        this.image = image;
    }

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

    public int getXl() {
        return xl;
    }

    public void setXl(int xl) {
        this.xl = xl;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
