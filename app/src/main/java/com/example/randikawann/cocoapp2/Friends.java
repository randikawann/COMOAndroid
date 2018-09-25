package com.example.randikawann.cocoapp2;

public class Friends {
    String user_id;
    String user_name;
    String user_image;
    String user_current_date;

    public Friends(){

    }
    public Friends(String user_id , String user_name , String user_image , String user_current_date) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_image = user_image;
        this.user_current_date = user_current_date;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getUser_current_date() {
        return user_current_date;
    }

    public void setUser_current_date(String user_current_date) {
        this.user_current_date = user_current_date;
    }
}

