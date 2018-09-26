package com.example.randikawann.cocoapp2;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.List;

public class FriendsFragment extends Fragment {

    View v;
    private RecyclerView mRecyclerView;
    private FriendsAdapter<F, RecyclerView.ViewHolder> mAdapter;

    private DatabaseReference mDatabaseRef;
    private FirebaseAuth mAuth;
    private List<Friends> mAllFriends;

    private String current_user_id;

    Toolbar mToolbar;

    public FriendsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_friends,container,false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.friendsrecycler);
        mRecyclerView.setHasFixedSize(true);
        FriendsAdapter<F, RecyclerView.ViewHolder> friendsAdapter = new FriendsAdapter<F, RecyclerView.ViewHolder>(getContext(),mAllFriends);

        mAuth = FirebaseAuth.getInstance();
        current_user_id = mAuth.getCurrentUser().getUid();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("friends").child(current_user_id);

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot :dataSnapshot.getChildren()){
                    Friends friendsRetrieve = postSnapshot.getValue(Friends.class);

                    mAllFriends.add(friendsRetrieve);
                }
                mAdapter = new FriendsAdapter<F, RecyclerView.ViewHolder>( getContext(),mAllFriends);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        FriendsAdapter<Friends, FriendsViewHolder> firebaseRecyclerAdapter = new FriendsAdapter<Friends, FriendsViewHolder>(
                Friends.class,
                R.layout.user_friends_layout,
                FriendsViewHolder.class,
                mDatabaseRef
        ){
            @NonNull
            @Override
            public FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent , int viewType) {
                return super.onCreateViewHolder(parent , viewType);
            }

            @Override
            public void onBindViewHolder(@NonNull FriendsViewHolder holder , int position) {
                super.onBindViewHolder(holder , position);
                Friends uploadCurrent = mAllFriends.get(position);
            }
        };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }
    public static class FriendsViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public FriendsViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setDate(String date){
            TextView sinceFriendsDate = (TextView) mView.findViewById(R.id.user_date_layout);
            sinceFriendsDate.setText(date);
        }
    }
}
