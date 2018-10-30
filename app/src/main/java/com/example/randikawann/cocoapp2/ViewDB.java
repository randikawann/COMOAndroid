package com.example.randikawann.cocoapp2;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ViewDB extends AppCompatActivity {

    //Access to sqlite
    DatabaseHelper mydb;
    EditText bttemName, bttemsurname, bttemmarks;
    Button btviewdata , btadd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_db);

        btviewdata = findViewById(R.id.btviewdata);
        btadd = findViewById(R.id.btadd);
        bttemName = findViewById(R.id.bttemName);
        bttemsurname = findViewById(R.id.bttemsurname);
        bttemmarks = findViewById(R.id.bttemmarks);

        //sqlite
//        mydb = new DatabaseHelper(this);

//        addData();


    }
//    public void addData(){
//        btadd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String name = bttemName.getText().toString();
//                String sureName = bttemsurname.getText().toString();
//                String marks = bttemmarks.getText().toString();
//                Log.i("database", "name "+name+","+sureName+","+marks);
//
//                boolean isInserted = mydb.insertData(name, sureName, marks);
//                if (isInserted ==true)
//                    Log.i("database", "Data Insert");
//                else
//                    Log.i("database", "Data not Insert");
//
//            }
//        });
//    }

//    public void showAll(){
//        btviewdata.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Cursor res = mydb.getAllData();
//                if(res.getCount() == 0){
//                    showMessage("Error", "Nothing Found");
//                    return ;
//                }
//                StringBuffer buffer = new StringBuffer();
//                while (res.moveToNext()){
//                    buffer.append("Id :"+ res.getString(0)+"\n");
//                    buffer.append("Id :"+ res.getString(1)+"\n");
//                    buffer.append("Id :"+ res.getString(2)+"\n");
//                    buffer.append("Id :"+ res.getString(3)+"\n");
//                    buffer.append("Id :"+ res.getString(4)+"\n");
//                    buffer.append("Id :"+ res.getString(5)+"\n");
//                    buffer.append("Id :"+ res.getString(6)+"\n");
//                    buffer.append("Id :"+ res.getString(7)+"\n");
//                    buffer.append("Id :"+ res.getString(8)+"\n\n");
//                }
//                showMessage("Data", buffer.toString());
//            }
//        });

//    }
    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
