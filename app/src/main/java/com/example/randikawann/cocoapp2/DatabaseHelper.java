package com.example.randikawann.cocoapp2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.example.randikawann.cocoapp2.models.User;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteAssetHelper {
    private static final String DB_NAME="glenDB.db";
    private static final int DB_VER=1;

    public DatabaseHelper(Context context) {
        super(context , DB_NAME , null , null , DB_VER);
    }

    public String userName(String user_id){
        SQLiteDatabase db=getReadableDatabase ();
        SQLiteQueryBuilder qb=new SQLiteQueryBuilder ();

//        String sqlSelect={"u"}
        return null;
    }
    public List<User> getUser(){
        SQLiteDatabase db=getReadableDatabase ();
        SQLiteQueryBuilder qb=new SQLiteQueryBuilder ();

        String[] sqlSelect={"user_id","user_name","user_img","user_thum_img","user_age","user_gender","user_status","device_token"};
        String sqlTable="users";
        qb.setTables ( sqlTable );

        Cursor c=qb.query ( db,sqlSelect,null,null,null,null,null);

        final  List<User> result =new ArrayList <> (  );
        if(c.moveToFirst ()){

            do {
                result.add ( new User ( c.getString ( c.getColumnIndex ( "user_id"  ) ),
                        c.getString ( c.getColumnIndex ( "user_name"  ) ) ,
                        c.getString ( c.getColumnIndex ( "user_img"  ) ) ,
                        c.getString ( c.getColumnIndex ( "user_thum_img"  ) ) ,
                        c.getString ( c.getColumnIndex ( "user_age"  ) ) ,
                        c.getString ( c.getColumnIndex ( "user_gender"  ) ) ,
                        c.getString ( c.getColumnIndex ( "user_status"  ) ) ,
                        c.getString ( c.getColumnIndex ( "device_token"  ) )
                ));
            }while (c.moveToNext ());

        }


        return result;
    }

    public void addToCart(User user){
        SQLiteDatabase db =getReadableDatabase ();
        String query =String.format ( "INSERT INTO users(user_id,user_name,user_img,user_thum_img,user_age,user_gender,user_status,device_token) VALUES('%S','%S','%S','%S','%S','%S','%S','%S');" ,
                user.getUser_id(),
                user.getUser_name(),
                user.getUser_img(),
                user.getUser_thum_img(),
                user.getUser_age(),
                user.getUser_gender(),
                user.getUser_status(),
                user.getDevice_token());
        db.execSQL ( query );


    }
}

//////////////////////////////////////////////////////////////////////////////
//public class DatabaseHelper extends SQLiteOpenHelper {
//
//    public static final String DATABASE_NAME = "CocoApp2";
//    public static final String TABLE_NAME = "student";
//    public static final String COL_1 = "ID";
//    public static final String COL_2 = "NAME";
//    public static final String COL_3 = "SURNAME";
//    public static final String COL_4 = "MARK";
//
//    public DatabaseHelper(Context context ) {
//        super(context , DATABASE_NAME , null , 1);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        String sqlcode1 = "create table student(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, SURNAME TEXT, MARK TEXT);";
//        String sqlcode = "create table "+TABLE_NAME+" ("+COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COL_2+" TEXT, "+COL_3+" TEXT, "+COL_4+" TEXT);";
//        db.execSQL(sqlcode1);
//
//
////        db.execSQL(CREATE_TABLE);
//
////        db.execSQL("CREATE TABLE "+TABLE_NAME+" ("+COL_1+ "INTEGER NOT NULL PRIMARY KEY, " +
////                                                   COL_2+" TEXT, "+
////                                                    COL_3+" TEXT, "+
////                                                    COL_4+" TEXT, "+
////                                                    COL_5+" TEXT, "+
////                                                    COL_6+" TEXT, "+
////                                                    COL_7+" TEXT, "+
////                                                    COL_8+" TEXT);");
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db , int oldVersion , int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
//        onCreate(db);
//
//    }
//
//    public boolean insertData(String name, String surname, String marks){
//        SQLiteDatabase db = this.getWritableDatabase();
////        String sqlcode1 = "insert into student(NAME,SURNAME, MARK) values("+name+","+surname+", "+marks+");";
////        String sqlcode = "insert into "+TABLE_NAME+" ("+COL_2+","+COL_3+","+COL_4+") values("+name+","+surname+", "+marks+");";
////        db.execSQL(sqlcode1);
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COL_2,name);
//        contentValues.put(COL_3,surname);
//        contentValues.put(COL_4,marks);
//
//
//        long result = db.insert(TABLE_NAME,null,contentValues);
//        if(result == -1){
//            return false;
//        }else
//            return true;
////        return true;
//
//    }
//    public Cursor getAllData(){
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor res = db.rawQuery("select * from "+TABLE_NAME, null);
//        return res;
//    }
//
////    public Cursor getData(){
////        SQLiteDatabase db = this.getWritableDatabase();
////        String query = "SELECT * FROM "+ TABLE_NAME;
////        Cursor data = db.rawQuery(query, null);
////
////        ArrayList<String> listData = new ArrayList<>();
////
////        while(data.moveToNext()){
////            listData.add(data.getString(1));
////
////        }
////
////
////        Log.i("maintv", listData.get(1));
////        return data;
////    }
//
//}
