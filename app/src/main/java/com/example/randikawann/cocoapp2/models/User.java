package com.example.randikawann.cocoapp2.models;

public class User {
    private String user_id;
    private String user_name;
    private String user_img;
    private String user_age;
    private String user_gender;
    private String user_status;


    public User() {
    }

    public User(String user_id , String user_name , String user_img , String user_age , String user_gender , String user_status) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_img = user_img;
        this.user_age = user_age;
        this.user_gender = user_gender;
        this.user_status = user_status;

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
        return user_img;
    }

    public void setUser_image(String user_img) {
        this.user_img = user_img;
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