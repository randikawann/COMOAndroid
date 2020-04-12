package com.ran.randikawann.cocoapp2.models;

public class Chat {
    String friends_id;
    String message;

    Chat() {

    }

    public Chat(String friends_id , String message) {
        this.friends_id = friends_id;
        this.message = message;
    }

    public String getFriends_id() {
        return friends_id;
    }

    public void setFriends_id(String friends_id) {
        this.friends_id = friends_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}