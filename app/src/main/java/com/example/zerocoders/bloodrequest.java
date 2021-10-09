package com.example.zerocoders;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.zerocoders.models.Request;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import io.alterac.blurkit.BlurLayout;

public class bloodrequest extends AppCompatActivity {
    BlurLayout blurLayout;

    TextView name, phoneNo, bloodGroup, pincode, reason;
    Button submit;
    FirebaseAuth auth;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //removes title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();    //removes action bar
        blurLayout = findViewById(R.id.blurLayout);         //for blurring background
        setContentView(R.layout.activity_bloodrequest);

        name = findViewById(R.id.namereq);
        phoneNo = findViewById(R.id.reqphone);
        bloodGroup = findViewById(R.id.reqbloodgp);
        pincode = findViewById(R.id.reqlocation);
        reason =  findViewById(R.id.reqreason);
        submit = findViewById(R.id.button3);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = auth.getCurrentUser().getUid();
                Request request = new Request(name.getText().toString(), phoneNo.getText().toString(),
                        bloodGroup.getText().toString(), pincode.getText().toString(), reason.getText().toString());
                database.getReference().child("Request").child(bloodGroup.getText().toString()).
                        child(pincode.getText().toString()).child(uid).setValue(request);
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