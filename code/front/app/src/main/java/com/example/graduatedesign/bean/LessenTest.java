package com.example.graduatedesign.bean;

public class LessenTest {

    private String testID;
    private String testName;

    private int grade;
    private String times;

    public LessenTest(String testID,String testName,String times){
        this.testID=testID;
        this.testName=testName;
        this.times=times;
    }

    public String getTestID() {
        return testID;
    }

    public void setTestID(String testID) {
        this.testID = testID;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }
}
