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

public class MapaInmuebleFragment extends Fragment {

    private MapaInmuebleViewModel mViewModel;

    public static MapaInmuebleFragment newInstance() {
        return new MapaInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(MapaInmuebleViewModel.class);
        View view= inflater.inflate(R.layout.fragment_mapa_inmueble, container, false);
        mViewModel.getMutableMarcarMapa().observe(getViewLifecycleOwner(), new Observer<MarcarMapa>() {
            @Override
            public void onChanged(MarcarMapa marcarMapa) {
                SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                        .findFragmentById(R.id.mapMarcarInmueble);
                mapFragment.getMapAsync(marcarMapa);
            }
        });
        mViewModel.marcarMapa();
        return view;
    }


}