package com.example.guavas.data;

import java.util.Comparator;

public class IllDetail {
    private String illName;
    private String description;
    private String prevention;
    private boolean isAllergy;
    private boolean isInherited;

    public IllDetail(String name, String Desc, String prevent){
        illName = name;
        description = Desc;
        prevention = prevent;
    }

    public IllDetail(String name, String desc, String prevent, boolean isAnAllergy, boolean isInheritable){
        illName = name;
        description = desc;
        prevention = prevent;
        isAllergy = isAnAllergy;
        isInherited = isInheritable;
    }

    public String getName(){
        return illName;
    }

    public String getDescription(){
        return description;
    }

    public String getPrevention(){
        return prevention;
    }

    public static Comparator<IllDetail> nameComparator = new Comparator<IllDetail>(){
        public int compare(IllDetail h1, IllDetail h2){
            return (int) (h1.getName().compareTo(h2.getName()));
        }
    };





}
