package com.example.guavas.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This class holds the properties of the medical data type.
 */
public class DataType implements Parcelable {

    private String dataTypeName;
    private String measurementUnit;
    private float minNormal;
    private float maxNormal;
    private boolean compound = false;
    private int numOfCompound;
    private DataType[] compounds;
    private boolean editable = true;

    /**
     * Constructor where the name is the only concern.
     *
     * @param name the name of the medical data type.
     */
    public DataType(String name) {
        dataTypeName = name;
    }

    /**
     * Constructor with the range of healthy value.
     *
     * @param name the name of the medical data type.
     * @param unit the unit of measurement used
     * @param min  the minimal value that is considered healthy.
     * @param max  the maximal value that is considered healthy.
     */
    public DataType(String name, String unit, float min, float max) {
        dataTypeName = name;
        measurementUnit = unit;
        minNormal = min;
        maxNormal = max;
    }

    /**
     * Constructor to set the editable of the data.
     *
     * @param name       the name of the medical data type.
     * @param unit       the unit of measurement used
     * @param min        the minimal value that is considered healthy.
     * @param max        the maximal value that is considered healthy.
     * @param isEditable <code>true</code> if the user can add data.
     */
    public DataType(String name, String unit, float min, float max, boolean isEditable) {
        dataTypeName = name;
        measurementUnit = unit;
        minNormal = min;
        maxNormal = max;
        editable = isEditable;
    }

    /**
     * Constructor for compound data type: a medical data type that uses other data types. Example is BMI.
     *
     * @param name          the name of the medical data type.
     * @param unit          the unit of measurement used
     * @param min           the minimal value that is considered healthy.
     * @param max           the maximal value that is considered healthy.
     * @param compound      <code>true</code> if it's a compound data type.
     * @param compoundCount the number of data type it depends on.
     * @param compounds     the data types it depends on.
     */
    public DataType(String name, String unit, float min, float max, boolean compound, int compoundCount, DataType[] compounds) {
        dataTypeName = name;
        measurementUnit = unit;
        minNormal = min;
        maxNormal = max;
        this.compound = compound;
        numOfCompound = compoundCount;
        this.compounds = compounds;
    }

    /**
     * Makes the data type passable between fragments.
     *
     * @param in the parcel to pass.
     */
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

    /**
     * Gets the medical data type name.
     *
     * @return the medical data type name.
     */
    public String getDataTypeName() {
        return dataTypeName;
    }

    /**
     * Gets the measurement unit.
     *
     * @return the measurement unit.
     */
    public String getMeasurementUnit() {
        return measurementUnit;
    }

    /**
     * Gets the minimal value that is considered healthy.
     *
     * @return the minimal value that is considered healthy.
     */
    public float getMinNormal() {
        return minNormal;
    }

    /**
     * Gets the maximal value that is considered healthy.
     *
     * @return the maximal value that is considered healthy.
     */
    public float getMaxNormal() {
        return maxNormal;
    }

    /**
     * Checks if the data type is a compound.
     *
     * @return <code>true</code> if the data type is a compound.
     */
    public boolean isCompound() {
        return compound;
    }

    /**
     * Gets the number of data type it depends on.
     *
     * @return the number of data type it depends on.
     */
    public int getNumOfCompound() {
        return numOfCompound;
    }

    /**
     * Gets the data type it depends on at the given index.
     *
     * @param index the index.
     * @return the data type it depends on at the given index.
     */
    public DataType getCompoundAtIndex(int index) {
        return compounds[index];
    }

    /**
     * Checks if the user can edit the data type.
     *
     * @return <code>true</code> if the user can edit the data type.
     */
    public boolean isEditable() {
        return editable;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Makes this class parcelable.
     *
     * @param dest  the parcel destionation.
     * @param flags the flag.
     */
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
