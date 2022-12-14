package com.ulp.inmobiliriaefler.modelo;

import com.ulp.inmobiliriaefler.request.ApiRetrofit;

import java.io.Serializable;
import java.util.Objects;

public class Inmueble implements Serializable {

    public enum EnUsos {
        //UpperCase
        Residencial,
        Comercial
    }
    private int id;
    private String direccion;
    private int uso;
    private int tipoInmuebleId;
    private Tipo_Inmueble tipoInmueble;
    private int ambientes;
    private String longitud;
    private String latitud;
    private Double precio;
    private int propietarioId;
    private boolean oferta_activa;
    private String imagen;


    public Inmueble(int idInmueble, String direccion, int uso, int tipo, int ambientes, Double precio, String longitud, String latitud, int propietario, boolean estado, String imagen,Tipo_Inmueble ti) {
        this.id = idInmueble;
        this.direccion = direccion;
        this.uso = uso;
        this.tipoInmuebleId = tipo;
        this.ambientes = ambientes;
        this.precio = precio;
        this.longitud= longitud;
        this.latitud=latitud;
        this.propietarioId = propietario;
        this.oferta_activa = estado;
        this.imagen = imagen;
        this.tipoInmueble=ti;
    }
    public Inmueble() {

    }
    public int getIdInmueble() {
        return id;
    }

    public void setIdInmueble(int idInmueble) {
        this.id = idInmueble;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getUso() {
        return uso;
    }

    public void setUso(int uso) {
        this.uso = uso;
    }

    public int getTipo() {
        return tipoInmuebleId;
    }

    public Tipo_Inmueble getTipoInmueble() {
        return tipoInmueble;
    }

    public void setTipoInmueble(Tipo_Inmueble tipoInmueble) {
        this.tipoInmueble = tipoInmueble;
    }


    public void setTipo(int tipo) {
        this.tipoInmuebleId = tipo;
    }

    public int getAmbientes() {
        return ambientes;
    }

    public void setAmbientes(int ambientes) {
        this.ambientes = ambientes;
    }

    public Double getPrecio() {
        return precio;
    }


    public void setPrecio(Double precio) {
        this.precio = precio;
    }
    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public int getPropietario() {
        return propietarioId;
    }

    public void setPropietario(int propietario) {
        this.propietarioId = propietario;
    }

    public boolean isEstado() {
        return oferta_activa;
    }

    public void setEstado(boolean estado) {
        this.oferta_activa = estado;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

}
