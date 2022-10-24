package com.ulp.inmobiliriaefler.ui.contratos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ulp.inmobiliriaefler.R;
import com.ulp.inmobiliriaefler.modelo.Inmueble;
import com.ulp.inmobiliriaefler.ui.inquilinos.InquilinosAdapter;
import com.ulp.inmobiliriaefler.ui.inquilinos.InquilinosViewModel;

import java.util.List;

public class ContratosFragment extends Fragment {

    private ContratosViewModel vm;
    private RecyclerView rvInmueblesContrato;
    private List<Inmueble> listaInmuebles;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        vm =new ViewModelProvider(this).get(ContratosViewModel.class);
        View view = inflater.inflate(R.layout.fragment_contratos, container, false);

        vm.getMutableInmuebles().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                listaInmuebles= inmuebles;
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
                rvInmueblesContrato.setLayoutManager(gridLayoutManager);
                ContratosAdapter par = new ContratosAdapter( listaInmuebles,getContext(), getLayoutInflater());
                rvInmueblesContrato.setAdapter(par);

            }
        });
        inicializarVista(view);
        return view;
    }
    private void inicializarVista(View view){
        rvInmueblesContrato =view.findViewById(R.id.rvInmueblesContrato);
        vm.obtenerInmuebles();
    }

}