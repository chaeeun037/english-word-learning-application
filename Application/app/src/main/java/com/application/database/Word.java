package com.application.database;

public class Word {

    private int unit_id;
    private int id;
    private String korean;
    private String english;
    private String imageSrc;
    private String shadowSrc;


    public void setUnit_id(int unit_id){
        this.unit_id = unit_id;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setKorean(String korean){
        this.korean = korean;
    }

    public void setEnglish(String english){
        this.english = english;
    }

    public void setImageSrc(String imageSrc){
        this.imageSrc = imageSrc;
    }

    public void setShadowSrc(String shadowSrc){
        this.shadowSrc = shadowSrc;
    }

    public int getUnit_id(){
        return unit_id;
    }

    public int getId(){
        return id;
    }

    public String getKorean(){
        return korean;
    }

    public String getEnglish(){
        return english;
    }

    public String getImageSrc(){
        return imageSrc;
    }

    public String getShadowSrc(){
        return shadowSrc;
    }
}
