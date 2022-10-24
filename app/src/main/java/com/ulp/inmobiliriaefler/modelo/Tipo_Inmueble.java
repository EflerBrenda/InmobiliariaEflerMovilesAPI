package com.ulp.inmobiliriaefler.modelo;

public class Tipo_Inmueble {
    public int id;
    public String descripcion;

    public Tipo_Inmueble(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {

        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
