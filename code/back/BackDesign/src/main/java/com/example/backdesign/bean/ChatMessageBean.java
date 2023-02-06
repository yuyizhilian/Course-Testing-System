package com.example.backdesign.bean;

import lombok.Data;

@Data
public class ChatMessageBean {

    private String userID;
    private String image;  //用户头像

    private String name;   //用户昵称
    private String times;
    private String content;

    public ChatMessageBean(String userID,String image,String name,String content,String times){
        this.userID=userID;
        this.image=image;
        this.name=name;
        this.content=content;
        this.times=times;
    }
}
