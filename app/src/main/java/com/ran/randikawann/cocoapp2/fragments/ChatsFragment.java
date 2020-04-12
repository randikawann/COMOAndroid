package com.ran.randikawann.cocoapp2.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ran.randikawann.cocoapp2.R;
import com.ran.randikawann.cocoapp2.adapters.ChatAdapter;
import com.ran.randikawann.cocoapp2.models.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatsFragment extends Fragment {
    private List<Chat> mAllChat;
    private RecyclerView mRecyclerView;
    private DatabaseReference chatReference;
    private String current_User_Id = null;
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


        try{
            current_User_Id = mAuth.getCurrentUser().getUid();
        }catch (Exception e){
            Log.i("efiusndc","chatFragment : 1");
        }
        if(current_User_Id == null){
            Log.i("efiusndc","current user null");
        }else {
            chatReference = FirebaseDatabase.getInstance().getReference().child("chat").child(current_User_Id);

            chatReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mAllChat.clear();
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

        }




//        Log.i("chat", "this fragment is work");




        // Inflate the layout for this fragment
        return v;
    }
}
