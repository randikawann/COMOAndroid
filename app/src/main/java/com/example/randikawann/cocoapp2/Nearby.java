package com.example.randikawann.cocoapp2;

public class Nearby{
    String user_id;
    String user_name;
    double latitute;
    double longitude;
    String lastupdated;

    public Nearby(){}

    public Nearby(String user_id , String user_name , double latitute , double longitude , String lastupdated) {
        this.user_id = user_id;
        this.user_name = user_name;
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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
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
