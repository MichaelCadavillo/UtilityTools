package com.example.michael.utilitytools;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class CompassActivity extends AppCompatActivity implements SensorEventListener{

    private ImageView compass;
    private TextView degrees;

    private static SensorManager sensorManager;
    private Sensor sensor;

    private float currentDegrees = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        MobileAds.initialize(this, Integer.toString(R.string.appID));

        AdView adView = (AdView) findViewById(R.id.adView);
//      AdRequest adRequest = new AdRequest.Builder().addTestDevice("A0D2DD44E80EC06C276F4C86AAE5DD68").build();
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        compass = (ImageView) findViewById(R.id.compass);
        degrees = (TextView) findViewById(R.id.degrees);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
    }

    @Override
    protected void onResume(){
        super.onResume();

        if(sensor != null){
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
        }else{
            Toast.makeText(CompassActivity.this, "Not Supported! Your phone doesn't seem to have a compass.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause(){
        super.onPause();

        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int degrees = Math.round(event.values[0]);

        this.degrees.setText(Integer.toString(degrees) + '°');

        RotateAnimation ra = new RotateAnimation(currentDegrees, -degrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        ra.setDuration(300);
        ra.setFillAfter(true);

        compass.startAnimation(ra);
        currentDegrees = -degrees;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
