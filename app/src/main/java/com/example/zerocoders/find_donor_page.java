package com.example.zerocoders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zerocoders.adapter.FindDonorAdapter;
import com.example.zerocoders.models.Donor;
import com.example.zerocoders.models.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.alterac.blurkit.BlurLayout;

public class find_donor_page extends AppCompatActivity {
    FirebaseDatabase database;
    ArrayList<Donor> donorList;

    Button searchBtn;
    RecyclerView findDonorRecyclerView;
    Spinner bloodGroup;
    TextView pincode;

    //    Initializing blurrkit
    BlurLayout blurLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //removes title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();    //removes action bar
        blurLayout = findViewById(R.id.blurLayout);         //for blurring background
        setContentView(R.layout.activity_find_donor_page);

        database = FirebaseDatabase.getInstance();
        searchBtn = findViewById(R.id.DonerSearchbtn);
        findDonorRecyclerView = findViewById(R.id.FindDonorRecyclerView);
        bloodGroup = findViewById(R.id.SpinnerBloodGroup);
        pincode = findViewById(R.id.pincode);


        if(donorList == null)
            donorList = new ArrayList<Donor>();

        FindDonorAdapter adapter = new FindDonorAdapter(donorList ,this);
        findDonorRecyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        findDonorRecyclerView.setLayoutManager(layoutManager);

        String[] option = {"Select", "O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.options_item, option);
        bloodGroup.setAdapter(arrayAdapter);
        bloodGroup.setSelection(0);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sBloodGroup = bloodGroup.getSelectedItem().toString();
                String sPincode = pincode.getText().toString();
                database.getReference().child(sBloodGroup).child(sPincode).orderByValue().
                        addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        donorList.clear();
                        adapter.notifyDataSetChanged();
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            Users users = snap.getValue(Users.class);
                            Donor donor = new Donor(users.getName(), users.getEmail(), users.getPhoneNo(), users.getDob());
                            donorList.add(donor);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(find_donor_page.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
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