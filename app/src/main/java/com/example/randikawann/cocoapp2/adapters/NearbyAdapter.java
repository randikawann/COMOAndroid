package com.example.randikawann.cocoapp2.adapters;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.randikawann.cocoapp2.GpsTracker;
import com.example.randikawann.cocoapp2.MapsActivity;
import com.example.randikawann.cocoapp2.R;
import com.example.randikawann.cocoapp2.models.Nearby;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.randikawann.cocoapp2.ProfileActivity.DEFAULT;

public class NearbyAdapter extends RecyclerView.Adapter<NearbyAdapter.NearbyViewHolder>{
    private Context mContext;
    private List<Nearby> mNearbyusers;
    private Dialog mDialog;
    private double current_lat;
    private double current_lon;
    private String friends_user_id;
    public FirebaseAuth mAuth;

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

        friends_user_id = nearbyCurrent.getUser_id();
        final SharedPreferences sharedPreferencesfriends = mContext.getSharedPreferences(friends_user_id, Context.MODE_PRIVATE);
        //Get preference
        String user_name = sharedPreferencesfriends.getString("user_name", DEFAULT);
        holder.userName.setText(user_name);
        holder.userDate.setText(nearbyCurrent.getLastupdated());

        //get current gps value
        currentgps();

//        Toast.makeText(mContext,current_lat + "&&" + current_lon,Toast.LENGTH_SHORT ).show();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView item_name = mDialog.findViewById(R.id.tvItem_name);
                TextView item_update = mDialog.findViewById(R.id.tvlastupdate);
                Button btview_location = mDialog.findViewById(R.id.btview_location);

                String user_id = mNearbyusers.get(holder.getAdapterPosition()).getUser_id();
                SharedPreferences sharedPreferencesfriends = mContext.getSharedPreferences(user_id, Context.MODE_PRIVATE);
                String user_name = sharedPreferencesfriends.getString("user_name", DEFAULT);
                item_name.setText(user_name);
                item_update.setText(mNearbyusers.get(holder.getAdapterPosition()).getLastupdated());
                mDialog.show();

                btview_location.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent mapIntent = new Intent(mContext,MapsActivity.class);
                        mapIntent.putExtra("current_user_lat",current_lat);
                        mapIntent.putExtra("current_user_lon",current_lon);
                        mapIntent.putExtra("friends_lat",mNearbyusers.get(holder.getAdapterPosition()).getLatitute());
                        mapIntent.putExtra("friends_lon",mNearbyusers.get(holder.getAdapterPosition()).getLongitude());
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
    public void currentgps(){
        ActivityCompat.requestPermissions((Activity) mContext ,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},123);
        GpsTracker gpsTracker = new GpsTracker(mContext.getApplicationContext());
        Location location = gpsTracker.getLocation();
        if(location !=null){
            current_lat = location.getLatitude();
            current_lon = location.getLongitude();

        }else{
//            Toast.makeText(NearbyActivity.this,"Location not Updated....",Toast.LENGTH_SHORT).show();
        }
    }
}
