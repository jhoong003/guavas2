package com.example.guavas;

import java.util.Comparator;

public class IllDetail {
    public String IllName;
    public String Description;
    public String Prevention;

    public IllDetail(){
        //Required public constructor
    }

    public IllDetail(String name, String Desc, String prevent){
        IllName = name;
        Description = Desc;
        Prevention = prevent;
    }

    public String getName(){
        return IllName;
    }

    public String getDescription(){
        return Description;
    }

    public String getPrevention(){
        return Prevention;
    }

    //Changed
    public static Comparator<IllDetail> nameComparator = new Comparator<IllDetail>(){
        public int compare(IllDetail h1, IllDetail h2){
            return (int) (h1.getName().compareTo(h2.getName()));
        }
    };





}
