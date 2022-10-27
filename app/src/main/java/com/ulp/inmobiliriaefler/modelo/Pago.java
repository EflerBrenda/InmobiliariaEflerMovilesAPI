package com.ulp.inmobiliriaefler.modelo;

import android.util.Log;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Pago implements Serializable {

    private int id;
    private String numero_pago;
    private int contratoId;
    private double importe;
    private String fecha_pago;

    public Pago() {}

    public Pago(int idPago, String numero, int contrato, double importe, Date fechaDePago) {
        this.id = idPago;
        this.numero_pago = numero;
        this.contratoId = contrato;
        this.importe = importe;
        this.fecha_pago = fechaDePago.toString();
    }

    public int getIdPago() {
        return id;
    }

    public void setIdPago(int idPago) {
        this.id = idPago;
    }

    public String getNumero() {
        return numero_pago;
    }

    public void setNumero(String numero) {
        this.numero_pago = numero;
    }

    public int getContrato() {
        return contratoId;
    }

    public void setContrato(int contrato) {
        this.contratoId = contrato;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public String getFechaDePago() {
        return convertirFecha(fecha_pago);
    }

    public void setFechaDePago(String fechaDePago) {
        this.fecha_pago = fechaDePago;
    }

    public String convertirFecha(String fecha){
        String dia="";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date d = dateFormat.parse(fecha);

            dia = formato.format(d);

            Log.d("salida",dia);
        } catch (
                ParseException e) {
            e.printStackTrace();
            Log.d("salida",e.getMessage());
        }
        return dia;
    }
}
