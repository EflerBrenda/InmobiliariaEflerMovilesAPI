package com.ulp.inmobiliriaefler.modelo;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Contrato implements Serializable {

    private int id;
    private String fecha_Desde;
    private String fecha_Hasta;
    private Double monto_Alquiler;
    private Inquilino inquilino;
    private Inmueble inmueble;


    public Contrato() {}
    public Contrato(int idContrato, Date fechaInicio, Date fechaFin, double montoAlquiler, Inquilino inquilino, Inmueble inmueble) throws ParseException {
        this.id = idContrato;
        this.fecha_Desde = fechaInicio.toString();
        this.fecha_Hasta = fechaFin.toString();
        this.monto_Alquiler = montoAlquiler;
        this.inquilino = inquilino;
        this.inmueble = inmueble;
    }

    public int getIdContrato() {
        return id;
    }

    public void setIdContrato(int idContrato) {
        this.id = idContrato;
    }

    public String getFechaInicio() {
        return fecha_Desde;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fecha_Desde = fechaInicio;
    }

    public String getFechaFin() {
        return fecha_Hasta;
    }

    public void setFechaFin(String fechaFin) {
        this.fecha_Hasta = fechaFin;
    }

    public double getMontoAlquiler() {
        return monto_Alquiler;
    }

    public void setMontoAlquiler(double montoAlquiler) {
        this.monto_Alquiler = montoAlquiler;
    }


    public Inquilino getInquilino() {
        return inquilino;
    }

    public void setInquilino(Inquilino inquilino) {
        this.inquilino = inquilino;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }

}
