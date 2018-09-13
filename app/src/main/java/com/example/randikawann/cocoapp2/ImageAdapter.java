package com.example.randikawann.cocoapp2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<User> mAllusers;

    public ImageAdapter (Context context, List<User> allusers){
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
    public void onBindViewHolder(@NonNull ImageViewHolder holder , int position) {
        //this is add value for the database
        User uploadCurrent = mAllusers.get(position);
        holder.userName.setText(uploadCurrent.user_name);
        holder.userStatus.setText(uploadCurrent.user_status);
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
