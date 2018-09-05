package com.example.randikawann.cocoapp2;

class Upload {
    String user_name;
    String user_image;
    String id;
    String userAge;
    String userGender;
    String user_status;

    public Upload(String user_name , String user_image , String id , String userAge , String userGender , String user_status) {
        this.user_name = user_name;
        this.user_image = user_image;
        this.id = id;
        this.userAge = userAge;
        this.userGender = userGender;
        this.user_status = user_status;
    }

    public Upload(){}

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getUserStatus() {
        return user_status;
    }

    public void setUserStatus(String userStatus) {
        this.user_status = userStatus;
    }
}
