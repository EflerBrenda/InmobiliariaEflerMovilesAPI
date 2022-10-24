package com.ulp.inmobiliriaefler.ui.inquilinos;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulp.inmobiliriaefler.modelo.Contrato;
import com.ulp.inmobiliriaefler.modelo.Inmueble;
import com.ulp.inmobiliriaefler.request.ApiRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquilinosViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<List<Inmueble>> mutableInmuebles;


    public InquilinosViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<List<Inmueble>> getMutableInmuebles() {
        if(mutableInmuebles==null){
            mutableInmuebles=new MutableLiveData<>();
        }
        return mutableInmuebles;
    }

    public void obtenerInmuebles(){

        String token = ApiRetrofit.obtenerToken(context);
        Call<List<Contrato>> obtenerContratosVigentesPromesa = ApiRetrofit.getServiceInmobiliaria().ContratosVigentes(token);
        obtenerContratosVigentesPromesa.enqueue(new Callback<List<Contrato>>() {
            @Override
            public void onResponse(Call<List<Contrato>> call, Response<List<Contrato>> response) {
                if(response.isSuccessful()){
                    List<Contrato> listacontratos = response.body();
                    List<Inmueble>listaInmuebles=new ArrayList<Inmueble>(listacontratos.size());
                    for (Contrato contrato:listacontratos
                         ) {
                        listaInmuebles.add(contrato.getInmueble());
                    }
                    mutableInmuebles.setValue(listaInmuebles);
                }
                else{
                    Toast.makeText(context, "Sin respuesta.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Contrato>> call, Throwable t) {
                Log.d("salida",t.getMessage());
                Toast.makeText(context, "Ocurrio un error en el servidor.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}