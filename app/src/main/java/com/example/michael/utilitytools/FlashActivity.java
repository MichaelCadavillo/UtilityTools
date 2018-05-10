package com.example.michael.utilitytools;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class FlashActivity extends AppCompatActivity {

    private ImageButton imageButton;
    public static boolean toggle=false;
    public boolean isOn = false;

                @Override
                protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    setContentView(R.layout.activity_flash);

                    MobileAds.initialize(this, Integer.toString(R.string.appID));

                    AdView adView = (AdView) findViewById(R.id.adView);
//      AdRequest adRequest = new AdRequest.Builder().addTestDevice("A0D2DD44E80EC06C276F4C86AAE5DD68").build();
                    AdRequest adRequest = new AdRequest.Builder().build();
                    adView.loadAd(adRequest);
                }

            public void handleFlash(View v){
                imageButton = (ImageButton) findViewById(R.id.switchBtn);

                toggle = !toggle;

                try {
                    CameraManager cameraManager = (CameraManager) getApplicationContext().getSystemService(Context.CAMERA_SERVICE);

                    for (String id : cameraManager.getCameraIdList()) {

                // Turn on the flash if camera has one
                if (cameraManager.getCameraCharacteristics(id).get(CameraCharacteristics.FLASH_INFO_AVAILABLE)) {
                    cameraManager.setTorchMode(id, toggle);
                    if(toggle){
                        imageButton.setImageResource(R.drawable.on);
                    }else{
                        imageButton.setImageResource(R.drawable.off);
                    }
                }
            }

        } catch (CameraAccessException e2) {
            Toast.makeText(getApplicationContext(), "Flashlight Failed: " + e2.getMessage(), Toast.LENGTH_SHORT).show();
        }

        /*if (isFlash) {
            if (!isOn) {
                imageButton.setBackgroundResource(R.drawable.on);
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(parameters);
                camera.startPreview();
                isOn = true;
            } else {
                imageButton.setBackgroundResource(R.drawable.off);
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(parameters);
                camera.stopPreview();
                isOn = false;
            }

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(FlashActivity.this);
            builder.setTitle("Oops!!");
            builder.setMessage("Sorry. It seems that flashlight isn't available on your device.");
            builder.setPositiveButton("Ok :(", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }*/
    }
}
