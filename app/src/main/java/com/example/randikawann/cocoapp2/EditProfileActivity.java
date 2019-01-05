package com.example.randikawann.cocoapp2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.randikawann.cocoapp2.models.User;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    private EditText etUserName;
    private EditText etAge;
    private EditText etStatus;
    private Spinner spinnerGender;
    private Button btSubmit;
    private CircleImageView imgProfile;

    //input layouts
    private TextInputLayout textInputUserName;
    private TextInputLayout textInputAge;
    private TextInputLayout textInputStatus;


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
    private String current_User_Id;
    private final static int Gallery_pick = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //add change to branch 02soveimgretrieve
        //merge it to 02editprofile
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //Toolbar
        Toolbar mToolbar = findViewById(R.id.edit_activity_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Edit Profile");
        //
        etUserName = (EditText) findViewById(R.id.etUserName);
        etAge = (EditText) findViewById(R.id.etAge);
        etStatus = (EditText) findViewById(R.id.etStatus);
        spinnerGender = (Spinner) findViewById(R.id.spinnerGender);
        btSubmit = (Button) findViewById(R.id.btSubmit);
        imgProfile = (CircleImageView) findViewById(R.id.imgProfile);
//        imgProfile = (ImageView) findViewById(R.id.imgProfile);

        //input layouts
        textInputUserName = findViewById(R.id.textInputUserName);
        textInputAge = findViewById(R.id.textInputAge);
        textInputStatus = findViewById(R.id.textInputStatus);



        //Add database Reference to user
        mAuth = FirebaseAuth.getInstance();
        current_User_Id = mAuth.getCurrentUser().getUid();
        userReference = FirebaseDatabase.getInstance().getReference().child("users").child(current_User_Id);
        storeProfileImageStorageRef = FirebaseStorage.getInstance().getReference().child("profile_img");

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateUserName() | !validateAge() | !validateStatus()){}
                addUser();
            }
        });

        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if(dataSnapshot.getValue()!=null){
                    etUserName.setText(user.getUser_name());
                    etAge.setText(user.getUser_age());
                    etStatus.setText(user.getUser_status());
                    spinnerGender.setSelection(((ArrayAdapter<String>) spinnerGender.getAdapter()).getPosition(user.getUser_gender()));
//                    etAge.setText(userAge);
//                    etStatus.setText(userStatus);
//                    spinnerGender.setSelection(((ArrayAdapter<String>) spinnerGender.getAdapter()).getPosition(userGender));
                    try {
//                        Glide.with(EditProfileActivity.this).load(user.getUser_image()).into(imgProfile);
                    }catch(Exception e){
                        Log.i("editProfile","exception retrieve image");
                    }
                    //retreve image with picasso
//                    Picasso.get().load(userimage).into(imgProfile);
                    try{
//                        imgProfile.setImageURI(Uri.parse(userimage));
                    }
                    catch(Exception e){
                        Log.i("editProfile" , e.toString());
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("editProfile","exception retrieve image2");
            }
        });
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();

            }
        });

    }

    private void openFileChooser() {
        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(galleryIntent, Gallery_pick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
          if(requestCode==Gallery_pick && resultCode==RESULT_OK && data!=null && data.getData() !=null){
            Uri imageuri = data.getData();
            imgProfile.setImageURI(imageuri);


            // start picker to get image for cropping and then use the image in cropping activity
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
              Picasso.get().load(imageuri).into(imgProfile);
        }
        //get crop image result
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                String user_id = mAuth.getCurrentUser().getUid();
                //save image in firebase storage
                final StorageReference filePath;

                filePath = storeProfileImageStorageRef.child(user_id+".jpeg");
                Log.i("editProfile" , "file path is "+filePath.toString());

                filePath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        String url = taskSnapshot.getStorage().getPath();
                        String url = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                        Log.i("editProfile" , "Url is "+url);

                        if(url!=null) {
                            Log.i("editProfile" , url);
//                        Toast.makeText(EditProfileActivity.this,url,Toast.LENGTH_SHORT).show();
                            userReference.child("user_img").setValue(url);
                        }else{
                            Log.i("editProfile" , "url null");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProfileActivity.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }


    public void addUser(){



//        validation
        if(!validateUserName() | !validateAge() | !validateStatus()){
            return;
        }
        userName = etUserName.getText().toString().trim();
        userAge = etAge.getText().toString();
        userStatus = etStatus.getText().toString();
        userGender = spinnerGender.getSelectedItem().toString();

        userReference.child("user_id").setValue(current_User_Id);
        userReference.child("user_name").setValue(userName);
        userReference.child("user_age").setValue(userAge);

//                userReference.child("user_img").setValue(downloadUrl);
//        userReference.child("user_thumbImg").setValue("user_thumbImg");
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


    private boolean validateUserName(){
        String userNameInput = textInputUserName.getEditText().getText().toString().trim();
        if(userNameInput.isEmpty()){
            textInputUserName.setError("Field can't be empty");
            return false;
        }else if(userNameInput.length()>10){
            textInputUserName.setError("Username too long");
            return false;
        }else{
            textInputUserName.setError(null);
            return true;
        }
    }
    private boolean validateAge(){
        String ageInput = textInputAge.getEditText().getText().toString().trim();
        if(ageInput.isEmpty()){
            textInputAge.setError("Field can't be empty");
            return false;
        }else if(ageInput.matches("[a-zA-Z_]+")){
            textInputAge.setError("Age Must be Number");
            return false;
        }else if(ageInput.length()>2){
            textInputAge.setError("Age too long");
            return false;
        }else{
            textInputAge.setError(null);
            return true;
        }
    }
    private boolean validateStatus(){
        String statusInput = textInputStatus.getEditText().getText().toString().trim();
        if(statusInput.isEmpty()){
            textInputStatus.setError("Field can't be empty");
            return false;
        }else{
            textInputStatus.setError(null);
            return true;
        }
    }
}
