package com.ulp.inmobiliriaefler;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.ulp.inmobiliriaefler.databinding.ActivityMainBinding;
import com.ulp.inmobiliriaefler.modelo.Propietario;
import com.ulp.inmobiliriaefler.request.ApiRetrofit;
import com.ulp.inmobiliriaefler.ui.perfil.PerfilViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private TextView tvNombreApellido,tvEmailMenu;
    private ImageView ivAvatarMenu;
    private PerfilViewModel pvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        inicializarVista(navigationView);


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_perfil,R.id.nav_inmuebles,R.id.nav_inquilinos,R.id.nav_contratos,R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        solicitarPermisos();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public void inicializarVista(NavigationView navigationView){
        View view= navigationView.getHeaderView(0);
        tvNombreApellido=view.findViewById(R.id.tvNombreApellido);
        tvEmailMenu= view.findViewById(R.id.tvEmailMenu);
        ivAvatarMenu= view.findViewById(R.id.ivAvatarMenu);

        String token=ApiRetrofit.obtenerToken(navigationView.getContext());
        Call<Propietario> obtenerPerfilPromesa = ApiRetrofit.getServiceInmobiliaria().obtenerPerfil(token);
        obtenerPerfilPromesa.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if(response.isSuccessful()){
                    Propietario p = response.body();

                    tvNombreApellido.setText(p.getNombre() +" "+ p.getApellido());
                    tvEmailMenu.setText(p.getEmail());
                    Glide.with(navigationView.getContext())
                            .load(p.getAvatar())
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(ivAvatarMenu);
                    SharedPreferences sp= getApplication().getSharedPreferences("propietarioActual",0);
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putInt("id",response.body().getId());
                    editor.commit();
                }
                else{
                    //Log.d("salida","Sin respuesta");
                    Toast.makeText(MainActivity.this, "Ocurrio un error.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Ocurrio un error en el servidor.", Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void solicitarPermisos() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
        }


    }


}