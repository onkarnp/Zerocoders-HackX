package com.example.zerocoders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

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
        db.getReference().child("RequestNotification").child(uid).orderByValue().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot)
            {
               for(DataSnapshot snap:snapshot.getChildren())
               {
                   Request request=snap.getValue(Request.class);
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