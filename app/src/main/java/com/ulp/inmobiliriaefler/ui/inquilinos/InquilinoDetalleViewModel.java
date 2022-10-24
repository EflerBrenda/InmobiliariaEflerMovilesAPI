package com.ulp.inmobiliriaefler.ui.inquilinos;

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
import com.ulp.inmobiliriaefler.modelo.Inquilino;
import com.ulp.inmobiliriaefler.request.ApiRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquilinoDetalleViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<Inquilino> mutableInquilino;


    public InquilinoDetalleViewModel(@NonNull Application application) {
        super(application);
        this.context=application.getApplicationContext();
    }
    public LiveData<Inquilino> getMutableInquilino() {
        if(mutableInquilino == null) {
            mutableInquilino = new MutableLiveData<>();
        }
        return mutableInquilino;
    }

    public void obtenerInquilino(Bundle bundle){
        Inmueble inmueble =(Inmueble) bundle.getSerializable("inmueble");
        String token = ApiRetrofit.obtenerToken(context);
        Call<Contrato> obtenerInquilinoPromesa = ApiRetrofit.getServiceInmobiliaria().obtenerContratosPorInmueble(token,inmueble.getIdInmueble());

        obtenerInquilinoPromesa.enqueue(new Callback<Contrato>() {
            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {

                if (response.isSuccessful()){
                    Inquilino inquilinoActual= response.body().getInquilino();
                    mutableInquilino.setValue(inquilinoActual);
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