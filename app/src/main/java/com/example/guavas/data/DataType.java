package com.example.guavas.data;

import android.os.Parcel;
import android.os.Parcelable;

public class DataType implements Parcelable {
    private String dataTypeName;
    private String measurementUnit;
    private float minNormal;
    private float maxNormal;

    public DataType(String name, String unit, float min, float max){
        dataTypeName = name;
        measurementUnit = unit;
        minNormal = min;
        maxNormal = max;
    }

    protected DataType(Parcel in) {
        dataTypeName = in.readString();
        measurementUnit = in.readString();
        minNormal = in.readFloat();
        maxNormal = in.readFloat();
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

    public void setDataTypeName(String dataTypeName) {
        this.dataTypeName = dataTypeName;
    }

    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    public float getMinNormal() {
        return minNormal;
    }

    public void setMinNormal(float minNormal) {
        this.minNormal = minNormal;
    }

    public float getMaxNormal() {
        return maxNormal;
    }

    public void setMaxNormal(float maxNormal) {
        this.maxNormal = maxNormal;
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
    }
}
