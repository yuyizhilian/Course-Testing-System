package com.example.graduatedesign.bean;

public class Problem {

    private String problemID;
    private String answerID;
    private int number;
    private String title;
    private String right;

    public Problem(String problemID,String answerID,int number,String title,String right){
        this.problemID=problemID;
        this.answerID=answerID;
        this.number=number;
        this.title=title;
        this.right=right;
    }

    public void setProblemID(String problemID) {
        this.problemID = problemID;
    }

    public String getProblemID() {
        return problemID;
    }

    public void setAnswerID(String answerID) {
        answerID = answerID;
    }

    public String getAnswerID() {
        return answerID;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public String getRight() {
        return right;
    }

    public String getTitle() {
        return title;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
