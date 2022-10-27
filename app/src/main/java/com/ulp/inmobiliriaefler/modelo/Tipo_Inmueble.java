package com.ulp.inmobiliriaefler.modelo;

import java.io.Serializable;

public class Tipo_Inmueble implements Serializable {
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
