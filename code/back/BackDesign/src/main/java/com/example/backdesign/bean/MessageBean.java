package com.example.backdesign.bean;

import lombok.Data;

@Data
public class MessageBean {

    private String messageID;
    private String userID;
    private String lessenID;
    private String msg;
    private String times;
}
