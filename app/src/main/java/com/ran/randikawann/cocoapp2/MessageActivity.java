package com.ran.randikawann.cocoapp2;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.ran.randikawann.cocoapp2.adapters.MessageAdapter;
import com.ran.randikawann.cocoapp2.models.Message;
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

import static com.ran.randikawann.cocoapp2.ProfileActivity.DEFAULT;

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
    DatabaseReference userReference;

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

        friendsId = getIntent().getExtras().getString("friends_id");

        //SharedPreferences
        SharedPreferences sharedPreferencesfriends = getSharedPreferences(friendsId, Context.MODE_PRIVATE);
        String friends_name = sharedPreferencesfriends.getString("user_name", DEFAULT);


        mAuth = FirebaseAuth.getInstance();

        currentUserId = mAuth.getCurrentUser().getUid();

        tvfriendsName.setText(friends_name);

        readMessage(currentUserId,friendsId);

        imgbtsendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textfieldString = etMessagecontent.getText().toString();

                if(!textfieldString.equals("")) {
                    sendMessage(currentUserId , friendsId , textfieldString);
                    etMessagecontent.setText("");
                }else{
                    Toast.makeText(MessageActivity.this,"Enter your Message",Toast.LENGTH_SHORT).show();
                }
            }
        });




    }

    public void sendMessage(String sender, String receiver, final String message){

        DatabaseReference chatReference = FirebaseDatabase.getInstance().getReference().child("chat").child(currentUserId).child(friendsId);
        DatabaseReference chatReference1 = FirebaseDatabase.getInstance().getReference().child("chat").child(friendsId).child(currentUserId);
        chatReference.child("friends_id").setValue(friendsId);
        chatReference.child("message").setValue(message);

        chatReference1.child("friends_id").setValue(currentUserId);
        chatReference1.child("message").setValue(message);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("reciever", receiver);
        hashMap.put("message", message);

        reference.child("message").push().setValue(hashMap);
        etMessagecontent.setText("");
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
//                        Log.i("intent","message is "+message.getReciever());
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
