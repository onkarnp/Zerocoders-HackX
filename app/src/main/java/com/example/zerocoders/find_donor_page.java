package com.example.zerocoders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
    AutoCompleteTextView bloodGroup;
    TextView pincode, city, state;

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
        searchBtn = findViewById(R.id.DonorSearchbtn);
        findDonorRecyclerView = findViewById(R.id.FindDonorRecyclerView);
        bloodGroup = findViewById(R.id.autoCompleteTextView);
        pincode = findViewById(R.id.pincode);
        city = findViewById(R.id.textcity);
        state = findViewById(R.id.textstate);


        if(donorList == null)
            donorList = new ArrayList<Donor>();

        FindDonorAdapter adapter = new FindDonorAdapter(donorList ,this);
        findDonorRecyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        findDonorRecyclerView.setLayoutManager(layoutManager);

        String[] option = {"Select", "O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.options_item, option);
        bloodGroup.setText(arrayAdapter.getItem(0).toString(), false); //to make default value...
        bloodGroup.setAdapter(arrayAdapter);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bloodGroup.getText().toString().equals("Select") || pincode.getText().toString().isEmpty()) {
                    Toast.makeText(find_donor_page.this, "Select blood group and pincode", Toast.LENGTH_SHORT).show();
                    Log.d("Userrrrrrrrrrrrrr", "Select");
                    Log.d("Userrrrrrrrrrrrrr", pincode.getText().toString());
                    return;
                }
                String sBloodGroup = bloodGroup.getText().toString();
                Log.d("Userrrrrrrrrrrrrr", sBloodGroup);
                String sPincode = pincode.getText().toString();
                Log.d("Userrrrrrrrrrrrrr",sPincode);
                database.getReference().child("Users").child(sBloodGroup).child(sPincode).orderByValue().
                        addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        donorList.clear();
                        adapter.notifyDataSetChanged();

                        Users users = null;
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            users = snap.getValue(Users.class);
                            Log.d("Userrrrrrrr", users.toString());
                            Donor donor = new Donor(users.getName(), users.getEmail(), users.getPhoneNo(), users.getDob());
                            donorList.add(donor);

                        }
                        if(snapshot.hasChildren() && users != null) {
                            city.setText(users.getCity());
                            state.setText(users.getState());
                        }
                        else
                            Toast.makeText(find_donor_page.this, "No Result", Toast.LENGTH_SHORT).show();
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