package com.ulp.inmobiliriaefler.ui.perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ulp.inmobiliriaefler.R;
import com.ulp.inmobiliriaefler.databinding.FragmentPerfilBinding;
import com.ulp.inmobiliriaefler.modelo.Propietario;

public class PerfilFragment extends Fragment {

    private PerfilViewModel vmPerfil;
    private EditText etId,etNombre,etApellido,etDni,etEmail,etTelefono,etPasswordEditar;
    private TextView tvErrorPerfil;
    private ImageView ivAvatar;
    private Button btAccion,btCambiarPassword;
    private String avatar="";
    private String password="";



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        vmPerfil = new ViewModelProvider(this).get(PerfilViewModel.class);
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        vmPerfil.getMutablePropietario().observe(getViewLifecycleOwner(), new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                etId.setText(propietario.getId()+"");
                etNombre.setText(propietario.getNombre());
                etApellido.setText(propietario.getApellido());
                etDni.setText(propietario.getDni()+"");
                etTelefono.setText(propietario.getTelefono());
                etEmail.setText(propietario.getEmail());
                password=propietario.getClave();


                Glide.with(getContext())
                        .load(propietario.getAvatar())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(ivAvatar);

                avatar= propietario.getAvatar();
            }
        });
        vmPerfil.getMutablePropiedadEnable().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean valor) {

                etNombre.setEnabled(valor);
                etApellido.setEnabled(valor);
                etDni.setEnabled(valor);
                etTelefono.setEnabled(valor);

            }
        });
        vmPerfil.getMutableTextoBoton().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String textoBoton) {
                btAccion.setText(textoBoton);
            }
        });
        vmPerfil.getMutableMensaje().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvErrorPerfil.setText(s);
            }
        });
        inicializarVista(view);
        return view;
    }
    public void inicializarVista(View view){
        etId=view.findViewById(R.id.etId);
        etNombre = view.findViewById(R.id.etNombre);
        etApellido= view.findViewById(R.id.etApellido);
        etDni= view.findViewById(R.id.etDni);
        etTelefono= view.findViewById(R.id.etTelefono);
        etEmail=view.findViewById(R.id.etEmail);
        ivAvatar=view.findViewById(R.id.ivAvatar);
        btAccion= view.findViewById(R.id.btAccion);
        btCambiarPassword= view.findViewById(R.id.btCambiarPassword);
        tvErrorPerfil=view.findViewById(R.id.tvErrorPerfil);
        vmPerfil.ObtenerUsuario();
        btAccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textoBoton=  btAccion.getText().toString();

                int id = Integer.parseInt(etId.getText().toString());
                String nombre = etNombre.getText().toString();
                String apellido= etApellido.getText().toString();
                String dni=etDni.getText().toString();
                String telefono= etTelefono.getText().toString();
                String email= etEmail.getText().toString();

                Propietario p = new Propietario(id,nombre,apellido,dni,telefono,email,password,avatar);
                vmPerfil.actualizarPropietario(textoBoton,p);
            }
        });
        btCambiarPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_cambiarPassword);
            }
        });

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}