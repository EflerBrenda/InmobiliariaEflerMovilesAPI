package com.ulp.inmobiliriaefler.ui.perfil;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulp.inmobiliriaefler.modelo.Propietario;
import com.ulp.inmobiliriaefler.request.ApiRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<Propietario> mPropietario;
    private MutableLiveData<Boolean> propiedadEnable;
    private MutableLiveData<String> textoBoton;
    private MutableLiveData<String> mutableMensaje;



    public PerfilViewModel(@NonNull Application application) {
        super(application);
        this.context= application.getApplicationContext();

    }
    public LiveData<String> getMutableMensaje() {
        if(mutableMensaje==null){
            mutableMensaje=new MutableLiveData<>();
        }
        return mutableMensaje;
    }
    public LiveData<Propietario> getMutablePropietario(){
        if(mPropietario==null){
            mPropietario=new MutableLiveData<>();
        }
        return mPropietario;
    }
    public LiveData<Boolean> getMutablePropiedadEnable(){
        if(propiedadEnable==null){
            propiedadEnable=new MutableLiveData<>();
        }
        return propiedadEnable;
    }
    public LiveData<String> getMutableTextoBoton(){
        if(textoBoton==null){
            textoBoton=new MutableLiveData<>();
        }
        return textoBoton;
    }

    public void ObtenerUsuario(){
        String token=ApiRetrofit.obtenerToken(context);
        Call<Propietario> obtenerPerfilPromesa = ApiRetrofit.getServiceInmobiliaria().obtenerPerfil(token);
        obtenerPerfilPromesa.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                mPropietario.postValue(response.body());
                propiedadEnable.postValue(false);
                textoBoton.postValue("EDITAR");
                mutableMensaje.postValue("");
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {

                Toast.makeText(context, "Ocurrio un error en el servidor.", Toast.LENGTH_SHORT).show();
            }
    });}

    public void actualizarPropietario(String boton, Propietario p){
        if(boton.equals("GUARDAR"))
        {

            if(p.getApellido().equals("") || p.getNombre().equals("") || p.getTelefono().equals("") || p.getDni().toString().equals("0")){
                mutableMensaje.postValue("Por favor, complete todos los campos.");
            }
            else{
                String token=ApiRetrofit.obtenerToken(context);
                Call <Propietario> actualizarPerfilPromesa =ApiRetrofit.getServiceInmobiliaria().actualizarPerfil(token,p.getId(),p.getDni(),p.getNombre(),p.getApellido(),p.getEmail(),p.getClave(),p.getTelefono());
                actualizarPerfilPromesa.enqueue(new Callback<Propietario>() {
                    @Override
                    public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                        if(response.isSuccessful()){

                        propiedadEnable.postValue(false);
                        textoBoton.postValue("EDITAR");
                        mutableMensaje.postValue("");
                        }
                    else{
                            Toast.makeText(context, "No hay respuesta.", Toast.LENGTH_SHORT).show();
                    }}

                    @Override
                    public void onFailure(Call<Propietario> call, Throwable t) {
                        Toast.makeText(context, "Ocurrio un error en el servidor.", Toast.LENGTH_SHORT).show();
                    }
                });

            }

        }
        else{
            propiedadEnable.setValue(true);
            textoBoton.setValue("GUARDAR");
        }
    }


}