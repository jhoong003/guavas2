package com.example.guavas.data;

import android.os.Parcel;
import android.os.Parcelable;

public class DataType implements Parcelable {

    private String dataTypeName;
    private String measurementUnit;
    private float minNormal;
    private float maxNormal;
    private boolean compound = false;
    private int numOfCompound;
    private DataType[] compounds;
    private boolean editable = true;

    public DataType(String name){
        dataTypeName = name;
    }

    public DataType(String name, String unit, float min, float max){
        dataTypeName = name;
        measurementUnit = unit;
        minNormal = min;
        maxNormal = max;
    }

    public DataType(String name, String unit, float min, float max, boolean isEditable){
        dataTypeName = name;
        measurementUnit = unit;
        minNormal = min;
        maxNormal = max;
        editable = isEditable;
    }

    public DataType(String name, String unit, float min, float max, boolean compound, int compoundCount, DataType[]compounds){
        dataTypeName = name;
        measurementUnit = unit;
        minNormal = min;
        maxNormal = max;
        this.compound = compound;
        numOfCompound = compoundCount;
        this.compounds = compounds;
    }

    protected DataType(Parcel in) {
        dataTypeName = in.readString();
        measurementUnit = in.readString();
        minNormal = in.readFloat();
        maxNormal = in.readFloat();
        compound = in.readInt() == 1;
        numOfCompound = in.readInt();
        compounds = in.createTypedArray(DataType.CREATOR);
        editable = in.readInt() == 1;
    }

    public static final Creator<DataType> CREATOR = new Creator<DataType>() {
        @Override
        public DataType createFromParcel(Parcel in) {
            return new DataType(in);
        }

        @Override
        public DataType[] newArray(int size) {
            return new DataType[size];
        }
    };

    public String getDataTypeName() {
        return dataTypeName;
    }

    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public float getMinNormal() {
        return minNormal;
    }

    public float getMaxNormal() {
        return maxNormal;
    }

    public boolean isCompound() {
        return compound;
    }

    public int getNumOfCompound() {
        return numOfCompound;
    }

    public DataType getCompoundAtIndex(int index) {
        return compounds[index];
    }

    public boolean isEditable() {
        return editable;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dataTypeName);
        dest.writeString(measurementUnit);
        dest.writeFloat(minNormal);
        dest.writeFloat(maxNormal);
        dest.writeInt(compound ? 1 : 0);
        dest.writeInt(numOfCompound);
        dest.writeTypedArray(compounds, 0);
        dest.writeInt(editable ? 1 : 0);
    }
}
