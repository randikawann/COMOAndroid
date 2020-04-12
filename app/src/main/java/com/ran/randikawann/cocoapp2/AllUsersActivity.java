package com.ran.randikawann.cocoapp2;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.ran.randikawann.cocoapp2.adapters.AllUsersAdapter;
import com.ran.randikawann.cocoapp2.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllUsersActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private AllUsersAdapter mAdapter;
    private DatabaseReference mDatabaseRef;
    private List<User> mAllusers;
    private FirebaseAuth mAuth;
    private String current_User_Id;



    Toolbar mToolbar;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);

        //Toolbar
        mToolbar = findViewById(R.id.all_users_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("All Users");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAllusers = new ArrayList<>();
        ///
        mAuth = FirebaseAuth.getInstance();
        current_User_Id = mAuth.getCurrentUser().getUid();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("users");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mAllusers.clear();
                for(DataSnapshot postSnapshot :dataSnapshot.getChildren()){

                    User userRetrieve = postSnapshot.getValue(User.class);
                    if(!userRetrieve.getUser_id().equals(current_User_Id)){
                        mAllusers.add(userRetrieve);
                    }



                }

                mAdapter = new AllUsersAdapter(AllUsersActivity.this,mAllusers);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AllUsersActivity.this,databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
