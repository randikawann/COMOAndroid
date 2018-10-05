package com.example.randikawann.cocoapp2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder> {
    private Context mContext;
    private List<Friends> mAllFriends;

    public FriendsAdapter(Context context , List<Friends> allfriends) {
        this.mContext = context;
        this.mAllFriends = allfriends;

    }

    @NonNull
    @Override
    public FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent , int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.user_list_layout , parent, false);
        return new FriendsAdapter.FriendsViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull FriendsViewHolder holder , final int position) {

        Friends uploadCurrent = mAllFriends.get(position);
        holder.userDate.setText(uploadCurrent.date);
        holder.userName.setText(uploadCurrent.friends_name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
            return mAllFriends.size();
    }


    public class FriendsViewHolder extends RecyclerView.ViewHolder {
        public TextView userName;
        public TextView userDate;

        @SuppressLint("ResourceType")
        public FriendsViewHolder(View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.user_name_layout);
            userDate = itemView.findViewById(R.id.user_status_layout);

        }

    }
}