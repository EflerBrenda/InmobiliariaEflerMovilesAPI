package com.ulp.inmobiliriaefler.login;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.net.Uri;

public class LeeSensor implements SensorEventListener {

    private Context context;

    public LeeSensor(Context context) {

        this.context = context;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.values[0] >= 7.511627 ) {
            Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:2664240305"));
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }

    }

    @Override
    public void onAccuracyChanged (Sensor sensor, int i){

    }
}
