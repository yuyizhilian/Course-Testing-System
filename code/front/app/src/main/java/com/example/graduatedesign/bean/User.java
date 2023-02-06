package com.example.graduatedesign.bean;

public class User {

    private String userID;
    private String userName;
    private String signature;
    private String password;
    private String identity;
    private String image;
    private String phone;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getIdentity() {
        return identity;
    }

    public String getImage() {
        return image;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
