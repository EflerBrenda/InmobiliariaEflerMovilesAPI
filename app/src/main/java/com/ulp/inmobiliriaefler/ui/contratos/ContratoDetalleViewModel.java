package com.ulp.inmobiliriaefler.ui.contratos;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulp.inmobiliriaefler.modelo.Contrato;

import com.ulp.inmobiliriaefler.modelo.Inmueble;
import com.ulp.inmobiliriaefler.request.ApiRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratoDetalleViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Contrato> mutableContrato;


    public ContratoDetalleViewModel(@NonNull Application application) {
        super(application);
        this.context=application.getApplicationContext();
    }
    public LiveData<Contrato> getMutableContrato() {
        if(mutableContrato == null) {
            mutableContrato = new MutableLiveData<>();
        }
        return mutableContrato;
    }

    public void obtenerContrato(Bundle bundle){
        Inmueble inmueble =(Inmueble) bundle.getSerializable("inmueble");
        String token = ApiRetrofit.obtenerToken(context);
        Call<Contrato> obtenerInquilinoPromesa = ApiRetrofit.getServiceInmobiliaria().obtenerContratosPorInmueble(token,inmueble.getIdInmueble());

        obtenerInquilinoPromesa.enqueue(new Callback<Contrato>() {
            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {

                if (response.isSuccessful()){
                    Contrato contratoActual= response.body();
                    mutableContrato.setValue(contratoActual);
                }
                else{
                    Toast.makeText(context, "Sin respuesta.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Contrato> call, Throwable t) {
                Log.d("salida",t.getMessage());
                Toast.makeText(context, "Ocurrio un error en el servidor.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}