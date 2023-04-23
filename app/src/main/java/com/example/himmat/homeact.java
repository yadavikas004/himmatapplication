package com.example.himmat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.InetAddresses;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;


public class homeact extends AppCompatActivity  {
    Button mlogout;
    Button mmaps2;
    Button mhelpline;
    Button mmessages;
    Button viewmap;
    Button selfdefence;
    Button speedometer;
    ImageView mSos;
    EditText mnumber;
    EditText mmessageforSOS;
    public FusedLocationProviderClient mFusedLocationClient;


    public LocationSettingsRequest.Builder builder;
    public String x= "", y ="";
    public static final int REQUEST_LOCATION =1;
    LocationManager locationManager;


    //calling
    private static final int Request_Call = 1;
    final int SEND_SMS_REQUEST_CODE =1;

    // for circle menu
    CircleMenu circleMenu;
    ConstraintLayout constraintLayout;

    GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeact);
        mSos = findViewById(R.id.sos_img);
        mmessages = findViewById(R.id.MSSGS);
        viewmap = findViewById(R.id.mapbtn);
        mmaps2 = findViewById(R.id.button2);
        mnumber = findViewById(R.id.editTextPhone);
        mmessageforSOS = findViewById(R.id.SOSmssg);
        mhelpline = findViewById(R.id.helpbtn);
        selfdefence=findViewById(R.id.selfdefence);
        speedometer=findViewById(R.id.speedometer);
        //circle menu
        circleMenu = findViewById(R.id.circle_menu);
        constraintLayout = findViewById(R.id.constraint_layout);
        //helpline
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mhelpline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(homeact.this,helpline.class);
                startActivity(i1);
            }
        });
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            onGPS();
        }else{
            startTrack();
        }

        mmaps2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(homeact.this, maps_page_2.class);
                startActivity(i1);
            }
        });
        // for circle menu
        circleMenu.setMainMenu(Color.parseColor("#FF8385"),R.mipmap.list,R.mipmap.multiply)
                .addSubMenu(Color.parseColor("#FF8385"),R.mipmap.home)
                .addSubMenu(Color.parseColor("#FF8385"),R.mipmap.add)
                .addSubMenu(Color.parseColor("#FF8385"),R.mipmap.account)
                .addSubMenu(Color.parseColor("#FF8385"),R.mipmap.gear)
                .addSubMenu(Color.parseColor("#FF8385"),R.mipmap.logout)
                .addSubMenu(Color.parseColor("#FF8385"),R.mipmap.help)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {
                    @Override
                    public void onMenuSelected(int index) {
                        switch (index){
                            case 0:
                                Toast.makeText(homeact.this, "Home", Toast.LENGTH_SHORT).show();
                                constraintLayout.setBackgroundColor(Color.parseColor("#2F2E41"));
                                Intent intent = new Intent(homeact.this, homeact.class);
                                startActivity(intent);
                                break;
                            case 1:
                                Toast.makeText(homeact.this, "Helpline", Toast.LENGTH_SHORT).show();
                                constraintLayout.setBackgroundColor(Color.parseColor("#2F2E41"));
                                Intent i1 = new Intent(homeact.this,helpline.class);
                                startActivity(i1);
                                break;
                            case 2:
                                Toast.makeText(homeact.this, "Crime Prediction", Toast.LENGTH_SHORT).show();
                                constraintLayout.setBackgroundColor(Color.parseColor("#2F2E41"));
                                Intent i2 = new Intent(homeact.this,crimerate.class);
                                startActivity(i2);
                                break;
                            case 3:
                                Toast.makeText(homeact.this, "Geolocation", Toast.LENGTH_SHORT).show();
                                constraintLayout.setBackgroundColor(Color.parseColor("#2F2E41"));
                                Intent i3 = new Intent(homeact.this,MapsPage.class);
                                startActivity(i3);
                                break;
                            case 4:
                                Toast.makeText(homeact.this, "You have been logged out bye bye", Toast.LENGTH_SHORT).show();
                                constraintLayout.setBackgroundColor(Color.parseColor("#2F2E41"));
                                FirebaseAuth.getInstance().signOut();
                                Intent intent2 = new Intent(homeact.this,login.class);
                                startActivity(intent2);
                                break;
                            case 5:
                                Toast.makeText(homeact.this, "Messages", Toast.LENGTH_SHORT).show();
                                constraintLayout.setBackgroundColor(Color.parseColor("#2F2E41"));
                                Intent i5 = new Intent(homeact.this,messages.class);
                                startActivity(i5);
                                break;
                        }
                    }
                });



        // for calls


        mmessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(homeact.this,messages.class);
                startActivity(intent2);
            }
        });

        viewmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(homeact.this,MapsPage.class);
                startActivity(intent2);
            }
        });

        selfdefence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(homeact.this,SelfDefenseActivity.class);
                startActivity(intent2);
            }
        });

        speedometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(homeact.this,Magnetometer.class);
                startActivity(intent2);
            }
        });

        // for messages in home page via edit text|| trying to connect the SOS button so that it acts as the send button for sms
        if(checkPermission(Manifest.permission.SEND_SMS)){
            mSos.setEnabled(true);
        }else{
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},SEND_SMS_REQUEST_CODE);
        }
        mSos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                String no=mnumber.getText().toString();
                String msg=mmessageforSOS.getText().toString();

                msg = "SOS WARNING: The user is in danger. Contact Authorities. Last LOCATION:" +"https://www.google.com/maps/search/?api=1&query="+x+"%2C"+y;

                //Getting intent and PendingIntent instance
                if(no.trim().length()>0) {
                    Intent intent = new Intent(getApplicationContext(), messages.class);
                    PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

                    //Get the SmsManager instance and call the sendTextMessage method to send message
                    SmsManager sms = SmsManager.getDefault();
                    sms.sendTextMessage(no, null, msg, pi, null);

                    Toast.makeText(getApplicationContext(), "Message Sent successfully!",
                            Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Enter Phone Number", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mSos.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                makePhoneCall();
                return true;
            }
        });
        mSos.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                makePhoneCall();
                return true;
            }
        });




    }
    // for messages
    public void startTrack() {
        if(ActivityCompat.checkSelfPermission(homeact.this,Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(homeact.this,Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);
        }else{
            Location locationGPS = locationManager.getLastKnownLocation((LocationManager.GPS_PROVIDER));
            if(locationGPS != null){
                double lat = locationGPS.getLatitude();
                double lng = locationGPS.getLongitude();
                x = String.valueOf(lat);
                y = String.valueOf(lng);
            }else{
                Toast.makeText(this, "Unable to find location", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void onGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent((Settings.ACTION_LOCATION_SOURCE_SETTINGS)));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        Intent intent2 = new Intent(homeact.this,login.class);
        startActivity(intent2);

    }
    public boolean checkPermission(String Permission){
        int check = ContextCompat.checkSelfPermission(this,Permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }


    public void gotocrime(View view) {
        Intent intentcrime=new Intent(homeact.this,crimerate.class);
        startActivity(intentcrime);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == Request_Call){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makePhoneCall();
            }else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


    //function for phone call
    private void makePhoneCall(){
        String no=mnumber.getText().toString();
        if(no.trim().length()>0){

            if(ContextCompat.checkSelfPermission(homeact.this,Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(homeact.this,
                        new String[]{Manifest.permission.CALL_PHONE},Request_Call);

            }else{
                String dial = "tel:" + no;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }else{
            Toast.makeText(getApplicationContext(), "Enter Phone Number", Toast.LENGTH_SHORT).show();
        }
    }
}