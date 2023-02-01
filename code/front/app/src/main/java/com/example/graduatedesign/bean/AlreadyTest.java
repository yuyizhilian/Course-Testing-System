package com.example.graduatedesign.bean;


public class AlreadyTest {

    private String publishID;
    private String testID;
    private String name;
    private int fulls;
    private int numbers;
    private String starts;
    private String ends;


    public AlreadyTest(String publishID,String testID,String name,int fulls,int numbers,String starts,String ends){
        this.publishID=publishID;
        this.name=name;
        this.fulls=fulls;
        this.numbers=numbers;
        this.starts=starts;
        this.ends=ends;
    }

    public String getPublishID() {
        return publishID;
    }

    public void setPublishID(String publishID) {
        this.publishID = publishID;
    }

    public String getTestID() {
        return testID;
    }

    public void setTestID(String testID) {
        this.testID = testID;
    }

    public String getEnds() {
        return ends;
    }

    public void setEnds(String ends) {
        this.ends = ends;
    }

    public int getNumbers() {
        return numbers;
    }

    public void setNumbers(int numbers) {
        this.numbers = numbers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFulls() {
        return fulls;
    }

    public void setFulls(int fulls) {
        this.fulls = fulls;
    }

    public String getStarts() {
        return starts;
    }

    public void setStarts(String starts) {
        this.starts = starts;
    }
}
