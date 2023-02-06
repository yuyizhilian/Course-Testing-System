package com.example.graduatedesign.bean;

public class Problem {

    private String problemID;
    private String testID;
    private int number;

    private int type;
    private String title;
    private String right;

    public Problem(String problemID,String testID,int number,int type,String title,String right){
        this.problemID=problemID;
        this.testID=testID;
        this.type=type;
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

    public String getTestID() {
        return testID;
    }

    public void setTestID(String testID) {
        this.testID = testID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
