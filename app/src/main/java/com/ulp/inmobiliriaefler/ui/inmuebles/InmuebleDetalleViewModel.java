package com.ulp.inmobiliriaefler.ui.inmuebles;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.model.LatLng;
import com.ulp.inmobiliriaefler.modelo.Inmueble;
import com.ulp.inmobiliriaefler.request.ApiRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleDetalleViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData <Inmueble> mutableInmueble;
    private MutableLiveData<MostrarLocalizacion> mutableMostrarLocalizacion;


    public InmuebleDetalleViewModel(@NonNull Application application) {
        super(application);
        this.context=application.getApplicationContext();
    }
    public LiveData<Inmueble> getMutableInmueble() {
        if(mutableInmueble == null) {
            mutableInmueble = new MutableLiveData<>();
        }
        return mutableInmueble;
    }


    public void obtenerInmueble(Bundle bundle){
        mutableInmueble.setValue((Inmueble) bundle.getSerializable("inmueble"));
    }
    public void editarDisponibilidad(Boolean b){
       Inmueble inmueble= mutableInmueble.getValue();
       inmueble.setEstado(b);
       String token= ApiRetrofit.obtenerToken(context);
       Call<Inmueble> actualizarInmueblePromesa = ApiRetrofit.getServiceInmobiliaria().actualizarEstado(token,inmueble);
       actualizarInmueblePromesa.enqueue(new Callback<Inmueble>() {
           @Override
           public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
               if (response.isSuccessful()){
                   Toast.makeText(context, "Se edito la disponibilidad del inmueble.", Toast.LENGTH_SHORT).show();
               }
               else{
                   Toast.makeText(context, "No hay respuesta", Toast.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onFailure(Call<Inmueble> call, Throwable t) {
               Toast.makeText(context, "Ocurrio un error en el servidor.", Toast.LENGTH_SHORT).show();
           }
       });

    }



}