package com.ulp.inmobiliriaefler.modelo;

import java.io.Serializable;
import java.util.Objects;

public class Usuario implements Serializable {

    private String Email;
    private String Password;


    public Usuario(){}
    public Usuario(String usuario, String clave) {
        this.Email= usuario;
        this.Password = clave;

    }
    public String getUsuario() {
        return Email;
    }

    public void setUsuario(String usuario) {
        this.Email = usuario;
    }

    public String getClave() {
        return Password;
    }

    public void setClave(String clave) {
        this.Password = clave;
    }

}

