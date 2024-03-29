package com.example.graduatedesign.bean;

public class Messages {

    private String messageID;
    private String lessenID;
    private String userID;
    private String image;
    private String lessenName;
    private String user;
    private String time;

    private String msg;

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getLessenID() {
        return lessenID;
    }

    public void setLessenID(String lessenID) {
        this.lessenID = lessenID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Messages(String messageID, String lessenID,String userID, String image, String lessenName, String user, String msg, String time){
        this.messageID=messageID;
        this.lessenID=lessenID;
        this.userID=userID;
        this.image=image;
        this.lessenName=lessenName;
        this.user=user;
        this.msg=msg;
        this.time=time;
    }
    public String getImage(){
        return image;
    }
    public void setImage(){
        this.image=image;
    }
    public String getLessenName(){
        return lessenName;
    }
    public void setLessenName(String className) {
        this.lessenName = className;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
