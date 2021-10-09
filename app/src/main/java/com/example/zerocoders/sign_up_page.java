package com.example.zerocoders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Objects;

import io.alterac.blurkit.BlurLayout;

public class sign_up_page extends AppCompatActivity {

    //    Initializing blurrkit
    BlurLayout blurLayout;
    CardView signupcard, signincard;
    private EditText name, mobileno, city, dob, Email, password,state;
    AutoCompleteTextView bloodgrp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //removes title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();    //removes action bar
        blurLayout = findViewById(R.id.blurLayout);         //for blurring background
        setContentView(R.layout.activity_sign_up_page);

//        for dropdown menu of blood group
        bloodgrp = findViewById(R.id.autoCompleteTextView);
        String[] option = {"Select", "O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.options_item, option);
        bloodgrp.setText(arrayAdapter.getItem(0).toString(), false); //to make default value...
        bloodgrp.setAdapter(arrayAdapter);

        name = findViewById(R.id.enterFullName);
        mobileno = findViewById(R.id.mobileNumber);
        state = findViewById(R.id.state);
        city = findViewById(R.id.city);
        dob = findViewById(R.id.dateOfBirth);
        Email = findViewById(R.id.email);
        password = findViewById(R.id.password);

//        for date picker for date of birth
        Calendar cal = Calendar.getInstance();
        dob = findViewById(R.id.dateOfBirth);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(sign_up_page.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String d = dayOfMonth + "-" + month + "-" + year;
                        dob.setText(d);
                    }
                }, year, month, day);
                //Disables past date
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                //Show date picker dialog
                datePickerDialog.show();
            }
        });
    }



    //Functions for making background blur (needed to be included to run blurrkit)
    @Override
    protected void onStart() {
        super.onStart();
        blurLayout = findViewById(R.id.blurLayout);
        blurLayout.startBlur();
    }

    //Functions for making background blur  (needed to be included to run blurrkit)
    @Override
    protected void onStop() {
        blurLayout.pauseBlur();
        super.onStop();
    }
}