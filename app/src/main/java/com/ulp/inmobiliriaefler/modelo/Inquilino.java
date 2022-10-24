package com.ulp.inmobiliriaefler.modelo;

import java.io.Serializable;

public class Inquilino implements Serializable {

    private int id;
    private String dni;
    private String nombre;
    private String apellido;
    //private String lugarDeTrabajo;
    private String email;
    private String telefono;
    private String nombre_garante;
    private String telefono_garante;

    public Inquilino() {}

    public Inquilino(int idInquilino, String DNI, String nombre, String apellido,String email, String telefono,String nombreGarante,String telefonoGarante) {
        this.id = idInquilino;
        this.dni = DNI;
        this.nombre = nombre;
        this.apellido = apellido;

        this.email = email;
        this.telefono = telefono;
        this.nombre_garante = nombreGarante;
        this.telefono_garante = telefonoGarante;
    }

    public int getIdInquilino() {
        return id;
    }

    public void setIdInquilino(int idInquilino) {
        this.id = idInquilino;
    }

    public String getDNI() {
        return dni;
    }

    public void setDNI(String DNI) {
        this.dni = DNI;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombreGarante() {
        return nombre_garante;
    }

    public void setNombreGarante(String nombreGarante) {
        this.nombre_garante = nombreGarante;
    }

    public String getTelefonoGarante() {
        return telefono_garante;
    }

    public void setTelefonoGarante(String telefonoGarante) {
        this.telefono_garante = telefonoGarante;
    }
}

