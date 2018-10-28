package com.example.randikawann.cocoapp2.models;

public class Chat {
    String reciever_name;
    String message;

    Chat(){

    }

    public Chat(String reciever_name , String message) {
        this.reciever_name = reciever_name;
        this.message = message;
    }

    public String getReciever_name() {
        return reciever_name;
    }

    public void setReciever_name(String reciever_name) {
        this.reciever_name = reciever_name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
