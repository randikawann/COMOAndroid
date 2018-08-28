package com.example.randikawann.cocoapp2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private FirebaseAuth mAuth;
    private TextView tvUserName;
    private TextView tvStatus;
    private TextView tvGender;
    private TextView tvAge;
    private CircleImageView imgProfile;

    private String userName;
    private String userAge;
    private String userGender;
    private String userStatus;
    private String userimage;

    private DatabaseReference userReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        tvGender = (TextView) findViewById(R.id.tvGender);
        tvAge = (TextView) findViewById(R.id.tvAge);
        imgProfile = (CircleImageView) findViewById(R.id.imgProfile);

        mAuth = FirebaseAuth.getInstance();
        String current_User_Id = mAuth.getCurrentUser().getUid();
        userReference = FirebaseDatabase.getInstance().getReference().child("users").child(current_User_Id);

        //Toolbar
        mToolbar = findViewById(R.id.edit_activity_toolbar);
        setSupportActionBar(mToolbar);
//        getSupportActionBar().setTitle("Profile");



        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    try{
                        userName = dataSnapshot.child("user_name").getValue().toString();
                        userAge = dataSnapshot.child("user_age").getValue().toString();
                        userGender = dataSnapshot.child("user_gender").getValue().toString();
                        userStatus = dataSnapshot.child("user_status").getValue().toString();
                        userimage = dataSnapshot.child("user_img").getValue().toString();
//                    String thumb_image = dataSnapshot.child("user_thumbImg").getValue().toString();

                    }catch (Exception e){
                        Toast.makeText(ProfileActivity.this,"exception",Toast.LENGTH_SHORT).show();
                    }
                    tvUserName.setText(userName);
                    tvAge.setText(userAge);
                    tvStatus.setText(userStatus);
                    tvGender.setText(userGender);
                    //retreve image with picasso
//                    Picasso.get().load(userimage).into(imgProfile);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this,"Data retreving error",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_profile: {
                Intent editProfileIntent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(editProfileIntent);
                // do your sign-out stuff
                break;
            }

            // case blocks for other MenuItems (if any)
        }
        return false;
    }
}
