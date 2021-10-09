package com.example.zerocoders;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zerocoders.models.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import io.alterac.blurkit.BlurLayout;

public class update_profile extends AppCompatActivity {


    //    Initializing blurrkit
    BlurLayout blurLayout;
    Button b1;
    EditText name;
    EditText Bloodgroup;
    EditText state;
    EditText city;
    EditText phone;
    String uid1;
    String city1;
    String bloodgroup1;
    EditText pincode;
    String pincode1;
    String state1;
    String dob1;
    String password1;
    String email1;

    Users user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //removes title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();    //removes action bar
        blurLayout = findViewById(R.id.blurLayout);         //for blurring background
        setContentView(R.layout.activity_update_profile);
        b1=findViewById(R.id.button2);
        name=findViewById(R.id.editTextTextPersonName2);
        pincode=findViewById(R.id.editTextTextPersonName6);
        phone=findViewById(R.id.editTextPhone);
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        FirebaseAuth uid2=FirebaseAuth.getInstance();
        uid1=uid2.getCurrentUser().getUid().toString();


        db.getReference().child("All_users").child(uid1).orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(Users.class);
                pincode1 = user.getPincode();
                bloodgroup1 = user.getBloodGroup();
                name.setText(user.getName());
                pincode.setText(user.getPincode());
                phone.setText(user.getPhoneNo());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(update_profile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!name.getText().toString().isEmpty())
                    user.setName(name.getText().toString());
                if(!pincode.getText().toString().isEmpty())
                    user.setPincode(pincode.getText().toString());
                if(!phone.getText().toString().isEmpty())
                    user.setPhoneNo(phone.getText().toString());

                if (!(pincode1.equals(pincode.getText().toString())))
                {
                    db.getReference().child("Users").child(bloodgroup1).child(pincode1).child(uid1).removeValue();
                    db.getReference().child("Users").child(bloodgroup1).child(pincode.getText().toString()).child(uid1).setValue(user);
                }
                else{
                    db.getReference().child("Users").child(bloodgroup1).child(pincode1).child(uid1).setValue(user);
                }
                db.getReference().child("All_users").child(uid1).setValue(user);
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