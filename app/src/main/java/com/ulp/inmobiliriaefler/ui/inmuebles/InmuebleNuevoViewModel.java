package com.ulp.inmobiliriaefler.ui.inmuebles;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.exifinterface.media.ExifInterface;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.ulp.inmobiliriaefler.R;
import com.ulp.inmobiliriaefler.modelo.Inmueble;
import com.ulp.inmobiliriaefler.modelo.Tipo_Inmueble;
import com.ulp.inmobiliriaefler.request.ApiRetrofit;

import java.io.ByteArrayOutputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.Header;

public class InmuebleNuevoViewModel extends AndroidViewModel {

    private Context context;

    private MutableLiveData<Integer> mutableUso;
    private MutableLiveData<Bundle> mutableTipo;
    private MutableLiveData<Bitmap> mutableFoto;


    public InmuebleNuevoViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }
  public LiveData<Integer> getMutableUso() {
        if(mutableUso == null) {
            mutableUso = new MutableLiveData<>();
        }
        return mutableUso;
    }
    public LiveData<Bundle> getMutableTipo() {
        if(mutableTipo == null) {
            mutableTipo = new MutableLiveData<>();
        }
        return mutableTipo;
    }
    public LiveData<Bitmap> getMutableFoto() {
        if(mutableFoto == null) {
            mutableFoto = new MutableLiveData<>();
        }
        return mutableFoto;
    }

    public void crearInmueble(int id,String domicilio,int uso,int tipo,String ambientes,String precio,String longitud,String latitud, int propietario,Boolean disponible,String encoded,Tipo_Inmueble tipoInmueble,View view){
        Integer amb=0;
        Double pre=0.1;

        if(domicilio.equals("")){
            Toast.makeText(context, "El campo domicilio no puede ser vacio.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(longitud.equals("")){
            Toast.makeText(context, "El campo longitud no puede ser vacio.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(latitud.equals("")){
            Toast.makeText(context, "El campo latitud no puede ser vacio.", Toast.LENGTH_SHORT).show();
            return;
        }
        try{
            amb=Integer.parseInt(ambientes);
        }
        catch(NumberFormatException i){
            Toast.makeText(context, "Ingrese el campo ambiente.", Toast.LENGTH_SHORT).show();
            return;
        }
        try{
           pre=Double.parseDouble(precio);
        }
        catch(NumberFormatException i){
            Toast.makeText(context, "Ingrese el campo precio.", Toast.LENGTH_SHORT).show();
            return;
        }
            String token = ApiRetrofit.obtenerToken(context);
            Call<Inmueble> crearInmueblePromesa = ApiRetrofit.getServiceInmobiliaria().agregarInmueble(token, domicilio, amb,pre, latitud, longitud, uso, disponible, tipo, propietario,encoded);
            crearInmueblePromesa.enqueue(new Callback<Inmueble>() {
                @Override
                public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "Se agrego el inmueble con exito.", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(view).navigate(R.id.nav_inmuebles);
                    } else {
                        Toast.makeText(context, "No hay respuesta.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Inmueble> call, Throwable t) {
                    Toast.makeText(context, "Ocurrio un error en el servidor.", Toast.LENGTH_SHORT).show();
                }
            });

        }

    public void cargarSpinerTipo(Spinner spinner, View view){
        String token = ApiRetrofit.obtenerToken(view.getContext());
        Call<Tipo_Inmueble[]> obtenerTiposPromesa = ApiRetrofit.getServiceInmobiliaria().obtenerTipoInmueble(token);
        obtenerTiposPromesa.enqueue(new Callback<Tipo_Inmueble[]>() {
            @Override
            public void onResponse(Call<Tipo_Inmueble[]> call, Response<Tipo_Inmueble[]> response) {
                if(response.isSuccessful()){
                    Tipo_Inmueble [] tipoInmuebleResponse= response.body();
                    String [] descripcionTipo=new String[tipoInmuebleResponse.length];

                    for(int i=0; i<tipoInmuebleResponse.length;i++){
                        descripcionTipo[i]=tipoInmuebleResponse[i].descripcion;
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, descripcionTipo);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

                    spinner.setAdapter(adapter);

                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            Tipo_Inmueble ti= new Tipo_Inmueble(tipoInmuebleResponse[position].getId(),tipoInmuebleResponse[position].getDescripcion());
                            Bundle tipoInmueble = new Bundle();
                            tipoInmueble.putSerializable("tipo",ti);
                            mutableTipo.postValue(tipoInmueble);
                             }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });}
            }

            @Override
            public void onFailure(Call<Tipo_Inmueble[]> call, Throwable t) {
                Toast.makeText(view.getContext(), "Error en el servidor", Toast.LENGTH_SHORT).show();

            }
        });

    }
    public void cargarSpinerUsos(Spinner spinner){

        String [] usos = new String [Inmueble.EnUsos.values().length];
        for(int i = 0; i< Inmueble.EnUsos.values().length; i++){
            usos[i]= Inmueble.EnUsos.values()[i].toString();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,usos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mutableUso.setValue(position+1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    public void sacarFoto(int requestCode, int resultCode, Intent data, int REQUEST_IMAGE_CAPTURE){
        if(requestCode== REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imagen= (Bitmap) extras.get("data");

            ByteArrayOutputStream baos= new ByteArrayOutputStream();

            imagen.compress(Bitmap.CompressFormat.JPEG,100,baos);
            byte[] b= baos.toByteArray();

            mutableFoto.postValue(rotateBitmap(imagen,90));
        }
    }
    private static Bitmap rotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
}