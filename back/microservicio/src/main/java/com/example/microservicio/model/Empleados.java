package com.example.microservicio.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Clase que representa cada uno de los mensajes cifrados entre usuarios: esta clase existe como entidad en la base de datos que almacena la información de 
// cada mensaje, su contenido cifrado, la clave que lo descifra y una firma digital para confirmar su integridad y la identidad del remitente
@Entity
@Table(name = "empleados")
public class Empleados {
	// Id del mensaje
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_empleado;

    @Column(unique=true)
    private String email;

    private String name;

    private String role;

    private String password;

    // Getters y setters
    public Long getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(Long id_empleado) {
        this.id_empleado = id_empleado;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}