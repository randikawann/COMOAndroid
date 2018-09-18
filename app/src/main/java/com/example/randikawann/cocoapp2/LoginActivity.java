package com.example.randikawann.cocoapp2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private DatabaseReference userReference;

    private Button btVerify;
    private EditText etPhoneNum;
    private EditText etVerifyCode;
    private int btntype=0;
    private TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btVerify = findViewById(R.id.btVerify);
        etPhoneNum = findViewById(R.id.etphoneNum);
        etVerifyCode = findViewById(R.id.etVerifyCode);
        tvError = findViewById(R.id.tvError);

        mAuth = FirebaseAuth.getInstance();

        userReference = FirebaseDatabase.getInstance().getReference().child("users");

        etVerifyCode.setEnabled(false);

        btVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(btntype==0) {
                    etVerifyCode.setVisibility(View.INVISIBLE);
//                    progressBar1.setVisibility(View.VISIBLE);
//                    etPhoneNum.setVisibility(View.INVISIBLE);
                    btVerify.setVisibility(View.VISIBLE);

                    String phoneNum = etPhoneNum.getText().toString();

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            phoneNum,        // Phone number to verify
                            60,                 // Timeout duration
                            TimeUnit.SECONDS,   // Unit of timeout
                            LoginActivity.this,               // Activity (for callback binding)
                            mCallbacks);        // OnVerificationStateChangedCallbacks
                }else{
                    btVerify.setEnabled(true);
                    etVerifyCode.setVisibility(View.VISIBLE);
//                    progressBar1.setVisibility(View.INVISIBLE);
                    String verificationCode = etVerifyCode.getText().toString();

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verificationCode);
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });
        //onVerifi
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                tvError.setText("There was some error in Verification");
                tvError.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
//                Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                btntype=1;
                //etPhoneNum.setVisibility(View.INVISIBLE);
                etVerifyCode.setVisibility(View.VISIBLE);
                btVerify.setText("Verify Code");
                btVerify.setEnabled(true);


                // ...
            }
        };
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String current_user_id = mAuth.getCurrentUser().getUid();
                            String deviceToken = FirebaseInstanceId.getInstance().getToken();

                            userReference.child(current_user_id).child("device_token").setValue(deviceToken)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // Sign in success, update UI with the signed-in user's information
                //                          //this is must be change as phone number.

                                            Intent mainIntent = new Intent(LoginActivity.this,EditProfileActivity.class);
                                            startActivity(mainIntent);

                                            finish();
                                        }
                                    });



                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            tvError.setText("There was some error in login");
                            tvError.setVisibility(View.VISIBLE);
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

}


