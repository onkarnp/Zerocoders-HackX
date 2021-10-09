package com.example.zerocoders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.zerocoders.models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Objects;

import io.alterac.blurkit.BlurLayout;

public class sign_up_page extends AppCompatActivity {

    BlurLayout blurLayout;
    EditText enterFullName, mobileNumber, pincode, dateOfBirth, email, password;
    AutoCompleteTextView bloodgrp;
    CardView signincard, signupcard;
    private ProgressDialog loadingBar;
    String city,state;

    private FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;


    // creating a variable for request queue.
    private RequestQueue mRequestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //removes title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();    //removes action bar
        blurLayout = findViewById(R.id.blurLayout);         //for blurring background
        setContentView(R.layout.activity_sign_up_page);
        loadingBar = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        // initializing our request que variable with request
        // queue and passing our context to it.
        mRequestQueue = Volley.newRequestQueue(sign_up_page.this);

        enterFullName = findViewById(R.id.enterFullName);
        mobileNumber = findViewById(R.id.mobileNumber);
        pincode = findViewById(R.id.pincode);
        dateOfBirth = findViewById(R.id.dateOfBirth);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        bloodgrp = findViewById(R.id.autoCompleteTextView);

        //        for dropdown menu of blood group
        String[] option = {"Select", "O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.options_item, option);
        bloodgrp.setText(arrayAdapter.getItem(0).toString(), false); //to make default value...
        bloodgrp.setAdapter(arrayAdapter);


//        for date picker for date of birth
        Calendar cal = Calendar.getInstance();
        dateOfBirth = findViewById(R.id.dateOfBirth);
        dateOfBirth.setOnClickListener(new View.OnClickListener() {
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
                        dateOfBirth.setText(d);
                    }
                }, year, month, day);
                //Disables past date
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                //Show date picker dialog
                datePickerDialog.show();
            }
        });

        signupcard = (CardView) findViewById(R.id.signupcard);
        signupcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCredentials();
            }
        });

        signincard = (CardView) findViewById(R.id.signincard);
        signincard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), log_in_page.class);
                startActivity(intent);
            }
        });
    }


    //function for checking credentials whether inserted in correct format
    public void checkCredentials(){
        String name =  enterFullName.getText().toString();
        String number = mobileNumber.getText().toString();
        String pin = pincode.getText().toString();
        String dob = dateOfBirth.getText().toString();
        String Email = email.getText().toString();
        String Password = password.getText().toString();
        String bloodgroup = bloodgrp.getText().toString();


        if(name.isEmpty() || name.length()<5)
        {
            showError(enterFullName, "Invalid Name");
        }
        else if (number.length() != 10)
        {
            showError(mobileNumber, "Invalid Phone Number");
        }
        else if (pin.length() != 6)
        {
            showError(pincode, "Invalid Address Pin");
        }
        else if (dob.isEmpty())
        {
            showError(dateOfBirth, "Invalid DOB");
        }
        else if (bloodgroup.isEmpty())
        {
            showError(bloodgrp, "Invalid BloodGroup");
        }
        else if (Email.isEmpty())
        {
            showError(email, "Invalid Email Address");
        }
        else if (Password.isEmpty())
        {
            showError(password, "Invalid Password");
        }
        else{
            createUser();
        }
    }

    //function for registration
    public void createUser() {
        String name = enterFullName.getText().toString();
        String number = mobileNumber.getText().toString();
        String pin = pincode.getText().toString();
        String dob = dateOfBirth.getText().toString();
        String Email = email.getText().toString();
        String Password = password.getText().toString();
        String bloodgroup = bloodgrp.getText().toString();
        String state, city;
        mAuth = FirebaseAuth.getInstance();

////        **************************
//
//        // clearing our cache of request queue.
//        mRequestQueue.getCache().clear();
//
//        // below is the url from where we will be getting
//        // our response in the json format.
//        String url = "http://www.postalpincode.in/api/pincode/" + pin;
//
//        // below line is use to initialize our request queue.
//        RequestQueue queue = Volley.newRequestQueue(sign_up_page.this);
//
//        // in below line we are creating a
//        // object request using volley.
//        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                // inside this method we will get two methods
//                // such as on response method
//                // inside on response method we are extracting
//                // data from the json format.
//                try {
//                    // we are getting data of post office
//                    // in the form of JSON file.
//                    JSONArray postOfficeArray = response.getJSONArray("PostOffice");
//                    if (response.getString("Status").equals("Error")) {
//                        // validating if the response status is success or failure.
//                        // in this method the response status is having error and
//                        // we are setting text to TextView as invalid pincode.
//                        showError(pincode, "Invalid Address Pin");
//                    } else {
//                        // if the status is success we are calling this method
//                        // in which we are getting data from post office object
//                        // here we are calling first object of our json array.
//                        JSONObject obj = postOfficeArray.getJSONObject(0);
//
//                        // inside our json array we are getting district name,
//                        // state and country from our data.
//                        String city = obj.getString("Taluk");
//                        String state = obj.getString("State");
//                    }
//                } catch (JSONException e) {
//                    // if we gets any error then it
//                    // will be printed in log cat.
//                    e.printStackTrace();
//                    showError(pincode, "Invalid Address Pin");
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                // below method is called if we get
//                // any error while fetching data from API.
//                // below line is use to display an error message.
//                showError(pincode, "Invalid Address Pin");
//            }
//        });
//        // below line is use for adding object
//        // request to our request queue.
//        queue.add(objectRequest);
//
//
////        **************************


        if(!Email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(Email).matches())
        {
            if(!Password.isEmpty()){
                loadingBar.setTitle("Create Account");
                loadingBar.setMessage("Please wait while account is being created.");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
                mAuth.createUserWithEmailAndPassword(Email,Password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    rootNode = FirebaseDatabase.getInstance();
                                    String userID = mAuth.getCurrentUser().getUid();
                                    reference = rootNode.getReference("Users");
                                    //get all the values
//                                    public Users(String name, String phoneNo, String pincode, String state, String city, String dob, String email, String password, String bloodGroup)
                                    Users user = new Users(name, number, pin,"Maharashtra", "Nanded", dob, Email, Password, bloodgroup);
                                    reference.child(bloodgroup).child(pin).child(userID).setValue(user);
                                    reference = rootNode.getReference("All_users");
                                    reference.child(userID).setValue(user);
                                    Toast.makeText(sign_up_page.this, "Registered successfully !!", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();
                                    startActivity(new Intent(sign_up_page.this, log_in_page.class));
                                    finish();
                                }else
                                {
                                    loadingBar.dismiss();
                                    Toast.makeText(sign_up_page.this, "Registration error, try with another mail.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
            else {
                if(Password.isEmpty()) {
                    showError(password, "Empty fields are not allowed.");
                }
            }
        }
        else if(Email.isEmpty())
        {
            showError(email,"Empty fields are not allowed.");
        }
        else{
            showError(email,"Please Enter correct Email.");
        }
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

    //Function to show error message when input is not in correct format
    public void showError(EditText input, String s)
    {
        input.setError(s);
        input.requestFocus();
    }
}