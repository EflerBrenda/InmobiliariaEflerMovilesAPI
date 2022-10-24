package com.ulp.inmobiliriaefler.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulp.inmobiliriaefler.MainActivity;
import com.ulp.inmobiliriaefler.modelo.Usuario;
import com.ulp.inmobiliriaefler.request.ApiRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {
    private MutableLiveData<Integer> mutableError;
    private Context context;
    //ApiClient api = ApiClient.getApi();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<Integer> getmutableError() {
        if (mutableError == null) { mutableError = new MutableLiveData<>(); }
        return mutableError;
    }

    public void login(String email, String pass) {
        Usuario usuario= new Usuario(email,pass);
        Call<String> tokenPromesa=ApiRetrofit.getServiceInmobiliaria().login(email,pass);
        tokenPromesa.enqueue(new Callback<String>()
        {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){

                    //Log.d("salida",token);
                    String token= "Bearer "+ response.body();
                    SharedPreferences sp= context.getSharedPreferences("token",0);
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putString("token",token);
                    editor.commit();
                    mutableError.setValue(View.INVISIBLE);
                    Intent i = new Intent(context, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
                else{
                    //Log.d("salida","Sin respuesta");
                    mutableError.setValue(View.VISIBLE);

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, "Ocurrio un error.", Toast.LENGTH_SHORT).show();
            }
        });

       /* Propietario p = api.login(email, pass);

        if (p != null) {
            mutableError.setValue(View.INVISIBLE);
            Intent i = new Intent(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        } else {
            mutableError.setValue(View.VISIBLE);
        }*/
    }


}
