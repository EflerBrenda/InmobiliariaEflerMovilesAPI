package com.ulp.inmobiliriaefler.ui.contratos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ulp.inmobiliriaefler.R;
import com.ulp.inmobiliriaefler.modelo.Contrato;
import com.ulp.inmobiliriaefler.modelo.Inquilino;
import com.ulp.inmobiliriaefler.ui.inquilinos.InquilinoDetalleFragment;
import com.ulp.inmobiliriaefler.ui.inquilinos.InquilinoDetalleViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ContratoDetalleFragment extends Fragment {

    private ContratoDetalleViewModel vmDetalle;
    private TextView tvCodigoContrato,tvFechaInicioContrato,tvMontoAlquiler,tvFechaFinContrato,tvInmuebleContrato,tvInquilinoContrato;
    private Button btPagos;

    public static ContratoDetalleFragment newInstance() {
        return new ContratoDetalleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vmDetalle = new ViewModelProvider(this).get(ContratoDetalleViewModel.class);
        View view =inflater.inflate(R.layout.fragment_contrato_detalle, container, false);
        vmDetalle.getMutableContrato().observe(getViewLifecycleOwner(), new Observer<Contrato>() {
            @Override
            public void onChanged(Contrato contrato) {

                tvCodigoContrato.setText(contrato.getIdContrato()+"");
                tvFechaInicioContrato.setText(contrato.getFechaInicio());
                tvMontoAlquiler.setText("$"+String.format("%.2f",contrato.getMontoAlquiler()));
                tvFechaFinContrato.setText(contrato.getFechaFin());
                tvInmuebleContrato.setText("Ubicado en: "+ contrato.getInmueble().getDireccion());
                tvInquilinoContrato.setText(contrato.getInquilino().getNombre()+" "+contrato.getInquilino().getApellido());
                btPagos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("contrato", contrato );
                        Navigation.findNavController(view).navigate(R.id.nav_pagos, bundle);
                    }
                });
            }
        });
        inicializarVista(view);
        return view;
    }
    public void inicializarVista(View view){

        tvCodigoContrato= view.findViewById(R.id.tvCodigoContrato);
        tvFechaInicioContrato= view.findViewById(R.id.tvFechaInicioContrato);
        tvMontoAlquiler= view.findViewById(R.id.tvMontoAlquiler);
        tvFechaFinContrato= view.findViewById(R.id.tvFechaFinContrato);
        tvInmuebleContrato= view.findViewById(R.id.tvInmuebleContrato);
        tvInquilinoContrato= view.findViewById(R.id.tvInquilinoContrato);
        btPagos= view.findViewById(R.id.btPagos);
        Bundle bundle = getArguments();
        vmDetalle.obtenerContrato(bundle);
    }

}