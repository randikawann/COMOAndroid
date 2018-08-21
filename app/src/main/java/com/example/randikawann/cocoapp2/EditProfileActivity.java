package com.example.randikawann.cocoapp2;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfileActivity extends AppCompatActivity {
    private Toolbar mToolbar;

    private EditText etUserName;
    private EditText etAge;
    private EditText etStatus;
    private Spinner spinnerGender;
    private Button btSubmit;
//    private ImageView imgProfile;

    private DatabaseReference userReference;
    private DatabaseReference getUserReference;
    private FirebaseAuth mAuth;

    private String userName;
    private String userAge;
    private String userGender;
    private String userStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //Toolbar
        mToolbar = findViewById(R.id.edit_activity_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Edit Profile");
        //
        etUserName = (EditText) findViewById(R.id.etUserName);
        etAge = (EditText) findViewById(R.id.etAge);
        etStatus = (EditText) findViewById(R.id.etStatus);
        spinnerGender = (Spinner) findViewById(R.id.spinnerGender);
        btSubmit = (Button) findViewById(R.id.btSubmit);
//        imgProfile = (ImageView) findViewById(R.id.imgProfile);

        //Add database Reference to user
        mAuth = FirebaseAuth.getInstance();
        String current_User_Id = mAuth.getCurrentUser().getUid();
        userReference = FirebaseDatabase.getInstance().getReference().child("users").child(current_User_Id);


        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });

        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    userName = dataSnapshot.child("user_name").getValue().toString();
                    userAge = dataSnapshot.child("user_age").getValue().toString();
                    userGender = dataSnapshot.child("user_gender").getValue().toString();
                    userStatus = dataSnapshot.child("user_status").getValue().toString();

                    etUserName.setText(userName);
                    etAge.setText(userAge);
                    etStatus.setText(userStatus);
                    spinnerGender.setSelection(((ArrayAdapter<String>) spinnerGender.getAdapter()).getPosition(userGender));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(EditProfileActivity.this, "Retrieving data error",Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void addUser(){
        userName = etUserName.getText().toString().trim();
        userAge = etAge.getText().toString();
        userStatus = etStatus.getText().toString();
        userGender = spinnerGender.getSelectedItem().toString();

        if(!TextUtils.isEmpty(userName)){
            if(!TextUtils.isEmpty(userStatus)){
                userReference.child("user_name").setValue(userName);
                userReference.child("user_age").setValue(userAge);
                userReference.child("user_status").setValue(userStatus);
                userReference.child("user_gender").setValue(userGender).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(EditProfileActivity.this, "Added data successfuly", Toast.LENGTH_SHORT).show();
                            Intent mainIntent = new Intent(EditProfileActivity.this, MainActivity.class);
                            startActivity(mainIntent);
                        }else{
                            Toast.makeText(EditProfileActivity.this, "User data not added", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }

    }
}
