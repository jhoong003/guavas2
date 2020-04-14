package com.example.guavas.data.database;

import com.example.guavas.data.entity.DataType;

/**
 * This class is a database of a medical data types and its properties.
 * The properties are saved in the <code>DataType</code> object.
 *
 * @see DataType
 */
public class DataTypes {

    /**
     * Vitals specific medical types database.
     */
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

    /**
     * BMI specific medical types database.
     */
    private static DataType[] dataTypesBMI = new DataType[]{
            new DataType("Body-Mass Index", "", 18.5f, 24.9f, true, 2,
                    new DataType[]{
                            new DataType("Height", "cm", -1, 999),
                            new DataType("Weight", "kg", -1, 999)
                    }),
            new DataType("Height", "cm", -1, 999, false),
            new DataType("Weight", "kg", -1, 999, false)
    };

    /**
     * Gets the properties of a medical type from the vitals.
     *
     * @param type the position of the type to get.
     * @return the properties of a medical type.
     */
    public static DataType getVitalDataType(int type) {
        return dataTypesVital[type];
    }

    /**
     * Gets the properties of a medical type from the BMI.
     *
     * @param type the position of the type to get.
     * @return the properties of a medical type.
     */
    public static DataType getBMIDataType(int type) {
        return dataTypesBMI[type];
    }

}
