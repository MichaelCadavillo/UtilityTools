package com.example.michael.utilitytools;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {
    private ImageButton flashBtn;
    private ImageButton compassBtn;
    private ImageButton mapsBtn;
    private ImageButton stopwatchBtn;

    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int LOCATION_REQUEST_CODE = 2;

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, Integer.toString(R.string.appID));

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(Integer.toString(R.string.iAdUnitID));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
//        mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice("A0D2DD44E80EC06C276F4C86AAE5DD68").build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mInterstitialAd.show();
                super.onAdLoaded();
            }
        });

//        new loadInterstitial();

        AdView adView = (AdView) findViewById(R.id.adView);
//      AdRequest adRequest = new AdRequest.Builder().addTestDevice("A0D2DD44E80EC06C276F4C86AAE5DD68").build();
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        buttonOnClick();
    }

    public void buttonOnClick(){
        flashBtn = (ImageButton) findViewById(R.id.flashlightBtn);

        flashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasCameraPermission()){
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    } else {
                        Log.d("TAG", "The interstitial wasn't loaded yet.");
                    }

                    Intent flashIntent = new Intent("com.example.michael.utilitytools.FlashActivity");
                    startActivity(flashIntent);
                }else{
                    requestCameraPermission();
                }
            }
        });

        compassBtn = (ImageButton) findViewById(R.id.compassBtn);

        compassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }

                Intent compassIntent = new Intent("com.example.michael.utilitytools.CompassActivity");
                startActivity(compassIntent);
            }
        });

        mapsBtn = (ImageButton) findViewById(R.id.mapsBtn);

        mapsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasLocationPermission()){
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    } else {
                        Log.d("TAG", "The interstitial wasn't loaded yet.");
                    }

                    Intent mapsIntent = new Intent("com.example.michael.utilitytools.MapsActivity");
                    startActivity(mapsIntent);
                }else{
                    requestLocationPermission();
                }
            }
        });

        stopwatchBtn = (ImageButton) findViewById(R.id.stopwatchBtn);

        stopwatchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }

                Intent stopwatchIntent = new Intent("com.example.michael.utilitytools.StopwatchActivity");
                startActivity(stopwatchIntent);
            }
        });
    }

    private boolean hasCameraPermission(){
        int result = 0;

        String[] permissions = new String[]{Manifest.permission.CAMERA};

        for(String permission: permissions){
            result = checkCallingOrSelfPermission(permission);

            if(result != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    private void    requestCameraPermission(){
        String[] permissions = new String[]{Manifest.permission.CAMERA};

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(permissions, CAMERA_REQUEST_CODE);
        }
    }

    private boolean hasLocationPermission(){
        int result = 0;

        String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};

        for(String permission: permissions){
            result = checkCallingOrSelfPermission(permission);

            if(result != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    private void requestLocationPermission(){
        String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(permissions, LOCATION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case CAMERA_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Intent flashIntent = new Intent("com.example.michael.utilitytools.FlashActivity");
                    startActivity(flashIntent);
                }else{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
                            Toast.makeText(this, "Camera Access Denied. Flashlight feature will not work!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;

            case LOCATION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Intent mapsIntent = new Intent("com.example.michael.utilitytools.MapsActivity");
                    startActivity(mapsIntent);
                }else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                            Toast.makeText(this, "Location Access Denied! Current location couldn't be found.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
    }
}
