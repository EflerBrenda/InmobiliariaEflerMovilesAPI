package com.ulp.inmobiliriaefler.ui.inmuebles;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.SupportMapFragment;
import com.ulp.inmobiliriaefler.R;

public class MostrarUbicacionInmuebleFragment extends Fragment {

    private MostrarUbicacionInmuebleViewModel mViewModel;

    public static MostrarUbicacionInmuebleFragment newInstance() {
        return new MostrarUbicacionInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(MostrarUbicacionInmuebleViewModel.class);
        View view= inflater.inflate(R.layout.fragment_mostrar_ubicacion_inmueble, container, false);
        mViewModel.getMutableMostrarLocalizacion().observe(getViewLifecycleOwner(), new Observer<MostrarLocalizacion>() {
            @Override
            public void onChanged(MostrarLocalizacion mostrarLocalizacion) {
                SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                        .findFragmentById(R.id.mapUbicacionInmueble);
                mapFragment.getMapAsync(mostrarLocalizacion);
            }
        });
        Bundle inmueble = getArguments();
        mViewModel.mostrarLocalizacion(inmueble);
        return view;
    }



}