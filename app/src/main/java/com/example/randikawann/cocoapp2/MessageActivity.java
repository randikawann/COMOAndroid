package com.example.randikawann.cocoapp2;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.randikawann.cocoapp2.adapters.MessageAdapter;
import com.example.randikawann.cocoapp2.models.Message;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
    private TextView tvfriendsName;
    private ImageButton imgbtsendMessage;
    private EditText etMessagecontent;
    private de.hdodenhof.circleimageview.CircleImageView imgfriendsimage;
    android.support.v7.widget.Toolbar messagetoolbar;

    MessageAdapter messageAdapter;
    List<Message> mMessage;
    RecyclerView recyclerViewMessage;

    private FirebaseAuth mAuth;

    private String friendsName;
    private String friendsId;
    private String currentUserId;

    private FirebaseUser currentUser;
    DatabaseReference messageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        tvfriendsName = findViewById(R.id.tvfriendsName);
        imgbtsendMessage = findViewById(R.id.imgbtsendMessage);
        etMessagecontent = findViewById(R.id.etMessagecontent);
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

        recyclerViewMessage = findViewById(R.id.recyclerViewMessage);
        recyclerViewMessage.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewMessage.setLayoutManager(linearLayoutManager);

        friendsName = getIntent().getExtras().getString("friends_name");
        friendsId = getIntent().getExtras().getString("friends_id");

        mAuth = FirebaseAuth.getInstance();

        currentUserId = mAuth.getCurrentUser().getUid();


        tvfriendsName.setText(friendsName);

        readMessage(currentUserId,friendsId);

        final String message = etMessagecontent.getText().toString();

        if(message==null){
            imgbtsendMessage.setEnabled(false);
        }else{
            imgbtsendMessage.setEnabled(true);
            imgbtsendMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendMessage(currentUserId, friendsId, message);

                }
            });
        }


    }

    public void sendMessage(String sender, String receiver, String message){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("reciever", receiver);
        hashMap.put("message", message);

        reference.child("message").push().setValue(hashMap);
    }

    private void readMessage(final String myid, final String userid){
        mMessage = new ArrayList<>();

        messageReference = FirebaseDatabase.getInstance().getReference().child("message");
        messageReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mMessage.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Message message = snapshot.getValue(Message.class);
                    if(message.getReciever().equals(myid) && message.getSender().equals(userid) ||
                            message.getReciever().equals(userid) && message.getSender().equals(myid)  ){
                        mMessage.add(message);
                    }
                    messageAdapter = new MessageAdapter(MessageActivity.this, mMessage);
                    recyclerViewMessage.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
