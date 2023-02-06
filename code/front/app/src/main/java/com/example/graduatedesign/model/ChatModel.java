package com.example.graduatedesign.model;

import java.io.Serializable;

public class ChatModel implements Serializable {

    private String userID;
    private String user;  //用户头像

    private String name;   //用户昵称
    private String times;
    private String content;

    public ChatModel(String userID,String user,String name,String times,String content){
        this.userID=userID;
        this.user=user;
        this.name=name;
        this.content=content;
        this.times=times;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimes() {
        return times;
    }

    public String getName() {
        return name;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
