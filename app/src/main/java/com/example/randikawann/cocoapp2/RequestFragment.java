package com.example.randikawann.cocoapp2;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class RequestFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RequestAdapter mAdapter;
    private DatabaseReference friendsReference;
    private List<Request> mAllrequest;
    private String current_User_Id;
    private FirebaseAuth mAuth;

    View v;
    public RequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_request,container,false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.friendsrecycler);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        mAllrequest = new ArrayList<Request>();
        mAuth = FirebaseAuth.getInstance();
        try {
            current_User_Id = mAuth.getCurrentUser().getUid();
            friendsReference = FirebaseDatabase.getInstance().getReference("friends_request").child(current_User_Id);
            friendsReference.addValueEventListener(new ValueEventListener() {
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                        Request requestsRetrieve = postSnapshot.getValue(Request.class);
                        mAllrequest.add(requestsRetrieve);
                    }

                    mAdapter = new RequestAdapter(getContext(),mAllrequest);
                    mRecyclerView.setAdapter(mAdapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getActivity() , databaseError.getMessage() , Toast.LENGTH_SHORT).show();
                }
            });

        }catch(Exception e){}





        // Inflate the layout for this fragment
        return v;
    }

}