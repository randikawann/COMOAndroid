package com.example.randikawann.cocoapp2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.bumptech.glide.Glide;
import com.bumptech.glide.Glide;
import com.example.randikawann.cocoapp2.models.User;
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
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private FirebaseAuth mAuth;
    private TextView tvUserName;
    private TextView tvStatus;
    private TextView tvGender;
    private TextView tvAge;
    private Button btsend_req;
    private Button btdecline_req;
    private CircleImageView profileimg;

//    private de.hdodenhof.circleimageview.CircleImageView profileimg;
    private String user_id;
    private String current_User_Id;
    private String userName;
    private String currentuserName;
    private String friendsuserName;
    private String userAge;
    private String userGender;
    private String userStatus;
    private String userimage;

    private String dateString;
    private DatabaseReference userReference;
    private DatabaseReference userReference2;
    private DatabaseReference userReference3;
    private DatabaseReference requestReference;
    private DatabaseReference friendsReference;

    private String CURRENT_STATE="not_friends";
    public static final String DEFAULT = "N/A";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        tvGender = (TextView) findViewById(R.id.tvGender);
        tvAge = (TextView) findViewById(R.id.tvAge);
        btsend_req = (Button) findViewById(R.id.btsend_req);
        btdecline_req = (Button) findViewById(R.id.btdecline_req);
        profileimg = findViewById(R.id.profileimg);
        final ImageView imageView = findViewById(R.id.imageView);

        mAuth = FirebaseAuth.getInstance();

        current_User_Id = mAuth.getCurrentUser().getUid();
        //        this is for user accept task
        userReference2 = FirebaseDatabase.getInstance().getReference().child("users");
        userReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    currentuserName = dataSnapshot.child(current_User_Id).child("user_name").getValue().toString();
//                    Toast.makeText(ProfileActivity.this,"current_user name"+currentuserName,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        added current date
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf= new SimpleDateFormat("MMM dd yyyy");
        dateString = sdf.format(date);

        user_id = getIntent().getExtras().getString("user_id");

        userReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    friendsuserName = dataSnapshot.child(user_id).child("user_name").getValue().toString();
                   }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //friends details not recieving...
            }
        });

//        user_id = current_User_Id;
        userReference = FirebaseDatabase.getInstance().getReference().child("users").child(user_id);
        requestReference = FirebaseDatabase.getInstance().getReference().child("friends_request");
        friendsReference = FirebaseDatabase.getInstance().getReference().child("friends");
        final StorageReference storageReference = FirebaseStorage.getInstance().getReference();

//        button actions



        //Toolbar
        mToolbar = findViewById(R.id.profile_activity_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



//        get shared preferences
        /*
        SharedPreferences sharedPreferences = getSharedPreferences(user_id, Context.MODE_PRIVATE);
        String user_img = sharedPreferences.getString("user_img", DEFAULT);

        if(user_img != null){
//            Picasso.with(ProfileActivity.this)
//                    .load(user_img)
//                    .error(R.drawable.common_google_signin_btn_icon_dark)
//                    .into(imgProfile);
//            Picasso.with(ProfileActivity.this).load(user_img).into(imgProfile);
//            Picasso.get().load(userimage).into(profileimg);
//                        Glide.with(ProfileActivity.this).load(user_img).into(profileimg);
        }
*/

        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    User user = dataSnapshot.getValue(User.class);

                    userimage = dataSnapshot.child("user_img").getValue().toString();


                    tvUserName.setText(user.getUser_name());
                    tvAge.setText(user.getUser_age());
                    tvStatus.setText(user.getUser_status());
                    tvGender.setText(user.getUser_gender());

//                    Log.i("editProfilee" , "Image file path is "+userimage);
//                    Picasso.get().load(userimage).into(profileimg);





                    requestReference.child(current_User_Id).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChild(user_id)) {
                                    String request_type = dataSnapshot.child(user_id).child("request_type").getValue().toString();

                                    if (request_type.equals("send")) { //send=1
                                        CURRENT_STATE = "request_send";
                                        btsend_req.setText("Cancel Friends Request");
                                    } else if (request_type.equals("recieved")) {
                                        CURRENT_STATE = "request_received";
                                        btsend_req.setText("Accept Request");
                                        btdecline_req.setVisibility(View.VISIBLE);
                                    }
                                }
                            else{
                                friendsReference.child(current_User_Id).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @SuppressLint("SetTextI18n")
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.hasChild(user_id)){
                                            CURRENT_STATE = "friends";
                                            btsend_req.setText("Unfriends this person");
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

//                    String url="https://firebasestorage.googleapis.com/v0/b/cocoapp2-4a7f4.appspot.com/o/profile_img%2FGrKVpQ6NtNMx7Wua7YOLaC2YvLy2.jpeg?alt=media&token=083f0697-8d54-4617-8e4e-132ef3d58f5e";

//                    Glide.with(getApplicationContext()).load(url).into(imgProfile);
//                    GlideApp.with(this).load("http://goo.gl/gEgYUd").into(userimage);
//                    GlideApp.with(this /* context */)
//                            .load(storageReference)
//                            .into(imageView);

                    //retreve image with picasso
//                    Picasso.get().load(userimage).into(imgProfile);
//                    imgProfile.setImageURI(Uri.parse(userimage));
//                    Picasso.with(ProfileActivity.this).load(userimage).into(imgProfile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this,"Data retreving error",Toast.LENGTH_SHORT).show();
            }
        });
        btdecline_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CURRENT_STATE.equals("request_received")){
                    declinefriendsreq();
                }
            }
        });
        btsend_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!current_User_Id.equals(user_id)) {
                    if (CURRENT_STATE.equals("not_friends")) { //not friends
                        sendFriendsRequestToUser();
                    }
                    if (CURRENT_STATE.equals("request_send")) { //request send
                        cancelFriendsRequest();
                    }
                    if (CURRENT_STATE.equals("request_received")) { //request received
                        acceptFriendsRequest();
                    }
                    if (CURRENT_STATE.equals("friends")) {
                        unfriendsFriends();
                    }
                }else{

                }
            }
        });
    }


    public void declinefriendsreq() {
        requestReference.child(user_id).child(current_User_Id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                requestReference.child(current_User_Id).child(user_id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        btsend_req.setEnabled(true);
                        btdecline_req.setVisibility(View.INVISIBLE);
                        CURRENT_STATE = "not_friends";
                        btsend_req.setText("Send Request");
                    }
                });
            }
        });
    }

    private void unfriendsFriends() {
        friendsReference.child(current_User_Id).child(user_id).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            friendsReference.child(user_id).child(current_User_Id).removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        btsend_req.setEnabled(true);
                                        CURRENT_STATE = "not_friends";
                                        btsend_req.setText("Send Request");
                                    }
                                }

                            });
                        }
                    }
                });
    }

    public void acceptFriendsRequest() {
        friendsReference.child(current_User_Id).child(user_id).child("date").setValue(dateString) //date must be added to this
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        friendsReference.child(user_id).child(current_User_Id).child("date").setValue(dateString)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        requestReference.child(current_User_Id).child(user_id).removeValue()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    requestReference.child(user_id).child(current_User_Id).removeValue()
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    friendsReference.child(current_User_Id).child(user_id).child("friends_id").setValue(user_id);
                                                                    friendsReference.child(user_id).child(current_User_Id).child("friends_id").setValue(current_User_Id);
                                                                    friendsReference.child(current_User_Id).child(user_id).child("friends_name").setValue(friendsuserName);
                                                                    friendsReference.child(user_id).child(current_User_Id).child("friends_name").setValue(currentuserName);
                                                                    CURRENT_STATE="friends";
                                                                    btsend_req.setText("Unfriends this person");
                                                                    btdecline_req.setVisibility(View.INVISIBLE);
                                                                }
                                                            });
                                                }
                                            });
                                    }
                                });
                    }
                });
    }

    public void cancelFriendsRequest() {
        requestReference.child(user_id).child(current_User_Id).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        requestReference.child(current_User_Id).child(user_id).removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    btsend_req.setEnabled(true);
                                    btdecline_req.setVisibility(View.INVISIBLE);
                                    CURRENT_STATE = "not_friends";
                                    btsend_req.setText("Send Request");
                                }
                            }
                        });
                    }
                });
    }

    private void sendFriendsRequestToUser() {
        requestReference.child(current_User_Id).child(user_id)
                .child("request_type").setValue("send").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    requestReference.child(user_id).child(current_User_Id).child("friends_id").setValue(current_User_Id);
                    requestReference.child(user_id).child(current_User_Id).child("friends_name").setValue(currentuserName);

                    requestReference.child(current_User_Id).child(user_id).child("friends_id").setValue(user_id);
                    requestReference.child(current_User_Id).child(user_id).child("friends_name").setValue(userName);

                    requestReference.child(user_id).child(current_User_Id) // send=1, recieve=2, norequest=0;
                            .child("request_type").setValue("recieved").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                btsend_req.setEnabled(true);
                                btdecline_req.setVisibility(View.INVISIBLE);
                                CURRENT_STATE = "request_send";
                                btsend_req.setText("Cancel Friends Request");
                            }
                        }
                    });
                }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        if(user_id.equals(current_User_Id)) {
            getMenuInflater().inflate(R.menu.menu_profile , menu);
            btsend_req.setVisibility(View.INVISIBLE);
            btdecline_req.setVisibility(View.INVISIBLE);

            return true;
        }else{
            btdecline_req.setVisibility(View.INVISIBLE);
            return false;
        }
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
