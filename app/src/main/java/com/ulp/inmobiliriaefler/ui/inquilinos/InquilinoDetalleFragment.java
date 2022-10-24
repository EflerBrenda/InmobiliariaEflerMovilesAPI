package com.ulp.inmobiliriaefler.ui.inquilinos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ulp.inmobiliriaefler.R;
import com.ulp.inmobiliriaefler.modelo.Inquilino;

public class InquilinoDetalleFragment extends Fragment {

    private InquilinoDetalleViewModel vmDetalle;
    private TextView tvNombreInquilino,tvApellidoInquilino,tvDniInquilino,tvTelInquilino,tvEmailInquilino,tvTelGarante,tvGarante;

    public static InquilinoDetalleFragment newInstance() {
        return new InquilinoDetalleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vmDetalle = new ViewModelProvider(this).get(InquilinoDetalleViewModel.class);
        View view =inflater.inflate(R.layout.fragment_inquilino_detalle, container, false);
        vmDetalle.getMutableInquilino().observe(getViewLifecycleOwner(), new Observer<Inquilino>() {
            @Override
            public void onChanged(Inquilino inquilino) {

                tvNombreInquilino.setText(inquilino.getNombre());
                tvApellidoInquilino.setText(inquilino.getApellido());
                tvDniInquilino.setText(inquilino.getDNI()+"");
                tvTelInquilino.setText(inquilino.getTelefono());
                tvEmailInquilino.setText(inquilino.getEmail());
                tvTelGarante.setText(inquilino.getTelefonoGarante());
                tvGarante.setText(inquilino.getNombreGarante());
            }
        });
        inicializarVista(view);
        return view;
    }
    public void inicializarVista(View view){

        tvNombreInquilino= view.findViewById(R.id.tvFechaInicioContrato);
        tvApellidoInquilino= view.findViewById(R.id.tvFechaFinContrato);
        tvDniInquilino= view.findViewById(R.id.tvMontoAlquiler);
        tvTelInquilino= view.findViewById(R.id.tvInmuebleContrato);
        tvEmailInquilino= view.findViewById(R.id.tvInquilinoContrato);
        tvTelGarante= view.findViewById(R.id.tvTelGarante);
        tvGarante= view.findViewById(R.id.tvGarante);
        Bundle bundle = getArguments();
        vmDetalle.obtenerInquilino(bundle);
    }

}