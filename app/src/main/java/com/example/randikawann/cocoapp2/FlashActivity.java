package com.example.randikawann.cocoapp2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class FlashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);

        Thread thread = new Thread(){

            public void run(){
                try{
                    sleep(3000);
                }catch(Exception e){

                }finally {
                    Intent mainIntent = new Intent(FlashActivity.this,MainActivity.class);
                    startActivity(mainIntent);
                }
            }
        };
        thread.start();
    }
}
