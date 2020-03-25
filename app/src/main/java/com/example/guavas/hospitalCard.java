package com.example.afinal;

public class hospitalCard {
    private int imgURL;
    private String title;

    public hospitalCard(int imgURL, String title){
        this.imgURL=imgURL;
        this.title=title;
    }

    public int getImgURL(){
        return imgURL;
    }

    public  void setImgURL(int imgURL){
        this.imgURL=imgURL;
    }

    public String getTitle(){
        return title;
    }

    public  void setTitle(String title){
        this.title=title;
    }

}