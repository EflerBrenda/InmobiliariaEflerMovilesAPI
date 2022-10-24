package com.ulp.inmobiliriaefler.modelo;

import java.io.Serializable;
import java.util.Objects;

public class Propietario implements Serializable {

    private int id;
    private String dni;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String telefono;
    private String avatar;

    public Propietario(){}
    public Propietario(int id, String nombre, String apellido,String dni, String telefono, String email, String clave,String Avatar) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = clave;
        this.telefono = telefono;
        this.avatar=avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return password;
    }

    public void setClave(String clave) {
        this.password = clave;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}

