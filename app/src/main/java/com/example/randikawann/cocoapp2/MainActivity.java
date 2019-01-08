package com.example.randikawann.cocoapp2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.randikawann.cocoapp2.adapters.TabPageAdapter;
import com.example.randikawann.cocoapp2.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView textView;
    private android.support.v7.widget.Toolbar mainToolBar;

    private ViewPager myViewPager;
    private TabLayout myTabLayout;
    private TabPageAdapter myTabPageAdapter;

    private String current_User_Id;
    private String current_user_name;
    private String dateString;
    double lat;
    double lon;
    //shaking sensor
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    private DatabaseReference gpsReference;
    private DatabaseReference userReference;

    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text);

        mainToolBar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mainToolBar);
        getSupportActionBar().setTitle("COMO Android");
        getSupportActionBar().setSubtitle("Real world application");
        mainToolBar.setElevation(10f);

        //tab for MainActivity
        myViewPager = (ViewPager) findViewById(R.id.main_tabs_pager);
        myTabPageAdapter = new TabPageAdapter(getSupportFragmentManager());
        myViewPager.setAdapter(myTabPageAdapter);
        myTabLayout = (TabLayout) findViewById(R.id.main_tabs);
        myTabLayout.setupWithViewPager(myViewPager);


        mAuth = FirebaseAuth.getInstance();

//        curent user id
        try {
            current_User_Id = mAuth.getCurrentUser().getUid();
            gpsLocation();
            Log.i("intent","on create gps update");
        }catch (Exception e){}


//        sharedprefer();
        userReference = FirebaseDatabase.getInstance().getReference("users");
        setDataPrefe();
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.getValue()!=null){
                    try{
                        current_user_name = dataSnapshot.child(current_User_Id).child("user_name").getValue().toString();

                    }catch (Exception e){
//                        Toast.makeText(EditProfileActivity.this,"exception",Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {

                handleShakeEvent(count);

            }
        });

    }
    private void setDataPrefe() {
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot :dataSnapshot.getChildren()){

                    User userRetrieve = postSnapshot.getValue(User.class);

                    String userID = userRetrieve.getUser_id();
                    SharedPreferences sharedPreferences = getSharedPreferences(userID,Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor= sharedPreferences.edit();
                    editor.putString("user_name", userRetrieve.getUser_name());
                    editor.putString("user_img", userRetrieve.getUser_image());
                    editor.putString("user_age", userRetrieve.getUser_image());
                    editor.putString("user_gender", userRetrieve.getUser_gender());
                    editor.putString("user_status", userRetrieve.getUser_status());
                    editor.putString("user_thumbImg", userRetrieve.getUser_thumbImg());
                    editor.putString("device_token", userRetrieve.getDevice_token());

//                    editor.commit();
                    editor.apply();

                    Log.i("maintbfgdf","data aded to value "+userID);
//                    Log.i("main","userNmae"+userRetrieve.getUser_name());




                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void gpsLocation() {


        //        added current date
        long date = System.currentTimeMillis();
//        SimpleDateFormat sdf= new SimpleDateFormat("MMM dd yyyy");
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy.MM.dd - HH:mm:ss");
        Log.i("intent",sdf.format(date));
        dateString = sdf.format(date);


        //        update gps location
        gpsReference = FirebaseDatabase.getInstance().getReference().child("gpslocation");
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},123);
        GpsTracker gpsTracker = new GpsTracker(getApplicationContext());
        Location location = gpsTracker.getLocation();
        if(location !=null){
            lat = location.getLatitude();
            lon = location.getLongitude();
//            Toast.makeText(MainActivity.this,"Lat is " + lat,Toast.LENGTH_SHORT).show();
            gpsReference.child(current_User_Id).child("latitute").setValue(lat);
            gpsReference.child(current_User_Id).child("longitude").setValue(lon);
            gpsReference.child(current_User_Id).child("lastupdated").setValue(dateString);
            gpsReference.child(current_User_Id).child("user_id").setValue(current_User_Id);
//            Toast.makeText(MainActivity.this,"Location Updated",Toast.LENGTH_SHORT).show();

        }else{
//            Toast.makeText(MainActivity.this,"Location not Updated....",Toast.LENGTH_SHORT).show();
        }

    }

    public void sendToLogin(){
        Intent authIntent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(authIntent);
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);


        if(currentUser == null){
            Intent authIntent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(authIntent);
        }
        try{
            gpsLocation();
            Log.i("intent","on start gps update");
//            Toast.makeText(MainActivity.this,"current vlaue "+lat+" "+lon,Toast.LENGTH_SHORT).show();
        }catch(Exception e){
//            Toast.makeText(MainActivity.this,"Exception for set gps location",Toast.LENGTH_SHORT).show();
        }


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile: {
                Intent profileIntent = new Intent(MainActivity.this, ProfileActivity.class);
                profileIntent.putExtra("user_id",current_User_Id);
                startActivity(profileIntent);
                // do your sign-out stuff
                break;
            }
            case R.id.allUsers:{
                Intent allUsersIntent = new Intent(MainActivity.this, AllUsersActivity.class);
                startActivity(allUsersIntent);
                break;
            }
            case R.id.logout:{
                mAuth.signOut();
                sendToLogin();
                break;
            }
            // case blocks for other MenuItems (if any)
        }
        return false;
    }
    //shaking
    private void handleShakeEvent(int count) {
        Log.i("intent","shake gps update");
        gpsLocation();
        Intent nearIntent = new Intent(MainActivity.this , NearbyActivity.class);
        startActivity(nearIntent);

    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }

}
