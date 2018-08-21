package com.example.randikawann.cocoapp2;

public class User {
    String id;
    String userName;
    String userAge;
    String userGender;
    String userStatus;

    public User() {
    }

    public User(String id, String userName, String userAge, String userGender, String userStatus) {
        this.id = id;
        this.userName = userName;
        this.userAge = userAge;
        this.userGender = userGender;
        this.userStatus = userStatus;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }
}