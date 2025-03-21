package com.example.randikawann.cocoapp2.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.randikawann.cocoapp2.MessageActivity;
import com.example.randikawann.cocoapp2.R;
import com.example.randikawann.cocoapp2.models.Chat;
import com.example.randikawann.cocoapp2.models.Friends;
import com.example.randikawann.cocoapp2.models.Message;

import java.util.List;

import static com.example.randikawann.cocoapp2.ProfileActivity.DEFAULT;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private Context mContext;
    private List<Chat> mAllChat;
    View v;
    String friends_user_id;

    public ChatAdapter(Context context , List<Chat> allchat) {
        this.mContext = context;
        this.mAllChat = allchat;

    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent , int viewType) {
        v = LayoutInflater.from(mContext).inflate(R.layout.user_list_layout , parent, false);
        return new ChatAdapter.ChatViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder , final int position) {
        final Chat uploadCurrent = mAllChat.get(position);

        friends_user_id = uploadCurrent.getFriends_id();
        SharedPreferences sharedPreferencesfriends = mContext.getSharedPreferences(friends_user_id, Context.MODE_PRIVATE);
        String user_name = sharedPreferencesfriends.getString("user_name", DEFAULT);
        holder.usermessage.setText(uploadCurrent.getMessage());
        holder.userName.setText(user_name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent messageIntent = new Intent(v.getContext(), MessageActivity.class);
                messageIntent.putExtra("friends_id",uploadCurrent.getFriends_id());
                mContext.startActivity(messageIntent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mAllChat.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {
        public TextView userName;
        public TextView usermessage;

        @SuppressLint("ResourceType")
        public ChatViewHolder(View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.user_name_layout);
            usermessage = itemView.findViewById(R.id.user_status_layout);


        }

    }
}
