package com.ulp.inmobiliriaefler.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ulp.inmobiliriaefler.R;

public class Login extends AppCompatActivity {
    private LoginViewModel vmLogin;
    private EditText etUsuario, etPassword;
    private Button btLogin;
    private TextView tvError;
    private SensorManager sm;
    private LeeSensor leeSensor;
    private Sensor acelerometro;
    private Context context;
    //private ApiClient api = ApiClient.getApi();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getApplicationContext();
        setContentView(R.layout.activity_login);
        inicializarVista();
        vmLogin = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginViewModel.class);
        vmLogin.getmutableError().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer visibility) {
                tvError.setVisibility(visibility);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.CALL_PHONE}, 1000);
        }

        leeSensor = new LeeSensor(context);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        acelerometro = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(leeSensor, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(leeSensor, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop() {
        super.onStop();
        sm.unregisterListener(leeSensor);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sm.unregisterListener(leeSensor);
    }

    public void inicializarVista() {
        btLogin = findViewById(R.id.btLogin);
        etUsuario = findViewById(R.id.etUsuario);
        etPassword = findViewById(R.id.etPassword);
        tvError = findViewById(R.id.tvError);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vmLogin.login(
                        etUsuario.getText().toString(),
                        etPassword.getText().toString()
                );
            }
        });
        etUsuario.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                tvError.setVisibility(View.INVISIBLE);
            }
        });
        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                tvError.setVisibility(View.INVISIBLE);
            }
        });

    }

  }

