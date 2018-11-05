package com.example.randikawann.cocoapp2.fragments;


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

import com.example.randikawann.cocoapp2.models.Friends;
import com.example.randikawann.cocoapp2.adapters.FriendsAdapter;
import com.example.randikawann.cocoapp2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FriendsFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private FriendsAdapter mAdapter;
    private DatabaseReference friendsReference;
    private List<Friends> mAllFriends;
    private String current_User_Id;
    private  FirebaseAuth mAuth;

    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        v = inflater.inflate(R.layout.fragment_friends,container,false);
        Log.i("maintbfgdfs","on  create friends fragment");
        mRecyclerView = v.findViewById(R.id.friendsrecycler);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAllFriends = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        current_User_Id = mAuth.getCurrentUser().getUid();

        friendsReference = FirebaseDatabase.getInstance().getReference("friends").child(current_User_Id);
        friendsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mAllFriends.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Friends friendsRetrieve = postSnapshot.getValue(Friends.class);
                    mAllFriends.add(friendsRetrieve);
            }

                mAdapter = new FriendsAdapter(getContext(),mAllFriends);
                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity() , databaseError.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });




        // Inflate the layout for this fragment
        return v;
    }


}
