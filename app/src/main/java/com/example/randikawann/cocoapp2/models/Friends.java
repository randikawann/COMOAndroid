package com.example.randikawann.cocoapp2.models;

import com.example.randikawann.cocoapp2.models.User;

public class Friends extends User {
    String friends_id;
    String date;

    public Friends(){

    }

    public Friends(String friends_id , String date ) {
        this.friends_id = friends_id;
        this.date = date;
    }

    public String getFriends_id() {
        return friends_id;
    }

    public void setFriends_id(String friends_id) {
        this.friends_id = friends_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}

