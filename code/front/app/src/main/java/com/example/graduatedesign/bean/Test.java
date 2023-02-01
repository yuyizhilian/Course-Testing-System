package com.example.graduatedesign.bean;

public class Test {
    private String testID;
    private String testName;
    private String userID;

    public Test(String testID,String testName,String userID){
        this.testID=testID;
        this.testName=testName;
        this.userID=userID;
    }

    public void setTestID(String testID) {
        this.testID = testID;
    }

    public String getTestID() {
        return testID;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestName() {
        return testName;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }
}
