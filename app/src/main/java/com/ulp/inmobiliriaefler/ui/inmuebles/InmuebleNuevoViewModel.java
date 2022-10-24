package com.ulp.inmobiliriaefler.ui.inmuebles;

import android.app.Application;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ulp.inmobiliriaefler.modelo.Inmueble;
import com.ulp.inmobiliriaefler.modelo.Tipo_Inmueble;
import com.ulp.inmobiliriaefler.request.ApiRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.Header;

public class InmuebleNuevoViewModel extends AndroidViewModel {

    private Context context;

    private MutableLiveData<Integer> mutableUso;
    private MutableLiveData<Integer> mutableTipo;


    public InmuebleNuevoViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }
  public LiveData<Integer> getMutableUso() {
        if(mutableUso == null) {
            mutableUso = new MutableLiveData<>();
        }
        return mutableUso;
    }
    public LiveData<Integer> getMutableTipo() {
        if(mutableTipo == null) {
            mutableTipo = new MutableLiveData<>();
        }
        return mutableTipo;
    }


    public void crearInmueble(Inmueble inmueble){
        if(inmueble.getDireccion().equals("")){
            Toast.makeText(context, "El campo domicilio no puede ser vacio.", Toast.LENGTH_SHORT).show();
        }
        if(inmueble.getUso() ==0 ){
            Toast.makeText(context, "El campo uso no puede ser vacio.", Toast.LENGTH_SHORT).show();
        }
        if (inmueble.getTipo() ==0){
            Toast.makeText(context, "El campo uso no puede ser vacio.", Toast.LENGTH_SHORT).show();
        }
        if (inmueble.getPropietario() ==0){
            Toast.makeText(context, "Por favor, vuelva a loguearse.", Toast.LENGTH_SHORT).show();
        }
        else {
            String token = ApiRetrofit.obtenerToken(context);
            Call<Inmueble> crearInmueblePromesa = ApiRetrofit.getServiceInmobiliaria().agregarInmueble(token, inmueble.getDireccion(), inmueble.getAmbientes(), inmueble.getPrecio(), inmueble.getLongitud(), inmueble.getLatitud(), inmueble.getUso(), inmueble.isEstado(), inmueble.getTipo(), inmueble.getPropietario(),inmueble.getImagen());
            crearInmueblePromesa.enqueue(new Callback<Inmueble>() {
                @Override
                public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "Se agrego el inmueble con exito.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "No hay respuesta.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Inmueble> call, Throwable t) {
                    Toast.makeText(context, "Ocurrio un error en el servidor.", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }
    public void cargarSpinerTipo(Spinner spinner, View view){
        String token = ApiRetrofit.obtenerToken(view.getContext());
        Call<Tipo_Inmueble[]> obtenerTiposPromesa = ApiRetrofit.getServiceInmobiliaria().obtenerTipoInmueble(token);
        obtenerTiposPromesa.enqueue(new Callback<Tipo_Inmueble[]>() {
            @Override
            public void onResponse(Call<Tipo_Inmueble[]> call, Response<Tipo_Inmueble[]> response) {
                if(response.isSuccessful()){
                    Tipo_Inmueble [] tipoInmuebleResponse= response.body();
                    String [] descripcionTipo=new String[tipoInmuebleResponse.length];
                    int [] idTipo=new int[tipoInmuebleResponse.length];
                    for(int i=0; i<tipoInmuebleResponse.length;i++){
                        idTipo[i]=tipoInmuebleResponse[i].id;
                        descripcionTipo[i]=tipoInmuebleResponse[i].descripcion;
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, descripcionTipo);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

                    spinner.setAdapter(adapter);

                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            mutableTipo.setValue(idTipo[position]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });}
            }

            @Override
            public void onFailure(Call<Tipo_Inmueble[]> call, Throwable t) {
                Toast.makeText(view.getContext(), "Error en el servidor", Toast.LENGTH_SHORT).show();

            }
        });

    }
    public void cargarSpinerUsos(Spinner spinner){

        String [] usos = new String [Inmueble.EnUsos.values().length];
        for(int i = 0; i< Inmueble.EnUsos.values().length; i++){
            usos[i]= Inmueble.EnUsos.values()[i].toString();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,usos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mutableUso.setValue(position+1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}