package com.example.zerocoders;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

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
        Bloodgroup=findViewById(R.id.editTextTextPersonName3);
        city=findViewById(R.id.editTextTextPersonName4);
        state=findViewById(R.id.editTextTextPersonName6);
        phone=findViewById(R.id.editTextPhone);
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        FirebaseAuth uid2=FirebaseAuth.getInstance();
        uid1=uid2.getCurrentUser().getUid().toString();
        db.getReference().child("All_users").child(uid1).child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name.setText(snapshot.getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        db.getReference().child("All_users").child(uid1).child("pincode").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pincode.setText(snapshot.getValue().toString());
                bloodgroup1=Bloodgroup.getText().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        db.getReference().child("All_users").child(uid1).child("state").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                state.setText(snapshot.getValue().toString());
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        db.getReference().child("All_users").child(uid1).child("city").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                city.setText(snapshot.getValue().toString());
//                city1=city.getText().toString();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        db.getReference().child("All_users").child(uid1).child("bloodGroup").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Bloodgroup.setText(snapshot.getValue().toString());
                bloodgroup1=Bloodgroup.getText().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        db.getReference().child("All_users").child(uid1).child("phoneNo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                phone.setText(snapshot.getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Users ur=new Users();
                String names="";
                HashMap<String,String>m=new HashMap<String, String>();
                m.put("name",name.getText().toString());
                db.getReference().child("All_users").child(uid1).child("name").setValue(name.getText().toString());
//                db.getReference().child("All_users").child(uid1).child("city").setValue(city.getText().toString());
                //db.getReference().child("All_users").child(uid1).child("state").setValue(state.getText().toString());
                db.getReference().child("All_users").child(uid1).child("pincode").setValue(phone.getText().toString());
                db.getReference().child("All_users").child(uid1).child("phoneNo").setValue(phone.getText().toString());
                db.getReference().child("All_users").child(uid1).child("bloodGroup").setValue(Bloodgroup.getText().toString());
                if(pincode!=)

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