package com.example.guavas.data.model;

/**
 * This class holds the data of a medical measurement and its time of measurement.
 * The time of measurement is in milliseconds from Epoch.
 */
public class MedicalRecord {
    private long time;
    private double measurement;

    /**
     * Required empty constructor for firebase.
     */
    public MedicalRecord(){}

    /**
     * Constructs a new medical record.
     * @param time the time since Epoch in milliseconds.
     * @param measurement the value of the measurement.
     */
    public MedicalRecord(long time, double measurement){
        this.time = time;
        this.measurement = measurement;
    }

    /**
     * Gets the measurement value.
     * @return the measurement value.
     */
    public double getMeasurement() {
        return measurement;
    }

    /**
     * Sets the measurement value.
     * @param measurement the measurement value.
     */
    public void setMeasurement(double measurement) {
        this.measurement = measurement;
    }

    /**
     * Gets the time of measurement in milliseconds since Epoch.
     * @return the time of measurement in milliseconds since Epoch.
     */
    public long getTime() {
        return time;
    }

    /**
     * Sets the time of measurement in milliseconds since Epoch.
     * @param time the time of measurement in milliseconds since Epoch.
     */
    public void setTime(long time) {
        this.time = time;
    }
}
