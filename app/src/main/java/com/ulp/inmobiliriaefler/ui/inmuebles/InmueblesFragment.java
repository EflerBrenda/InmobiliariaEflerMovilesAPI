package com.ulp.inmobiliriaefler.ui.inmuebles;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ulp.inmobiliriaefler.R;
import com.ulp.inmobiliriaefler.modelo.Inmueble;

import java.util.List;


public class InmueblesFragment extends Fragment {

    private InmueblesViewModel vm;
    private RecyclerView rvInmuebles;
    private List<Inmueble> listaInmuebles;
    private FloatingActionButton btNuevoInmueble;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        vm =new ViewModelProvider(this).get(InmueblesViewModel.class);
        View view = inflater.inflate(R.layout.fragment_inmuebles, container, false);

        vm.getMutableInmuebles().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                listaInmuebles= inmuebles;
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
                rvInmuebles.setLayoutManager(gridLayoutManager);
                InmueblesAdapter par = new InmueblesAdapter( listaInmuebles,getContext(), getLayoutInflater());
                rvInmuebles.setAdapter(par);
            }
        });
        inicializarVista(view);
        return view;
    }
    private void inicializarVista(View view){
        rvInmuebles =view.findViewById(R.id.rvInmuebles);
        btNuevoInmueble= view.findViewById(R.id.btNuevoInmueble);
        btNuevoInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_nuevoinmueble);
            }
        });
        vm.obtenerInmuebles();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}