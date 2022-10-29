package com.ulp.inmobiliriaefler.ui.inmuebles;

import static androidx.core.content.PermissionChecker.checkSelfPermission;

import androidx.core.content.PermissionChecker;
import androidx.exifinterface.media.ExifInterface;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ulp.inmobiliriaefler.R;
import com.ulp.inmobiliriaefler.modelo.Inmueble;
import com.ulp.inmobiliriaefler.modelo.Propietario;
import com.ulp.inmobiliriaefler.modelo.Tipo_Inmueble;
import com.ulp.inmobiliriaefler.request.ApiRetrofit;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static android.Manifest.permission.CAMERA;

public class InmuebleNuevoFragment extends Fragment {

    private Button btAgregarInmueble,btBuscarCoordenadas;
    private Spinner spUso,spTipo;
    private EditText etDireccionNuevo,etLatitudNuevo,etLongitudNuevo,etPrecioNuevo,etAmbientes;
    private Switch swDisponibleNuevo;
    private ImageView ivInmuebleNuevo;
    private Integer iduso=0;
    private Tipo_Inmueble tipoInmueble;
    private String encoded;
    private InmuebleNuevoViewModel mViewModel;
    private static int REQUEST_IMAGE_CAPTURE=1;
    String latitud="";
    String longitud="";

    public static InmuebleNuevoFragment newInstance() {
        return new InmuebleNuevoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(InmuebleNuevoViewModel.class);
        View view = inflater.inflate(R.layout.fragment_inmueble_nuevo, container, false);
       mViewModel.getMutableTipo().observe(getViewLifecycleOwner(), new Observer<Bundle>() {
            @Override
            public void onChanged(Bundle tipo) {
                tipoInmueble=(Tipo_Inmueble) tipo.getSerializable("tipo");
            }
        });
        mViewModel.getMutableUso().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                iduso= integer;
            }
        });
        mViewModel.getMutableFoto().observe(getViewLifecycleOwner(), new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap bitmap) {

                ivInmuebleNuevo.setImageBitmap(bitmap);

                ByteArrayOutputStream baos=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
                byte [] b=baos.toByteArray();

                encoded = Base64.getEncoder().encodeToString(b);

            }
        });


        inicializarVista(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        buscarCoordenadas();
    }

    public void inicializarVista(View view){
        etDireccionNuevo= view.findViewById(R.id.etDireccionNuevo);
        etLatitudNuevo= view.findViewById(R.id.etLatitudNuevo);
        etLongitudNuevo= view.findViewById(R.id.etLongitudNuevo);
        etPrecioNuevo= view.findViewById(R.id.etPrecioNuevo);
        etAmbientes= view.findViewById(R.id.etAmbientes);
        swDisponibleNuevo= view.findViewById(R.id.swDisponibleNuevo);
        spUso= view.findViewById(R.id.spUso);
        spTipo= view.findViewById(R.id.spTipo);
        btAgregarInmueble= view.findViewById(R.id.btAgregarInmueble);
        btBuscarCoordenadas= view.findViewById(R.id.btBuscarCoordenadas);
        ivInmuebleNuevo= view.findViewById(R.id.ivInmuebleNuevo);
        mViewModel.cargarSpinerTipo(spTipo,view);
        mViewModel.cargarSpinerUsos(spUso);

        solicitarPermisos();
        buscarCoordenadas();

        ivInmuebleNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inicializarCamara(view);
            }
        });
        btAgregarInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String domicilio= etDireccionNuevo.getText().toString();
                String precio=etPrecioNuevo.getText().toString();
                String ambientes=etAmbientes.getText().toString();
                Boolean disponible=  swDisponibleNuevo.isChecked();
                int uso=iduso;
                int propietario= ApiRetrofit.obtenerPropietarioActual(getContext());

                mViewModel.crearInmueble(0,domicilio,uso,tipoInmueble.getId(),ambientes,precio,longitud,latitud,propietario,disponible,encoded,tipoInmueble);
            }

        });
        btBuscarCoordenadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_mapaInmueble);
            }
        });

    }

    public void inicializarCamara(View v){
        Intent tomarFoto= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (tomarFoto.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(tomarFoto, 1);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        InmuebleNuevoFragment.super.onActivityResult(requestCode, resultCode, data);
        mViewModel.sacarFoto(requestCode, resultCode, data,REQUEST_IMAGE_CAPTURE );
    }

    public void buscarCoordenadas(){
        SharedPreferences sp= getContext().getSharedPreferences("ubicacion",0);
        latitud=sp.getString("latitud","");
        longitud=sp.getString("longitud","");
        etLatitudNuevo.setText(latitud);
        etLongitudNuevo.setText(longitud);
    }
    public void solicitarPermisos(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(getContext(),CAMERA)
                != PermissionChecker.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.CAMERA}, 1000);
        }
    }



}


