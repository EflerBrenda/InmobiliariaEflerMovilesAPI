package com.ulp.inmobiliriaefler.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ulp.inmobiliriaefler.modelo.Contrato;
import com.ulp.inmobiliriaefler.modelo.Inmueble;
import com.ulp.inmobiliriaefler.modelo.Inquilino;
import com.ulp.inmobiliriaefler.modelo.Pago;
import com.ulp.inmobiliriaefler.modelo.Propietario;
import com.ulp.inmobiliriaefler.modelo.Tipo_Inmueble;
import com.ulp.inmobiliriaefler.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class ApiRetrofit {
    //private static final String PATH="http://practicastuds.ulp.edu.ar/api/";
    private static final String PATH="http://192.168.0.104:5000/api/";
    private static ServiceInmobiliaria servicioInmobiliaria;

    public static ServiceInmobiliaria getServiceInmobiliaria(){

        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(PATH)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        servicioInmobiliaria=retrofit.create(ServiceInmobiliaria.class);

        return servicioInmobiliaria;
    }
    public static String obtenerToken(Context context){
        SharedPreferences sp= context.getSharedPreferences("token",0);
        String token=sp.getString("token","No hay datos");
        return token;
    }
    public static void logOut(Context context){
        SharedPreferences sp= context.getSharedPreferences("token",0);
        sp.edit().clear();
    }
    public static int obtenerPropietarioActual(Context context){
        SharedPreferences sp= context.getSharedPreferences("propietarioActual",0);
        int id=sp.getInt("id",0);
        return id;
    }


    public interface ServiceInmobiliaria {

        @FormUrlEncoded
        @POST("Propietarios/login")
        Call<String>login (@Field("Email") String email,@Field("Password") String password);

        @GET("Propietarios")
        Call<Propietario> obtenerPerfil (@Header("Authorization") String token);

        @FormUrlEncoded
        @PUT("Propietarios")
        Call<Propietario> actualizarPerfil (@Header("Authorization") String token, @Field("Id") int id,@Field("Dni") String dni,@Field("Nombre") String nombre,@Field("Apellido") String apellido,@Field("Email") String email,@Field("Password") String password,@Field("Telefono") String telefono,@Field("Avatar") String Avatar);

        @GET("Inmuebles")
        Call<List<Inmueble>> obtenerInmuebles (@Header("Authorization") String token);

        @PUT("Inmuebles")
        Call<Inmueble> actualizarEstado (@Header("Authorization") String token, @Body Inmueble inmueble);

        @GET("Inmuebles/ObtenerTipoInmueble")
        Call<Tipo_Inmueble[]> obtenerTipoInmueble (@Header("Authorization") String token);

        @FormUrlEncoded
        @POST("Inmuebles")
        Call<Inmueble> agregarInmueble (@Header("Authorization") String token,@Field("Direccion") String direccion,@Field("Ambientes") int ambientes,@Field("Precio") Double precio,@Field("Latitud") Double latitud,@Field("Longitud") Double longitud,@Field("Uso") int uso,@Field("Oferta_activa") Boolean Oferta_activa,@Field("TipoInmuebleId") int TipoInmuebleId, @Field("PropietarioId") int propietarioId, @Field("Imagen") String imagen);

        @GET("Contratos/ContratosVigentes")
        Call<List<Contrato>> ContratosVigentes (@Header("Authorization") String token);

        @FormUrlEncoded
        @PUT("Propietarios/CambiarPassword")
        Call<Propietario> cambiarPassword (@Header("Authorization") String token,@Field("PasswordActual") String passwordActual,@Field("PasswordNueva") String passwordNueva);

        @GET("Inquilinos/{id}")
         Call<Contrato> obtenerContratosPorInmueble (@Header("Authorization") String token,@Path("id") int id);

        @GET("Pagos/{id}")
        Call<List<Pago>> obtenerPagos (@Header("Authorization") String token,@Path("id") int id);

        @FormUrlEncoded
        @POST("Propietarios/PedidoEmail")
        Call<Propietario> reestablecerPassword (@Field("email") String email);

    }
}
