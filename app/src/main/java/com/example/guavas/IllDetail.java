package com.example.guavas;

public class IllDetail {
    public String IllName;
    public String Description;
    public String Prevention;
    public IllDetail(){

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


}
