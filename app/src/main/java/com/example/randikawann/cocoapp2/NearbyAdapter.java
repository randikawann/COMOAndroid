package com.example.randikawann.cocoapp2;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

class NearbyAdapter extends RecyclerView.Adapter<NearbyAdapter.NearbyViewHolder>{

    private Context mContext;
    private List<Nearby> mNearbyusers;
    private Dialog mDialog;

    public NearbyAdapter(Context context, List<Nearby> nearbyusers){
        mContext = context;
        mNearbyusers = nearbyusers;
    }
    @NonNull
    @Override
    public NearbyViewHolder onCreateViewHolder(@NonNull ViewGroup parent , int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.nearby_list_layout , parent, false);




        return new NearbyAdapter.NearbyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final NearbyViewHolder holder , int position) {
        Nearby nearbyCurrent = mNearbyusers.get(position);

        mDialog = new Dialog(mContext);
        mDialog.setContentView(R.layout.dialog_nearby);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        holder.userName.setText(nearbyCurrent.getUser_name());
        holder.userDate.setText(nearbyCurrent.getLastupdated());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView item_name = mDialog.findViewById(R.id.tvItem_name);
                TextView item_update = mDialog.findViewById(R.id.tvlastupdate);
                Button btview_location = mDialog.findViewById(R.id.btview_location);

                item_name.setText(mNearbyusers.get(holder.getAdapterPosition()).getUser_name());
                item_update.setText(mNearbyusers.get(holder.getAdapterPosition()).getLastupdated());
                mDialog.show();

                btview_location.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent mapIntent = new Intent(mContext,MapsActivity.class);

                        mContext.startActivity(mapIntent);
                    }
                });


            }
        });

    }

    @Override
    public int getItemCount() {
        return mNearbyusers.size();
    }

    public class NearbyViewHolder extends RecyclerView.ViewHolder {
        public TextView userName;
        public CircleImageView userImage;
        public TextView userDate;
        public TextView item_name;
        public TextView item_update;

        public NearbyViewHolder(View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.friends_name_layout);
            userDate = itemView.findViewById(R.id.user_status_layout);


        }
    }
}
