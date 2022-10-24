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
import com.ulp.inmobiliriaefler.modelo.Pago;
import com.ulp.inmobiliriaefler.request.ApiRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagoViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<List<Pago>> mutablePagos;


    public PagoViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<List<Pago>> getMutablePagos() {
        if(mutablePagos==null){
            mutablePagos=new MutableLiveData<>();
        }
        return mutablePagos;
    }

    public void obtenerPagos(Bundle bundle){
        Contrato contrato =(Contrato) bundle.getSerializable("contrato");
        String token= ApiRetrofit.obtenerToken(context);
        Call<List<Pago>> obtenerPagosPromesa = ApiRetrofit.getServiceInmobiliaria().obtenerPagos(token,contrato.getIdContrato());
        obtenerPagosPromesa.enqueue(new Callback<List<Pago>>() {
            @Override
            public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                if(response.isSuccessful()){
                    List<Pago> pagos= response.body();
                    mutablePagos.setValue(pagos);
                }
                else {
                    Toast.makeText(context, "Sin respuesta.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Pago>> call, Throwable t) {
                Log.d("salida",t.getMessage());
                Toast.makeText(context, "Ocurrio un error en el servidor.", Toast.LENGTH_SHORT).show();
            }
        });


    }
}