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

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder>{
    private Context mContext;
    private List<Friends> mAllFriends;
    private static final String TAG = "MyActivity";

    public FriendsAdapter (Context context, List<Friends> allfriends){
        mContext = context;
        mAllFriends = allfriends;
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
//                String efszdx= mAllusers.get(position).user_name;
                String selected_user_id = mAllFriends.get(position).user_id;
                Intent profileIntent = new Intent(v.getContext() , ProfileActivity.class);
                profileIntent.putExtra("user_id" , selected_user_id);
                mContext.startActivity(profileIntent);
//                Log.d("MyActivity", efszdx);
//                int pos = getAdapterPosition();
//
//                // check if item still exists
//                if(pos != RecyclerView.NO_POSITION){
//                    RvDataItem clickedDataItem = dataItems.get(pos);
//                    Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getName(), Toast.LENGTH_SHORT).show();
//                }
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
        public TextView userName;
        public CircleImageView userImage;
        public TextView userDate;

        @SuppressLint("ResourceType")
        public FriendsViewHolder(View itemView){
            super(itemView);

            userName = itemView.findViewById(R.id.user_name_layout);
            userDate = itemView.findViewById(R.id.user_date_layout);
//            userImage = itemView.findViewById(defaultuser);


        }
    }
}
