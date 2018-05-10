package com.example.michael.utilitytools;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Intro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Thread thread = new Thread(){
            @Override
            public void run(){
                try {
                    sleep(1000);
                    Intent intent = new Intent("com.example.michael.utilitytools.MainActivity");
                    startActivity(intent);
                    finish();
                }catch (InterruptedException ie){
                    ie.printStackTrace();
                }
            }
        };
        thread.start();
    }
}
