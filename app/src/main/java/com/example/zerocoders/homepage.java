package com.example.zerocoders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.cardview.widget.CardView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import com.example.zerocoders.models.Request;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import io.alterac.blurkit.BlurLayout;

public class homepage extends AppCompatActivity {

    //    Initializing blurrkit
    public static final String NOTIFICATION_CHANNEL_ID="10001";
    private final static String default_notification_channel_id="default";
    BlurLayout blurLayout;

    String Token ="admin123";

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
        FirebaseAuth auth=FirebaseAuth.getInstance();
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        String uid=auth.getCurrentUser().getUid().toString();
        db.getReference().child("RequestNotification").child(uid).orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot)
            {
                if(snapshot.hasChildren()){

                   Request request=snapshot.getValue(Request.class);
                   String message=request.toString();
                  NotificationManager manager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                  NotificationCompat.Builder builder=new NotificationCompat.Builder(homepage.this,default_notification_channel_id)
                          .setContentTitle("request")
                          .setContentText(message)
                          .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                          .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                          .setAutoCancel(true);
                  if(android.os.Build.VERSION.SDK_INT>= Build.VERSION_CODES.O)
                   {
                       int importance=NotificationManager.IMPORTANCE_HIGH;
                       NotificationChannel channel=new NotificationChannel(NOTIFICATION_CHANNEL_ID,"NOTIFICATION_CHANNEL_NAME",importance);
                       builder.setChannelId(NOTIFICATION_CHANNEL_ID);
                       assert manager != null;
                       manager.createNotificationChannel(channel);
                   }
                  assert manager!=null;
                  manager.notify((int )System.currentTimeMillis(),builder.build());
                }
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });


        db.getReference().child("RequestNotification").child(uid).setValue(null);

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

    public void dialog_Open(View view) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(homepage.this);
        View mview = getLayoutInflater().inflate(R.layout.dialog,null);
         final
         EditText txt_inputText=(EditText)mview.findViewById(R.id.txt_input);
        Button btn_cancel=(Button)mview.findViewById(R.id.cancel);
        Button btn_ok=(Button)mview.findViewById(R.id.ok);

        alert.setView(mview);

        final AlertDialog alertDialog=alert.create();
        alertDialog.setCanceledOnTouchOutside(false);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();

            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( Token.equals(txt_inputText.getText().toString()))
                {
                    Intent intent=new Intent(getApplicationContext(),enroll_user.class);
                    startActivity(intent);

                }

            }
        });
        alertDialog.show();

    }
}