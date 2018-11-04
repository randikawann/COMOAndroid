package com.example.randikawann.cocoapp2.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.randikawann.cocoapp2.MessageActivity;
import com.example.randikawann.cocoapp2.R;
import com.example.randikawann.cocoapp2.adapters.ChatAdapter;
import com.example.randikawann.cocoapp2.adapters.FriendsAdapter;
import com.example.randikawann.cocoapp2.adapters.MessageAdapter;
import com.example.randikawann.cocoapp2.models.Chat;
import com.example.randikawann.cocoapp2.models.Friends;
import com.example.randikawann.cocoapp2.models.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatsFragment extends Fragment {
    // difficult to solve in this branch....
    //because of turn into another branch 19..
    private List<Chat> mAllChat;
    private RecyclerView mRecyclerView;
    private DatabaseReference chatReference;
    private String current_User_Id;
    private FirebaseAuth mAuth;

    private ChatAdapter chatAdapter;


    View v;
    public ChatsFragment() {
        // Required empty public constructor

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        v = inflater.inflate(R.layout.fragment_chats, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.chatsrecycler);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAllChat = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        current_User_Id = mAuth.getCurrentUser().getUid();

//        Log.i("chat", "this fragment is work");


        chatReference= FirebaseDatabase.getInstance().getReference().child("chat").child(current_User_Id);
        chatReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Chat chatRetrieve = postSnapshot.getValue(Chat.class);
                    mAllChat.add(chatRetrieve);

                }
                chatAdapter = new ChatAdapter(getContext(), mAllChat);
                mRecyclerView.setAdapter(chatAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Inflate the layout for this fragment
        return v;
    }

}
