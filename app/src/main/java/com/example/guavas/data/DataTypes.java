package com.example.guavas.data;

public class DataTypes {

    private static DataType[] dataTypesVital = new DataType[]{
            new DataType("Alcohol Consumption", "ml", -1, 355),
            new DataType("Blood Glucose", "mg/dL", 72, 140),
            new DataType("Blood Pressure (Systolic)", "mmHg", 120, 139),
            new DataType("Blood Pressure (Diastolic)", "mmHg", 80, 89),
            new DataType("Heart Rate", "BPM", 60, 100),
            new DataType("Insulin", "mlU/L", -1, 999),
            new DataType("Pulse Rate", "/min", 60, 100),
            new DataType("Tricep Skin Thickness", "mm", -1, 31)
    };

    private static DataType[] dataTypesBMI = new DataType[]{
            new DataType("Body-Mass Index", "", 18.5f, 24.9f, true, 2,
                    new DataType[]{
                            new DataType("Height", "cm", -1, 999),
                            new DataType("Weight", "kg", -1, 999)
            }),
            new DataType("Height", "cm", -1, 999, false),
            new DataType("Weight", "kg", -1, 999, false)
    };

    public static DataType getVitalDataType(int type){
        return dataTypesVital[type];
    }

    public static DataType getBMIDataType(int type){
        return dataTypesBMI[type];
    }

}
