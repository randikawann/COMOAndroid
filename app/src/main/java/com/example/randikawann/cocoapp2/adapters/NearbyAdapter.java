package com.example.randikawann.cocoapp2.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.randikawann.cocoapp2.R;
import com.example.randikawann.cocoapp2.models.Nearby;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NearbyAdapter extends RecyclerView.Adapter<NearbyAdapter.NearbyViewHolder>{

    private Context mContext;
    private List<Nearby> mNearbyusers;

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
    public void onBindViewHolder(@NonNull NearbyViewHolder holder , int position) {
        Nearby nearbyCurrent = mNearbyusers.get(position);

        holder.userName.setText(nearbyCurrent.getUser_name());
        holder.userDate.setText(nearbyCurrent.getLastupdated());

    }

    @Override
    public int getItemCount() {
        return mNearbyusers.size();
    }

    public class NearbyViewHolder extends RecyclerView.ViewHolder {
        public TextView userName;
        public CircleImageView userImage;
        public TextView userDate;

        public NearbyViewHolder(View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.friends_name_layout);
            userDate = itemView.findViewById(R.id.user_status_layout);
        }
    }
}
