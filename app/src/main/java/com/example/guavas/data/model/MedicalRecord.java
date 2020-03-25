package com.example.guavas.data.model;

public class MedicalRecord {
    private long time;
    private double measurement;

    public MedicalRecord(){}

    public MedicalRecord(long time, double measurement){
        this.time = time;
        this.measurement = measurement;
    }

    public double getMeasurement() {
        return measurement;
    }

    public void setMeasurement(double measurement) {
        this.measurement = measurement;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
