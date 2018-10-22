package com.example.randikawann.cocoapp2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class MessageActivity extends AppCompatActivity {
    private TextView tvfriendsName;
    private de.hdodenhof.circleimageview.CircleImageView imgfriendsimage;
    android.support.v7.widget.Toolbar messagetoolbar;
    private String friendsName;

    private FirebaseUser currentUser;
    DatabaseReference message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        tvfriendsName = findViewById(R.id.tvfriendsName);
        messagetoolbar = findViewById(R.id.messagetoolbar);

        setSupportActionBar(messagetoolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        messagetoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        friendsName = getIntent().getExtras().getString("friends_name");


        tvfriendsName.setText(friendsName);






    }
}
