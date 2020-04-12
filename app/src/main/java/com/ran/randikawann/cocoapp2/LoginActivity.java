package com.ran.randikawann.cocoapp2;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    private Button btVerify;
    private EditText etPhoneNum;

    private Dialog mDialog;

    private TextInputLayout textInputphoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btVerify = findViewById(R.id.btVerify);
        etPhoneNum = findViewById(R.id.etphoneNum);

        textInputphoneNum = findViewById(R.id.textInputphoneNum);

        mAuth = FirebaseAuth.getInstance();


        mDialog = new Dialog(LoginActivity.this);
        mDialog.setContentView(R.layout.dialog_login);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        btVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText etMobile_code = mDialog.findViewById(R.id.etMobile_code);
                Button btverify_code = mDialog.findViewById(R.id.btverify_code);

                String phoneNum = etPhoneNum.getText().toString();

                //validate
                if(!validatePhoneNum()){
                    return;
                }

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            phoneNum,        // Phone number to verify
                            60,                 // Timeout duration
                            TimeUnit.SECONDS,   // Unit of timeout
                            LoginActivity.this,               // Activity (for callback binding)
                            mCallbacks);        // OnVerificationStateChangedCallbacks

                mDialog.show();
                btverify_code.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String verificationCode = etMobile_code.getText().toString();

                        //validate code
                        if (verificationCode.isEmpty()) {
                            Toast.makeText(LoginActivity.this,"Field can't be empty",Toast.LENGTH_LONG).show();
                            return;
                        }else if(verificationCode.equals(mVerificationId)){
                            Toast.makeText(LoginActivity.this,"Verification code doens't match",Toast.LENGTH_LONG).show();
                            return;
                        }



                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verificationCode);
                        Toast.makeText(LoginActivity.this,"Verification code matched",Toast.LENGTH_LONG).show();
                        mDialog.dismiss();
                        signInWithPhoneAuthCredential(credential);


                    }
                });

            }

        });
        //onVerifi
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                mDialog.dismiss();
                Toast.makeText(LoginActivity.this,"Automatically validate code",Toast.LENGTH_LONG).show();
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(LoginActivity.this,"There was some error in Verification",Toast.LENGTH_SHORT).show();
//                tvError.setText("There was some error in Verification");
//                tvError.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number,

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
                Toast.makeText(LoginActivity.this,"Verification code sent",Toast.LENGTH_SHORT).show();
            }
        };
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                          //this is must be change as phone number.

                            Intent mainIntent = new Intent(LoginActivity.this,EditProfileActivity.class);
                            startActivity(mainIntent);

                            finish();


                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Toast.makeText(LoginActivity.this,"There was some error in login",Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }





    private boolean validatePhoneNum() {
        String userPhoneNum = textInputphoneNum.getEditText().getText().toString().trim();
        if (userPhoneNum.isEmpty()) {
            textInputphoneNum.setError("Field can't be empty");
            return false;
        } else if(isLeadingDigit(userPhoneNum)){
            textInputphoneNum.setError("Start with '+94'");
            return false;
        }else if(userPhoneNum.matches("[a-zA-Z_]+")){
            textInputphoneNum.setError("Age Must be Number");
            return false;
        }else if (userPhoneNum.length() > 12) {
            textInputphoneNum.setError("Mobile Number too long");
            return false;
        } else {
            textInputphoneNum.setError(null);
            return true;
        }
    }

    public boolean isLeadingDigit(final String value){
        final char c = value.charAt(0);
        if(c=='+'){
            return false;
        }else{
            return true;
        }
    }

}

