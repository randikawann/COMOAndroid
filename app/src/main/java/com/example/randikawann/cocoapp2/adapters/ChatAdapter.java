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
import com.example.randikawann.cocoapp2.models.Chat;
import com.example.randikawann.cocoapp2.models.Friends;
import com.example.randikawann.cocoapp2.models.Message;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private Context mContext;
    private List<Chat> mAllChat;
    View v;

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
    public void onBindViewHolder(@NonNull ChatViewHolder holder , int position) {
        final Chat uploadCurrent = mAllChat.get(position);
        holder.usermessage.setText(uploadCurrent.getMessage());
        holder.userName.setText(uploadCurrent.getReciever_name());

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
