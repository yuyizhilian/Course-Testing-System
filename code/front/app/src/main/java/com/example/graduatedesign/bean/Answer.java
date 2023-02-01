package com.example.graduatedesign.bean;

import android.text.Editable;

public class Answer {

    private String answerID;
    private String problemID;
    private String tag;
    private String content;

    public Answer(String problemID,String tag, String content){
        this.problemID=problemID;
        this.tag=tag;
        this.content=content;
    }

    public String getAnswerID() {
        return answerID;
    }

    public void setAnswerID(String answerID) {
        this.answerID = answerID;
    }

    public String getProblemID() {
        return problemID;
    }

    public void setProblemID(String problemID) {
        this.problemID = problemID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
