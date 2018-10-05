package com.example.randikawann.cocoapp2;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {
    private Context mContext;
    private List<Request> mAllRequest;
    private Dialog mDialog;

    public RequestAdapter(Context context , List<Request> mAllRequest) {
        this.mContext = context;
        this.mAllRequest = mAllRequest;

    }



    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent , int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.request_list_layout , parent, false);
        final RequestViewHolder vHolder = new RequestViewHolder(v);

        mDialog = new Dialog(mContext);
        mDialog.setContentView(R.layout.dialog_request);
        TextView dialog_name = (TextView) mDialog.findViewById(R.id.tvItem_name);
        TextView dialog_status = (TextView) mDialog.findViewById(R.id.tvrequestType);
        ImageView dialog_image = (ImageView) mDialog.findViewById(R.id.friends_img);


        vHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,String.valueOf(vHolder.getAdapterPosition()),Toast.LENGTH_SHORT).show();
                mDialog.show();
            }
        });

        return new RequestAdapter.RequestViewHolder(v);

//        dialog initialize
    }

    @Override
    public void onBindViewHolder(@NonNull final RequestViewHolder holder , int position) {
        Request uploadCurrent = mAllRequest.get(position);
        holder.request_type.setText(uploadCurrent.request_type);
        holder.userName.setText(uploadCurrent.friends_name);




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,String.valueOf(holder.getAdapterPosition()),Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return mAllRequest.size();
    }


    public class RequestViewHolder extends RecyclerView.ViewHolder {
        public TextView userName;
        public TextView request_type;
        public TextView item_name;

        @SuppressLint("ResourceType")
        public RequestViewHolder(View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.friends_name_layout);
            request_type = itemView.findViewById(R.id.user_status_layout);
            item_name = itemView.findViewById(R.id.tvItem_name);

        }

    }
}
