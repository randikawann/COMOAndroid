package com.example.randikawann.cocoapp2.models;

public class Request {
    String friends_id;
    String friends_name;
    String request_type;

    Request(){

    }

    public Request(String friends_id , String friends_name , String request_type) {
        this.friends_id = friends_id;
        this.friends_name = friends_name;
        this.request_type = request_type;
    }

    public String getFriends_id() {

        return friends_id;
    }

    public void setFriends_id(String friends_id) {
        this.friends_id = friends_id;
    }

    public String getFriends_name() {
        return friends_name;
    }

    public void setFriends_name(String friends_name) {
        this.friends_name = friends_name;
    }

    public String getStatus() {
        return request_type;
    }

    public void setStatus(String status) {
        this.request_type = request_type;
    }
}
