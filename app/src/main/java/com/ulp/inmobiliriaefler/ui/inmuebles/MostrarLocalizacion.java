package com.ulp.inmobiliriaefler.ui.inmuebles;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.ulp.inmobiliriaefler.modelo.Inmueble;

public class MostrarLocalizacion implements OnMapReadyCallback {
    private LatLng latLnginmueble;
    private Context context;
    private GoogleMap map;
    private Inmueble inmueble;


    public MostrarLocalizacion(Context context, Inmueble inmueble) {
        this. inmueble= inmueble;
        this.latLnginmueble= new LatLng(Double.parseDouble(inmueble.getLatitud()),Double.parseDouble(inmueble.getLongitud()));
        this.context= context;

    }

    @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            map= googleMap;
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            Marker marcadorSANLUIS=googleMap.addMarker(new MarkerOptions().position(latLnginmueble));
            marcadorSANLUIS.setTitle(inmueble.getDireccion());
            marcadorSANLUIS.setPosition(latLnginmueble);

            obtenerUltimaUbicacion();

        }
    private void obtenerUltimaUbicacion() {
        FusedLocationProviderClient fl= LocationServices.getFusedLocationProviderClient(context);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        fl.getLastLocation().addOnSuccessListener(context.getMainExecutor(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    LatLng ua= new LatLng(location.getLatitude(),location.getLongitude());
                    map.addMarker(new MarkerOptions().position(ua))

                            .setTitle("Mi ubicacion");
                    CameraPosition camUlp= new CameraPosition.Builder()
                            .target(ua)
                            .zoom(15)
                            .bearing(45)
                            .tilt(70)
                            .build();
                    CameraUpdate caULP= CameraUpdateFactory.newCameraPosition(camUlp);
                    map.animateCamera(caULP);

                }
            }
        });
    }

    }

