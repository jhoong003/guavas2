package com.example.guavas;

import com.example.guavas.data.IllDetail;

import java.util.Comparator;

public class Hospital {
    private String hospitalName;
    private String hospitalAddress;
    private String hospitalWebsite;
    private String hospitalDescription;
    private String hospitalNumber;
    private int imgURL;

    public Hospital(String hospitalName, String hospitalAddress, String hospitalNumber /* String hospitalWebsite,*/, String HospitalDescription, int imgURL){
        //this.hospitalWebsite=hospitalWebsite;
        this.hospitalName=hospitalName;
        this.hospitalAddress=hospitalAddress;
        this.hospitalNumber=hospitalNumber;
        this.hospitalDescription=HospitalDescription;
        this.imgURL=imgURL;
    }


    public int getImgURL(){
        return imgURL;
    }

    public  void setImgURL(int imgURL){
        this.imgURL=imgURL;
    }

    public String getName(){
        return hospitalName;
    }

    public  void setName(String hospitalName){
        this.hospitalName=hospitalName;
    }


    /* public String getHospitalWebsite(){
         return hospitalWebsite;
     }
     public void setHospitalWebsite(String hospitalWebsite) {
         this.hospitalWebsite = hospitalWebsite;
     }*/
    public String getAddress(){
        return hospitalAddress;
    }

    public void setAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }
    public String getDescription(){
        return hospitalDescription;
    }
    public void setDescription(String hospitalDescription) {
        this.hospitalDescription = hospitalDescription;
    }
    public String getNumber(){
        return hospitalNumber;
    }
    public void setNumber(String hospitalNumber) {
        this.hospitalNumber = hospitalNumber;
    }

    public static Comparator<Hospital> nameComparator = new Comparator<Hospital>(){
        public int compare(Hospital h1, Hospital h2){
            return (int) (h1.getName().compareTo(h2.getName()));
        }
    };






}
