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

import com.example.randikawann.cocoapp2.ProfileActivity;
import com.example.randikawann.cocoapp2.R;
import com.example.randikawann.cocoapp2.models.User;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllUsersAdapter extends RecyclerView.Adapter<AllUsersAdapter.ImageViewHolder> {
    private Context mContext;
    private List<User> mAllusers;
    private static final String TAG = "MyActivity";

    public AllUsersAdapter(Context context, List<User> allusers){
        mContext = context;
        mAllusers = allusers;
    }
    @NonNull

    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent , int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.user_list_layout , parent, false);
        return new ImageViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder , final int position) {
        //this is add value for the database
        User uploadCurrent = mAllusers.get(position);
        holder.userName.setText(uploadCurrent.getUser_name());
        holder.userStatus.setText(uploadCurrent.getUser_status());
//        holder.userStatus.setText();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String efszdx= mAllusers.get(position).user_name;
                String selected_user_id= mAllusers.get(position).getUser_id();
                Intent profileIntent = new Intent(v.getContext(),ProfileActivity.class);
                profileIntent.putExtra("user_id",selected_user_id);
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
        return mAllusers.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        public TextView userName;
        public CircleImageView userImage;
        public TextView userStatus;

        @SuppressLint("ResourceType")
        public ImageViewHolder(View itemView){
            super(itemView);

            userName = itemView.findViewById(R.id.user_name_layout);
            userStatus = itemView.findViewById(R.id.user_status_layout);
//            userImage = itemView.findViewById(defaultuser);


        }
    }
}
