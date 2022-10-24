package com.ulp.inmobiliriaefler.ui.inmuebles;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulp.inmobiliriaefler.modelo.Inmueble;
import com.ulp.inmobiliriaefler.request.ApiRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmueblesViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<List<Inmueble>> mutableInmuebles;
    //private ApiClient api= ApiClient.getApi();

    public InmueblesViewModel(@NonNull Application application) {
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
        String token= ApiRetrofit.obtenerToken(context);
        Call<List<Inmueble>> obtenerInmueblePromesa = ApiRetrofit.getServiceInmobiliaria().obtenerInmuebles(token);
        obtenerInmueblePromesa.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if(response.isSuccessful()){
                    List<Inmueble> listaInmuebles = response.body();
                    mutableInmuebles.setValue(listaInmuebles);
                }
                else{
                    Toast.makeText(context, "Sin respuesta del servidor.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                Toast.makeText(context, "Ocurrio un error en el servidor.", Toast.LENGTH_SHORT).show();
            }
        });



    }
}