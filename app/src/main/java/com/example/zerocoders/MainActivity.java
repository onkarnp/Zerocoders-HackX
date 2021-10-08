package com.example.zerocoders;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.util.Objects;

import io.alterac.blurkit.BlurLayout;

public class MainActivity extends AppCompatActivity {

    BlurLayout blurLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //removes title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();    //removes action bar
        blurLayout = findViewById(R.id.blurLayout);         //for blurring background
        setContentView(R.layout.activity_main);
    }

    //Functions for making background blur
    @Override
    protected void onStart() {
        super.onStart();
        blurLayout = findViewById(R.id.blurLayout);
        blurLayout.startBlur();
    }

    //Functions for making background blur
    @Override
    protected void onStop() {
        blurLayout.pauseBlur();
        super.onStop();
    }
}