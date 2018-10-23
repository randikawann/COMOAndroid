package com.example.randikawann.cocoapp2.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.randikawann.cocoapp2.R;
import com.example.randikawann.cocoapp2.models.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>  {
    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    private Context mContext;
    private List<Message> mAllmessages;

    FirebaseUser message_user;

    public MessageAdapter(Context context, List<Message> messages){
        mContext = context;
        mAllmessages = messages;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent , int viewType) {
        if(viewType == MSG_TYPE_RIGHT) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right , parent , false);
            return new MessageAdapter.MessageViewHolder(v);
        }else{
            View v = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left , parent , false);
            return new MessageAdapter.MessageViewHolder(v);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder , int position) {
        Message message = mAllmessages.get(position);
        holder.etshowmessage.setText(message.getMessage());
    }

    @Override
    public int getItemCount() {
        return mAllmessages.size();
    }


    public class MessageViewHolder extends RecyclerView.ViewHolder{
        public TextView etshowmessage;
        @SuppressLint("ResourceType")
        public MessageViewHolder(View itemView){
            super(itemView);

            etshowmessage = itemView.findViewById(R.id.etshowmessage);

        }
    }

    @Override
    public int getItemViewType(int position) {
        message_user = FirebaseAuth.getInstance().getCurrentUser();
        if(mAllmessages.get(position).getSender().equals(message_user.getUid())){
            return MSG_TYPE_RIGHT;
        }else{
            return MSG_TYPE_LEFT;
        }
    }
}
