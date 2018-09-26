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

import com.google.firebase.database.DatabaseReference;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendsAdapter<F, F1 extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder>{
    private Context mContext;
    private List<Friends> mAllFriends;

    public FriendsAdapter (Context context, List<Friends> allfriends){
        this.mContext = context;
        this.mAllFriends = allfriends;
    }

    public FriendsAdapter(Class<F> friendsClass , int user_friends_layout , Class<F1> friendsViewHolderClass , DatabaseReference mDatabaseRef) {
    }

    @NonNull
    @Override
    public FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent , int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.user_friends_layout , parent, false);

        return new FriendsAdapter.FriendsViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull FriendsAdapter.FriendsViewHolder holder , final int position) {
        //this is add value for the database
        Friends uploadCurrent = mAllFriends.get(position);
        holder.userName.setText(uploadCurrent.user_name);
        holder.userDate.setText(uploadCurrent.user_current_date);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
//        holder.userImage.setImageURI(R.drawable.defaultuser);
//        holder.userImage.setImageResource(R.drawable.defaultuser);

    }

    @Override
    public int getItemCount() {
        return mAllFriends.size();
    }

    public class FriendsViewHolder extends RecyclerView.ViewHolder{
        private TextView userName;
        private CircleImageView userImage;
        private TextView userDate;

        @SuppressLint("ResourceType")
        public FriendsViewHolder(View itemView){
            super(itemView);

            userName = itemView.findViewById(R.id.user_name_layout);
            userDate = itemView.findViewById(R.id.user_date_layout);
//            userImage = itemView.findViewById(defaultuser);


        }
    }
}
