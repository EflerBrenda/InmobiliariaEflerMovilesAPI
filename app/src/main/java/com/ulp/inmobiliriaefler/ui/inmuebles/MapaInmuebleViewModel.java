package com.ulp.inmobiliriaefler.ui.inmuebles;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class MapaInmuebleViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<MarcarMapa> mutableMarcarMapa;


    public MapaInmuebleViewModel(@NonNull Application application) {
        super(application);
        this.context= application.getApplicationContext();
    }
    public LiveData<MarcarMapa> getMutableMarcarMapa(){
        if(mutableMarcarMapa==null){
            mutableMarcarMapa=new MutableLiveData<>();
        }
        return mutableMarcarMapa;
    }

    public void marcarMapa(){

        mutableMarcarMapa.setValue(new MarcarMapa(context));
    }
}