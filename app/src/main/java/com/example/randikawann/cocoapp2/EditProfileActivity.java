package com.example.randikawann.cocoapp2;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoProvider;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {
    private Toolbar mToolbar;

    private EditText etUserName;
    private EditText etAge;
    private EditText etStatus;
    private Spinner spinnerGender;
    private Button btSubmit;

    private DatabaseReference userReference;
    private DatabaseReference getUserReference;
    private FirebaseAuth mAuth;
    private StorageReference storeProfileImageStorageRef;

    private String userName;
    private String userAge;
    private String userGender;
    private String userStatus;
    private String userimage;
    private String downloadUrl;
    private final static int Gallery_pick = 1;

    private CircleImageView imgProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //add change to branch 02soveimgretrieve
        //merge it to 02editprofile
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //Toolbar
        mToolbar = findViewById(R.id.edit_activity_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Edit Profile");
        //
        etUserName = findViewById(R.id.etUserName);
        etAge = findViewById(R.id.etAge);
        etStatus = findViewById(R.id.etStatus);
        spinnerGender = findViewById(R.id.spinnerGender);
        btSubmit = findViewById(R.id.btSubmit);
        imgProfile = findViewById(R.id.imgProfile);

        //Add database Reference to user
        mAuth = FirebaseAuth.getInstance();
        String current_User_Id = mAuth.getCurrentUser().getUid();
        userReference = FirebaseDatabase.getInstance().getReference().child("users").child(current_User_Id);
        storeProfileImageStorageRef = FirebaseStorage.getInstance().getReference().child("profile_img");
        Toast.makeText(EditProfileActivity.this,"call data reference",Toast.LENGTH_SHORT).show();

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
                    try{
                        Toast.makeText(EditProfileActivity.this,"data retrieving...",Toast.LENGTH_SHORT).show();
                        userName = dataSnapshot.child("user_name").getValue().toString();
                        userAge = dataSnapshot.child("user_age").getValue().toString();
                        userGender = dataSnapshot.child("user_gender").getValue().toString();
                        userStatus = dataSnapshot.child("user_status").getValue().toString();
                        userimage = dataSnapshot.child("user_img").getValue().toString();
//                    String thumb_image = dataSnapshot.child("user_thumbImg").getValue().toString();

                    }catch (Exception e){
                        Toast.makeText(EditProfileActivity.this,"exception",Toast.LENGTH_SHORT).show();
                    }
                    etUserName.setText(userName);
                    etAge.setText(userAge);
                    etStatus.setText(userStatus);
                    spinnerGender.setSelection(((ArrayAdapter<String>) spinnerGender.getAdapter()).getPosition(userGender));

                    //retreve image with picasso

//                    Picasso.get().load(userimage).into(imgProfile);


                    Picasso.get() .load(userimage) .resize(50, 50) .centerCrop() .into(imgProfile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(EditProfileActivity.this, "Retrieving data error",Toast.LENGTH_SHORT).show();
            }
        });
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, Gallery_pick);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
          if(requestCode==Gallery_pick && resultCode==RESULT_OK && data!=null){
            Uri imageuri = data.getData();
            // start picker to get image for cropping and then use the image in cropping activity
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }
        //get crop image result
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                String user_id = mAuth.getCurrentUser().getUid();
                //save image in firebase storage
                StorageReference filePath = storeProfileImageStorageRef.child(user_id+".jpg");

                filePath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(EditProfileActivity.this,"Saving your profile to firebase database",Toast.LENGTH_SHORT).show();
                            downloadUrl = task.getResult().getStorage().getDownloadUrl().toString();
                            userReference.child("user_img").setValue(downloadUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(EditProfileActivity.this,"image uploaded succesfully",Toast.LENGTH_SHORT).show();
//                                    Picasso.get() .load(userimage) .resize(50, 50) .centerCrop() .into(imgProfile);
                                    Glide.with(EditProfileActivity.this).load(userimage).diskCacheStrategy(DiskCacheStrategy.ALL).into(imgProfile);
                                }
                            });

                        }else
                            Toast.makeText(EditProfileActivity.this,"Error Occur uploading",Toast.LENGTH_SHORT).show();
                    }
                });
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
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
//                userReference.child("user_img").setValue(downloadUrl);
                userReference.child("user_thumbImg").setValue("user_thumbImg");
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
