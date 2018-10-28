package com.example.randikawann.cocoapp2.models;

public class Message {

    private String sender;
    private String reciever;
    private String reciever_name;
    private String message;

    public Message() {

    }
    public Message(String sender , String reciever ,String reciever_name, String message) {
        this.sender = sender;
        this.reciever = reciever;
        this.message = message;
        this.reciever_name = reciever_name;
    }

    public String getReciever_name() {
        return reciever_name;
    }

    public void setReciever_name(String reciever_name) {
        this.reciever_name = reciever_name;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
