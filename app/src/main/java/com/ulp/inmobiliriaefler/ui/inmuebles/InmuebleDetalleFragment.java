package com.ulp.inmobiliriaefler.ui.inmuebles;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.ulp.inmobiliriaefler.R;
import com.ulp.inmobiliriaefler.modelo.Inmueble;

public class InmuebleDetalleFragment extends Fragment {

    private InmuebleDetalleViewModel vmDetalle;
    private TextView tvCodigoDetalle,tvDireccionDetalle,tvUsoDetalle,tvTipoDetalle,tvAmbientesDetalle,tvPrecioDetalle;
    private Switch cbDisponible;
    private ImageView ivAvatarDetalle;
    private Button btVerUbicacion;


    public static InmuebleDetalleFragment newInstance() {
        return new InmuebleDetalleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vmDetalle = new ViewModelProvider(this).get(InmuebleDetalleViewModel.class);
        View view =inflater.inflate(R.layout.fragment_inmueble_detalle, container, false);
        vmDetalle.getMutableInmueble().observe(getViewLifecycleOwner(), new Observer<Inmueble>() {
            @Override
            public void onChanged(Inmueble inmueble) {
                tvCodigoDetalle.setText(inmueble.getIdInmueble()+"");
                tvDireccionDetalle.setText(inmueble.getDireccion());
                int uso = inmueble.getUso() -1;
                tvUsoDetalle.setText(Inmueble.EnUsos.values()[uso].toString());
                tvTipoDetalle.setText(inmueble.getTipoInmueble().getDescripcion());
                tvAmbientesDetalle.setText(inmueble.getAmbientes()+"");
                tvPrecioDetalle.setText("$"+String.format("%.2f",inmueble.getPrecio()));
                cbDisponible.setChecked(inmueble.isEstado());
                cbDisponible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        vmDetalle.editarDisponibilidad(b);
                    }
                });
                Glide.with(getContext())
                        .load(inmueble.getImagen())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(ivAvatarDetalle);
            }

        });

        inicializarVista(view);
        return view;
    }
    public void inicializarVista(View view){
        tvCodigoDetalle= view.findViewById(R.id.tvCodigoDetalle);
        tvDireccionDetalle= view.findViewById(R.id.tvDireccionDetalle);
        tvUsoDetalle= view.findViewById(R.id.tvUsoDetalle);
        tvTipoDetalle= view.findViewById(R.id.tvTipoDetalle);
        tvAmbientesDetalle= view.findViewById(R.id.tvAmbientesDetalle);
        tvPrecioDetalle= view.findViewById(R.id.tvPrecioDetalle);
        cbDisponible= view.findViewById(R.id.cbDisponible);
        ivAvatarDetalle= view.findViewById(R.id.ivInmuebleNuevo);
        btVerUbicacion= view.findViewById(R.id.btVerUbicacion);


        Bundle bundle = getArguments();
        vmDetalle.obtenerInmueble(bundle);
        btVerUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_ubicacionInmueble,bundle);
            }
        });

    }

}