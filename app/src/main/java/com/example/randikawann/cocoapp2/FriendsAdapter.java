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

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder> {
    private Context mContext;
    private List<Friends> mAllfriends;

    public FriendsAdapter(Context context , List<Friends> allfriends) {
        mContext = context;
        mAllfriends = allfriends;
    }

    @NonNull
    @Override
    public FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent , int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.user_list_layout , parent, false);
        return new FriendsAdapter.FriendsViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull FriendsViewHolder holder , final int position) {
        Friends uploadCurrent = mAllfriends.get(position);
        holder.userDate.setText(uploadCurrent.date);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//this is for selected item
//                String efszdx= mAllusers.get(position).user_name;
//                String selected_user_id= mAllfriends.get(position).user_id;
//                Intent profileIntent = new Intent(v.getContext(),ProfileActivity.class);
//                profileIntent.putExtra("user_id",selected_user_id);
//                mContext.startActivity(profileIntent);
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
    }

    @Override
    public int getItemCount() {
        return mAllfriends.size();
    }


    public class FriendsViewHolder extends RecyclerView.ViewHolder {
        public TextView userDate;

        @SuppressLint("ResourceType")
        public FriendsViewHolder(View itemView) {
            super(itemView);

            userDate = itemView.findViewById(R.id.user_date_layout);


        }

    }
}