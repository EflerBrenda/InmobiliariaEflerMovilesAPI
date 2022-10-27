package com.ulp.inmobiliriaefler.ui.perfil;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.ulp.inmobiliriaefler.modelo.Propietario;
import com.ulp.inmobiliriaefler.request.ApiRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CambiarPasswordViewModel extends AndroidViewModel {
    private Context context;


    public CambiarPasswordViewModel(@NonNull Application application) {
        super(application);
        this.context= application.getApplicationContext();
    }

    public void cambiarPassword(String passActual,String passNueva,String passConfirmacion){
        if(passNueva.equals(passConfirmacion)){
        String token = ApiRetrofit.obtenerToken(context);
        Call<Propietario> cambiarPasswordPromesa = ApiRetrofit.getServiceInmobiliaria().cambiarPassword(token,passActual,passNueva);
        cambiarPasswordPromesa.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, "Se cambio la contraseña con exito.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context, "La contraseña actual ingresada no es correcta.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Toast.makeText(context, "Ocurrio un error en el servidor.", Toast.LENGTH_SHORT).show();
            }
        });
    }else{
        Toast.makeText(context, "Los campos no coinciden.", Toast.LENGTH_SHORT).show();
    }
}}