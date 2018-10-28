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
    private List<Message> mAllmessages;
    private RecyclerView mRecyclerView;
    private DatabaseReference messageReference;
    private String current_User_Id;
    private FirebaseAuth mAuth;

    ChatAdapter chatAdapter;


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

        mAllmessages = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        current_User_Id = mAuth.getCurrentUser().getUid();

//        Log.i("chat", "this fragment is work");


        messageReference = FirebaseDatabase.getInstance().getReference().child("message");
        messageReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mAllmessages.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Message message = snapshot.getValue(Message.class);
                    mAllmessages.add(message);
                    ////////////////////////////////
                    List<String> mAllname;
                    mAllname = new ArrayList<>();
                    mAllname.add(message.getReciever_name());
                    sort(mAllname);
                }

                chatAdapter = new ChatAdapter(getContext(), mAllmessages);
                mRecyclerView.setAdapter(chatAdapter);
            }

            private void sort(List<String> reciever_name) {
                String temp = null;

                for(int j=0;j<reciever_name.size();j++) {
                    for(int i = 0; i<reciever_name.size()-1;i++) {
                        if(reciever_name.get(i).compareTo(reciever_name.get(i + 1))<0) {
//                            temp = reciever_name.get(i);
//                            array[i] = array[i+1];
//                            array[i+1] = temp;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Inflate the layout for this fragment
        return v;
    }

}
