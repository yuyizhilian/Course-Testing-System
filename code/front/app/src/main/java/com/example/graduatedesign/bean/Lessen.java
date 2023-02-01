package com.example.graduatedesign.bean;

public class Lessen {

    private String lessenID;
    private String lessenName;
    private String image;

    public String getLessenID() {
        return lessenID;
    }

    public void setLessenID(String lessenID) {
        this.lessenID = lessenID;
    }
    public Lessen(String lessenID,String lessenName, String image){
        this.lessenID=lessenID;
        this.lessenName=lessenName;
        this.image=image;
    }
    public String getLessenName(){
        return lessenName;
    }

    public void setLessenName(String lessenName){
        this.lessenName=lessenName;
    }

    public String getImage(){
        return image;
    }

    public void setImage(String image){
        this.image=image;
    }
}
