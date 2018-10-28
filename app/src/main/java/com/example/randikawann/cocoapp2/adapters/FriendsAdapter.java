package com.example.randikawann.cocoapp2.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.randikawann.cocoapp2.MessageActivity;
import com.example.randikawann.cocoapp2.R;
import com.example.randikawann.cocoapp2.models.Friends;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder> {
    private Context mContext;
    private List<Friends> mAllFriends;
    View v;

    public FriendsAdapter(Context context , List<Friends> allfriends) {
        this.mContext = context;
        this.mAllFriends = allfriends;

    }

    @NonNull
    @Override
    public FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent , int viewType) {
        v = LayoutInflater.from(mContext).inflate(R.layout.user_list_layout , parent, false);
        return new FriendsAdapter.FriendsViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull final FriendsViewHolder holder , final int position) {

        final Friends uploadCurrent = mAllFriends.get(position);
        holder.userDate.setText(uploadCurrent.getDate());
        holder.userName.setText(uploadCurrent.getFriends_name());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent messageIntent = new Intent(v.getContext(), MessageActivity.class);
                messageIntent.putExtra("friends_id",uploadCurrent.getFriends_id());
                messageIntent.putExtra("friends_name",uploadCurrent.getFriends_name());
                mContext.startActivity(messageIntent);

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