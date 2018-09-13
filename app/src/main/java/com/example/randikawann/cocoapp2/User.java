package com.example.randikawann.cocoapp2;

public class User {
    String id;
    String user_name;
    String user_image;
    String user_age;
    String user_gender;
    String user_status;


    public User() {
    }

    public User(String id , String user_name , String user_image , String user_age , String user_gender , String user_status) {
        this.id = id;
        this.user_name = user_name;
        this.user_image = user_image;
        this.user_age = user_age;
        this.user_gender = user_gender;
        this.user_status = user_status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getUser_age() {
        return user_age;
    }

    public void setUser_age(String user_age) {
        this.user_age = user_age;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    public String getUser_status() {
        return user_status;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }
}