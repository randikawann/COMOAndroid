package com.example.randikawann.cocoapp2;

import android.Manifest;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class NearbyActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private NearbyAdapter mAdapter;
    private DatabaseReference gpsReference;
    private DatabaseReference userReference;
    private FirebaseAuth mAuth;
    private List<Nearby> mNearbyUser;

    private String current_User_Id;
    private String current_user_name;
    private String dateString;
    double current_lat;
    double current_lon;


    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);

        mRecyclerView = findViewById(R.id.mRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAuth = FirebaseAuth.getInstance();
        current_User_Id = mAuth.getCurrentUser().getUid();
        gpsLocation();

//        get value from main intent
//        double current_user_lat = (double) getIntent().getExtras().get("lat");
//        double current_user_lon = (double) getIntent().getExtras().get("lon");

        mNearbyUser = new ArrayList<Nearby>();

        gpsReference = FirebaseDatabase.getInstance().getReference("gpslocation");
//        userReference = FirebaseDatabase.getInstance().getReference("users");
//        current_user_name = userReference.child(current_User_Id).child("user_name").toString();
//        Toast.makeText(NearbyActivity.this,current_user_name,Toast.LENGTH_SHORT).show();

        gpsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot postSnapshot :dataSnapshot.getChildren()){


//                    Toast.makeText(NearbyActivity.this,postSnapshot.getChildren().toString(),Toast.LENGTH_SHORT).show();
                    Nearby nearbyRetrieve = postSnapshot.getValue(Nearby.class);
                    double lat = nearbyRetrieve.getLatitute();

                    double lon = nearbyRetrieve.getLongitude();

//                    Toast.makeText(NearbyActivity.this,"users value with near"+lat+" "+lon,Toast.LENGTH_SHORT).show();
                        // lag .01 = 1km, lag .001 = 100m
                    if(current_lat - 0.001 <= lat && lat <= current_lat + 0.001){
                        if(current_lon - 0.001 <= lon && lon <= current_lon + 0.001) {
                            Nearby nearbywith = postSnapshot.getValue(Nearby.class);
                            mNearbyUser.add(nearbywith);

//                            Toast.makeText(NearbyActivity.this , nearbywith.getUser_name() , Toast.LENGTH_SHORT).show();
//                            Toast.makeText(NearbyActivity.this , lat +" and " +lon+" in range" , Toast.LENGTH_SHORT).show();
                        }else{
//                            Toast.makeText(NearbyActivity.this,  lon +" and "+current_lon,Toast.LENGTH_SHORT).show();
                        }
                    }else{
//                        Toast.makeText(NearbyActivity.this,  lat +" and " +lon+" not in range ..... ",Toast.LENGTH_SHORT).show();
                    }
//                    mNearbyUser.add(nearbyRetrieve);


                }
                mAdapter = new NearbyAdapter(NearbyActivity.this,mNearbyUser);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void gpsLocation() {
        //        added current date
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf= new SimpleDateFormat("MMM dd yyyy");
        dateString = sdf.format(date);

        //        update gps location
        gpsReference = FirebaseDatabase.getInstance().getReference().child("gpslocation");
        ActivityCompat.requestPermissions(NearbyActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},123);
        GpsTracker gpsTracker = new GpsTracker(getApplicationContext());
        Location location = gpsTracker.getLocation();
        if(location !=null){
            current_lat = location.getLatitude();
            current_lon = location.getLongitude();
//            Toast.makeText(NearbyActivity.this,"current value with near"+current_lat+" "+current_lon,Toast.LENGTH_SHORT).show();
//            Toast.makeText(NearbyActivity.this,"Lat is " + current_lat,Toast.LENGTH_SHORT).show();
//            gpsReference.child(current_User_Id).child("user_name").setValue(current_user_name);
            gpsReference.child(current_User_Id).child("latitute").setValue(current_lat);
            gpsReference.child(current_User_Id).child("longitude").setValue(current_lon);
            gpsReference.child(current_User_Id).child("lastupdated").setValue(dateString);
//            Toast.makeText(MainActivity.this,"Location Updated",Toast.LENGTH_SHORT).show();

        }else{
//            Toast.makeText(NearbyActivity.this,"Location not Updated....",Toast.LENGTH_SHORT).show();
        }

    }
}
