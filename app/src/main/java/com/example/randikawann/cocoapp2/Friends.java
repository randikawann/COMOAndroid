package com.example.randikawann.cocoapp2;

public class Friends {
    String friends_id;
    String date;
    String friends_name;

    public Friends(){

    }

    public Friends(String friends_id , String date , String friends_name) {
        this.friends_id = friends_id;
        this.date = date;
        this.friends_name = friends_name;
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

    public String getFriends_name() {
        return friends_name;
    }

    public void setFriends_name(String friends_name) {
        this.friends_name = friends_name;
    }
}

