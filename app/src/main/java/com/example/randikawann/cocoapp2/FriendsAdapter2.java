package com.example.randikawann.cocoapp2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class FriendsAdapter2 extends RecyclerView.Adapter<FriendsAdapter2.FriendsViewHolder>{
    private Context mContext;
    private List<Friends> mAllFriends;
    private List<User> mfriendsUser;

    public FriendsAdapter2(Context context , List<User> friendsuser) {
        this.mContext = context;
        this.mfriendsUser= friendsuser;
    }

    @NonNull
    @Override
    public FriendsAdapter2.FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent , int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.user_list_layout , parent, false);
        return new FriendsAdapter2.FriendsViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull FriendsAdapter2.FriendsViewHolder holder , final int position) {

        User uploadCurrent = mfriendsUser.get(position);
        holder.userName.setText(uploadCurrent.user_name);
        holder.userStatus.setText(uploadCurrent.user_status);




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mfriendsUser.size();
    }


    public class FriendsViewHolder extends RecyclerView.ViewHolder {
        public TextView userName;
        public TextView userStatus;

        @SuppressLint("ResourceType")
        public FriendsViewHolder(View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.user_name_layout);
            userStatus = itemView.findViewById(R.id.user_status_layout);
        }

    }
}
