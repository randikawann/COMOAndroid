package com.example.randikawann.cocoapp2;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView textView;
    private android.support.v7.widget.Toolbar mainToolBar;

    //shaking sensor
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

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
//        mainToolBar.setNavigationIcon(R.drawable.ic_action_navigation);
        mAuth = FirebaseAuth.getInstance();

        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                /*
                 * The following method, "handleShakeEvent(count):" is a stub //
                 * method you would use to setup whatever you want done once the
                 * device has been shook.
                 */
                handleShakeEvent(count);
            }
        });

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
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.editProfile: {
                Intent editProfileIntent = new Intent(MainActivity.this,EditProfileActivity.class);
                startActivity(editProfileIntent);
                // do your sign-out stuff
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
        Intent nearIntent = new Intent(MainActivity.this, NearbyActivity.class);
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
