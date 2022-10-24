package com.ulp.inmobiliriaefler.ui.home;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.ulp.inmobiliriaefler.modelo.Propietario;

public class HomeViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<LeerMapa> mutableLeerMapa;


    public HomeViewModel(@NonNull Application application) {
        super(application);
        this.context= application.getApplicationContext();
    }
    public LiveData<LeerMapa> getMutableLeerMapa(){
        if(mutableLeerMapa==null){
            mutableLeerMapa=new MutableLiveData<>();
        }
        return mutableLeerMapa;
    }

    public void leerMapa(){

       mutableLeerMapa.setValue(new LeerMapa(context));
    }


}