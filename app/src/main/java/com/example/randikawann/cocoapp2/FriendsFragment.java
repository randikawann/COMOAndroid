package com.example.randikawann.cocoapp2;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

    private DatabaseReference mDatabaseRef;
    private List<Friends> mAllFriends;

    Toolbar mToolbar;

    public FriendsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


//        mRecyclerView = mRecyclerView.findViewById(R.id.recyclerView);
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        mAllFriends = new ArrayList<>();
//
//        mDatabaseRef = FirebaseDatabase.getInstance().getReference("friends");
//
//        mDatabaseRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot postSnapshot :dataSnapshot.getChildren()){
//                    Friends friendsRetrieve = postSnapshot.getValue(Friends.class);
//
//                    mAllFriends.add(friendsRetrieve);
//                }
//                mAdapter = new FriendsAdapter( getContext(),mAllFriends);
//                mRecyclerView.setAdapter(mAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(getContext(),databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friends,container,false);
    }

}
