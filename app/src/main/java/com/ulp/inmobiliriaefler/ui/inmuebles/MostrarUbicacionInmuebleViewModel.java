package com.ulp.inmobiliriaefler.ui.inmuebles;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ulp.inmobiliriaefler.modelo.Inmueble;

public class MostrarUbicacionInmuebleViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<MostrarLocalizacion> mutableMostrarLocalizacion;

    public MostrarUbicacionInmuebleViewModel(@NonNull Application application) {
        super(application);
        this.context= application.getApplicationContext();

    }

    public LiveData<MostrarLocalizacion> getMutableMostrarLocalizacion() {
        if(mutableMostrarLocalizacion == null) {
            mutableMostrarLocalizacion = new MutableLiveData<>();
        }
        return mutableMostrarLocalizacion;
    }
    public void mostrarLocalizacion(Bundle inm){
        Inmueble inmueble=(Inmueble) inm.getSerializable("inmueble");
        mutableMostrarLocalizacion.setValue(new MostrarLocalizacion(context,inmueble));
    }
}