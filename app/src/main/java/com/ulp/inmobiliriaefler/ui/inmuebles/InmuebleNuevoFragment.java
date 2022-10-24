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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.ulp.inmobiliriaefler.R;
import com.ulp.inmobiliriaefler.modelo.Inmueble;
import com.ulp.inmobiliriaefler.modelo.Propietario;
import com.ulp.inmobiliriaefler.modelo.Tipo_Inmueble;
import com.ulp.inmobiliriaefler.request.ApiRetrofit;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleNuevoFragment extends Fragment {

    private Button btAgregarInmueble;
    private Spinner spUso,spTipo;
    private EditText etDireccionNuevo,etLatitudNuevo,etLongitudNuevo,etPrecioNuevo,etAmbientes;
    private Switch swDisponibleNuevo;
    private ImageView ivInmuebleNuevo;
    private Integer idtipo=0;
    private Integer iduso=0;
    private InmuebleNuevoViewModel mViewModel;

    public static InmuebleNuevoFragment newInstance() {
        return new InmuebleNuevoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(InmuebleNuevoViewModel.class);
        View view = inflater.inflate(R.layout.fragment_inmueble_nuevo, container, false);
       mViewModel.getMutableTipo().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                idtipo= integer;
            }
        });
        mViewModel.getMutableUso().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                iduso= integer;
            }
        });

        inicializarVista(view);
        return view;
    }

    public void inicializarVista(View view){
        etDireccionNuevo= view.findViewById(R.id.etDireccionNuevo);
        etLatitudNuevo= view.findViewById(R.id.etLatitudNuevo);
        etLongitudNuevo= view.findViewById(R.id.etLongitudNuevo);
        etPrecioNuevo= view.findViewById(R.id.etPrecioNuevo);
        etAmbientes= view.findViewById(R.id.etAmbientes);
        swDisponibleNuevo= view.findViewById(R.id.swDisponibleNuevo);
        spUso= view.findViewById(R.id.spUso);
        spTipo= view.findViewById(R.id.spTipo);
        btAgregarInmueble= view.findViewById(R.id.btAgregarInmueble);
        ivInmuebleNuevo= view.findViewById(R.id.ivInmuebleNuevo);
        mViewModel.cargarSpinerTipo(spTipo,view);
        mViewModel.cargarSpinerUsos(spUso);
       /* ivInmuebleNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
        btAgregarInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String domicilio= etDireccionNuevo.getText().toString();
                Double latitud;
                Double longitud;
                Double precio;
                int ambientes;
                Boolean disponible= swDisponibleNuevo.isChecked();
                int uso=iduso;
                int tipo=idtipo;
                int propietario= ApiRetrofit.obtenerPropietarioActual(getContext());
                if(etAmbientes.getText().toString().equals("")){
                    Toast.makeText(view.getContext(), "Ingrese el campo ambiente.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    ambientes=Integer.parseInt(etAmbientes.getText().toString());
                }
                if(etLatitudNuevo.getText().toString().equals("")){
                    Toast.makeText(view.getContext(), "Ingrese el campo latitud.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    latitud=Double.parseDouble(etLatitudNuevo.getText().toString());
                }
                if(etLongitudNuevo.getText().toString().equals("")){
                    Toast.makeText(view.getContext(), "Ingrese el campo longitud.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    longitud=Double.parseDouble(etLongitudNuevo.getText().toString());
                }
                if(etPrecioNuevo.getText().toString().equals("")){
                    Toast.makeText(view.getContext(), "Ingrese el campo precio.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    precio=Double.parseDouble(etPrecioNuevo.getText().toString());
                }


                Inmueble inmueble= new Inmueble(0,domicilio,uso,tipo,ambientes,precio,longitud,latitud,propietario,disponible,null);
                mViewModel.crearInmueble(inmueble);
            }

        });




    }

}


