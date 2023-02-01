package com.example.backdesign.bean;

import lombok.Data;

@Data
public class ShowMessageBean {

    private String messageID;
    private String image;
    private String lessenName;
    private String user;
    private String times;
    private String msg;

    public ShowMessageBean(String messageID,String image,String lessenName,String user,String times,String msg){
        this.messageID=messageID;
        this.image=image;
        this.user=user;
        this.msg=msg;
        this.lessenName=lessenName;
        this.times=times;
    }
}
