package com.example.randikawann.cocoapp2.models;

public class Nearby{

    String user_id;
    double latitute;
    double longitude;
    String lastupdated;

    public Nearby(){}

    public Nearby(String user_id , double latitute , double longitude , String lastupdated) {
        this.user_id = user_id;
        this.latitute = latitute;
        this.longitude = longitude;
        this.lastupdated = lastupdated;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public double getLatitute() {
        return latitute;
    }

    public void setLatitute(double latitute) {
        this.latitute = latitute;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getLastupdated() {
        return lastupdated;
    }

    public void setLastupdated(String lastupdated) {
        this.lastupdated = lastupdated;
    }
}
