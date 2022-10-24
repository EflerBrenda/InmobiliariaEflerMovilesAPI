package com.ulp.inmobiliriaefler.ui.inquilinos;

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
import com.ulp.inmobiliriaefler.ui.inmuebles.InmueblesAdapter;
import com.ulp.inmobiliriaefler.ui.inmuebles.InmueblesViewModel;

import java.util.List;

public class InquilinosFragment extends Fragment {

    private InquilinosViewModel vm;
    private RecyclerView rvAlquilados;
    private List<Inmueble> listaInmuebles;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        vm =new ViewModelProvider(this).get(InquilinosViewModel.class);
        View view = inflater.inflate(R.layout.fragment_inquilinos, container, false);

        vm.getMutableInmuebles().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                listaInmuebles= inmuebles;
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
                rvAlquilados.setLayoutManager(gridLayoutManager);
                InquilinosAdapter par = new InquilinosAdapter( listaInmuebles,getContext(), getLayoutInflater());
                rvAlquilados.setAdapter(par);
            }
        });
        inicializarVista(view);
        return view;
    }
    private void inicializarVista(View view){
        rvAlquilados =view.findViewById(R.id.rvAlquilados);
        vm.obtenerInmuebles();
    }


}