package com.example.guavas.data;

public class DataTypes {

    private static DataType[] dataTypes = new DataType[]{
            new DataType("Blood Glucose", "mg/dL", 72, 140),
            new DataType("Blood Pressure", "mmHg", 80, 139),
            new DataType("Heart Rate", "BPM", 60, 100),
            new DataType("Pulse Rate", "/min", 60, 100)
    };

    public static DataType get(int type){
        return dataTypes[type];
    }

}
