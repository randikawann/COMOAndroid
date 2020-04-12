package com.ran.randikawann.cocoapp2.adapters;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ran.randikawann.cocoapp2.GpsTracker;
import com.ran.randikawann.cocoapp2.MapsActivity;
import com.ran.randikawann.cocoapp2.MessageActivity;
import com.ran.randikawann.cocoapp2.ProfileActivity;
import com.ran.randikawann.cocoapp2.R;
import com.ran.randikawann.cocoapp2.models.Friends;
import com.ran.randikawann.cocoapp2.models.Nearby;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static com.ran.randikawann.cocoapp2.ProfileActivity.DEFAULT;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder> {
    private Context mContext;
    private Dialog mDialog;
    private List<Friends> mAllFriends;
    double current_lat;
    double current_lon;
    View v;
    private String friends_user_id;
    public FriendsAdapter(Context context , List<Friends> allfriends) {
        this.mContext = context;
        this.mAllFriends = allfriends;

    }

    @NonNull
    @Override
    public FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent , int viewType) {
        v = LayoutInflater.from(mContext).inflate(R.layout.user_list_layout , parent, false);
        return new FriendsAdapter.FriendsViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull final FriendsViewHolder holder , final int position) {

        final Friends uploadCurrent = mAllFriends.get(position);

        friends_user_id = uploadCurrent.getFriends_id();
        final SharedPreferences sharedPreferencesfriends = mContext.getSharedPreferences(friends_user_id, Context.MODE_PRIVATE);
        String friends_name = sharedPreferencesfriends.getString("user_name", DEFAULT);;
        holder.userDate.setText(uploadCurrent.getDate());
        holder.userName.setText(friends_name);

        mDialog = new Dialog(mContext);
        mDialog.setContentView(R.layout.dialog_friends);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Log.i("maintbfgdfst", "Item clicked "+position);
                mDialog.show();
                TextView tvItem_name = mDialog.findViewById(R.id.tvItem_name);
                Button btviewProfile = mDialog.findViewById(R.id.btviewProfile);
                Button btviewLocation = mDialog.findViewById(R.id.btviewLocation);
                Button btmessage = mDialog.findViewById(R.id.btmessage);
                ImageView friends_img = mDialog.findViewById(R.id.friends_img);

                final String all_friends_id = mAllFriends.get(holder.getAdapterPosition()).getFriends_id();
                SharedPreferences sharedPreferencesAllusers = mContext.getSharedPreferences(all_friends_id, Context.MODE_PRIVATE);
                String all_friends_name = sharedPreferencesAllusers.getString("user_name", DEFAULT);

                tvItem_name.setText(all_friends_name);
                String friends_lat;

                // retrieve gps cordination for firebase
                setDataPrefe();

                btviewProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String all_friends_id = mAllFriends.get(holder.getAdapterPosition()).getFriends_id();
                        Intent profileIntent = new Intent(v.getContext(), ProfileActivity.class);
                        profileIntent.putExtra("user_id",all_friends_id);
                        mContext.startActivity(profileIntent);

                        mDialog.dismiss();
                    }
                });

                btviewLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentgps();
                        //get value from gps ref
                        String gps_friends_id = "gps "+all_friends_id;

                        Log.i("maintbfgdfst", "gps freinds id :"+gps_friends_id);

                        //get shared reference
                        SharedPreferences sharedPreferencesfriends = mContext.getSharedPreferences(gps_friends_id, Context.MODE_PRIVATE);
                        String friends_lat = sharedPreferencesfriends.getString("friends_lat", DEFAULT);
                        String friends_lon = sharedPreferencesfriends.getString("friends_lon", DEFAULT);

                        Log.i("maintbfgdfst", "friends_ lat "+friends_lat);
                        Log.i("maintbfgdfst", "friends_ lon "+friends_lon);


                        Intent mapIntent = new Intent(mContext,MapsActivity.class);
                        mapIntent.putExtra("current_user_lat",current_lat);
                        mapIntent.putExtra("current_user_lon",current_lon);
                        mapIntent.putExtra("friends_lat",Double.parseDouble(friends_lat));
                        mapIntent.putExtra("friends_lon",Double.parseDouble(friends_lon));
                        mContext.startActivity(mapIntent);

                        mDialog.dismiss();

                    }
                });

                btmessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent messageIntent = new Intent(v.getContext(), MessageActivity.class);
                        messageIntent.putExtra("friends_id",uploadCurrent.getFriends_id());
                        mContext.startActivity(messageIntent);

                        mDialog.dismiss();
                    }
                });


            }
        });

    }
    private void setDataPrefe() {
        DatabaseReference gpsreference = FirebaseDatabase.getInstance().getReference("gpslocation");
        gpsreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot :dataSnapshot.getChildren()){

                    Nearby nearbyRetrieve = postSnapshot.getValue(Nearby.class);

                    String userID = nearbyRetrieve.getUser_id();
                    SharedPreferences sharedPreferences = mContext.getSharedPreferences("gps "+userID,Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor= sharedPreferences.edit();

                    editor.putString("friends_lat", String.valueOf(nearbyRetrieve.getLatitute()));
                    editor.putString("friends_lon", String.valueOf(nearbyRetrieve.getLongitude()));

                    editor.apply();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemCount() {
            return mAllFriends.size();
    }


    public class FriendsViewHolder extends RecyclerView.ViewHolder {
        public TextView userName;
        public TextView userDate;

        @SuppressLint("ResourceType")
        public FriendsViewHolder(View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.user_name_layout);
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