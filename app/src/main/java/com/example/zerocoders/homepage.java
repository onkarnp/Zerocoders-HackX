package com.example.zerocoders;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import io.alterac.blurkit.BlurLayout;

public class homepage extends AppCompatActivity {

    //    Initializing blurrkit
    BlurLayout blurLayout;
    CardView update_profile_card, request_blood_card, search_donor_card, log_out_card, admin_panel;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //removes title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();    //removes action bar
        blurLayout = findViewById(R.id.blurLayout);         //for blurring background
        setContentView(R.layout.activity_homepage);

        update_profile_card = (CardView) findViewById(R.id.update_profile);
        update_profile_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), update_profile.class);
                startActivity(intent);
            }
        });

        request_blood_card = (CardView) findViewById(R.id.request_blood);
        request_blood_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), bloodrequest.class);
                startActivity(intent);
            }
        });

        search_donor_card = (CardView) findViewById(R.id.search_donor);
        search_donor_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), find_donor_page.class);
                startActivity(intent);
            }
        });

        log_out_card = (CardView) findViewById(R.id.log_out);
        log_out_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(homepage.this);
                alertDialogBuilder.setTitle("Confirm Exit..!!");
                alertDialogBuilder.setIcon(R.drawable.ic_exit);
                alertDialogBuilder.setMessage("Are you sure ?");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(),log_in_page.class);
                        mAuth.signOut();
                        startActivity(intent);
                        Toast.makeText(homepage.this,"Logged out successfully",Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(homepage.this,"Cancelled..",Toast.LENGTH_LONG).show();
                    }
                });
                alertDialogBuilder.show();
            }
        });

         admin_panel= (CardView) findViewById(R.id.admin_panel);
        admin_panel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), enroll_user.class);
                startActivity(intent);
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