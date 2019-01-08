package com.example.randikawann.cocoapp2.adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.randikawann.cocoapp2.ProfileActivity;
import com.example.randikawann.cocoapp2.R;
import com.example.randikawann.cocoapp2.models.Request;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.List;

import static com.example.randikawann.cocoapp2.ProfileActivity.DEFAULT;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {
    private Context mContext;
    private List<Request> mAllRequest;
    private Dialog mDialog;
    private DatabaseReference requestReference;
    private DatabaseReference friendsReference;
    private DatabaseReference userReference;
    private FirebaseAuth mAuth;
    String friends_user_id;
    String current_user_id;
    String friends_user_name;
    String current_user_name;
    private String dateString;

    public RequestAdapter(Context context , List<Request> mAllRequest) {
        this.mContext = context;
        this.mAllRequest = mAllRequest;

    }



    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent , int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.request_list_layout , parent, false);

        final RequestViewHolder vHolder = new RequestViewHolder(v);


        requestReference = FirebaseDatabase.getInstance().getReference().child("friends_request");
        friendsReference = FirebaseDatabase.getInstance().getReference().child("friends");
        userReference = FirebaseDatabase.getInstance().getReference().child("users");
        mAuth = FirebaseAuth.getInstance();
        current_user_id = mAuth.getCurrentUser().getUid();

        long date = System.currentTimeMillis();
        SimpleDateFormat sdf= new SimpleDateFormat("MMM dd yyyy");
        dateString = sdf.format(date);

        return new RequestAdapter.RequestViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final RequestViewHolder holder , int position) {


        Request uploadCurrent = mAllRequest.get(position);
        holder.request_type.setText(uploadCurrent.getStatus());
        friends_user_id = uploadCurrent.getFriends_id();
        final SharedPreferences sharedPreferencesfriends = mContext.getSharedPreferences(friends_user_id, Context.MODE_PRIVATE);
        final SharedPreferences sharedPreferencesusers = mContext.getSharedPreferences(current_user_id, Context.MODE_PRIVATE);

        String user_name = sharedPreferencesfriends.getString("user_name", DEFAULT);
        holder.userName.setText(user_name);

        mDialog = new Dialog(mContext);
        mDialog.setContentView(R.layout.dialog_request);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView tvItem_name = mDialog.findViewById(R.id.tvItem_name);
                TextView tvrequest_type = mDialog.findViewById(R.id.tvrequestType);
                Button btsend_req = mDialog.findViewById(R.id.btsend_req);
                Button btdecline_req = mDialog.findViewById(R.id.btdecline_req);
                ImageView friends_img = mDialog.findViewById(R.id.friends_img);


                String all_friends_id = mAllRequest.get(holder.getAdapterPosition()).getFriends_id();
                SharedPreferences sharedPreferencesAllusers = mContext.getSharedPreferences(all_friends_id, Context.MODE_PRIVATE);
                current_user_name = sharedPreferencesAllusers.getString("user_name", DEFAULT);
                String status = mAllRequest.get(holder.getAdapterPosition()).getStatus();

                tvItem_name.setText(current_user_name);
                tvrequest_type.setText(status);

                if(status.equals("send")){
                    btsend_req.setText("Cancel Friends Request");
                    btdecline_req.setVisibility(View.INVISIBLE);
                    btsend_req.setOnClickListener(new View.OnClickListener() {
//                        cancel friends request
                        @Override
                        public void onClick(View v) {
                            friends_user_id = mAllRequest.get(holder.getAdapterPosition()).getFriends_id();
                            requestReference.child(friends_user_id).child(current_user_id).removeValue();
                            requestReference.child(current_user_id).child(friends_user_id).removeValue();

                            mDialog.dismiss();
                        }
                    });
                }if(status.equals("recieved")){
                    btsend_req.setText("Accept Friends Request");
                    btdecline_req.setText("Decline Friends Request");
                    btsend_req.setOnClickListener(new View.OnClickListener() {
//                    Accept friends request
                        @Override
                        public void onClick(View v) {
                            friends_user_id = mAllRequest.get(holder.getAdapterPosition()).getFriends_id();
                            friends_user_name = sharedPreferencesfriends.getString("user_name", DEFAULT);
                            current_user_name = sharedPreferencesusers.getString("user_name", DEFAULT);

                            requestReference.child(friends_user_id).child(current_user_id).removeValue();
                            requestReference.child(current_user_id).child(friends_user_id).removeValue();

                            friendsReference.child(current_user_id).child(friends_user_id).child("friends_id").setValue(friends_user_id);
                            friendsReference.child(friends_user_id).child(current_user_id).child("friends_id").setValue(current_user_id);
                            friendsReference.child(current_user_id).child(friends_user_id).child("date").setValue(dateString);
                            friendsReference.child(friends_user_id).child(current_user_id).child("date").setValue(dateString);

                            mDialog.dismiss();

                        }
                    });
                    btdecline_req.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            friends_user_id = mAllRequest.get(holder.getAdapterPosition()).getFriends_id();
                            requestReference.child(friends_user_id).child(current_user_id).removeValue();
                            requestReference.child(current_user_id).child(friends_user_id).removeValue();

                            mDialog.dismiss();

                        }
                    });
                }else{
                }


                mDialog.show();


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

        @SuppressLint("ResourceType")
        public RequestViewHolder(View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.friends_name_layout);
            request_type = itemView.findViewById(R.id.user_status_layout);
        }

    }
}
