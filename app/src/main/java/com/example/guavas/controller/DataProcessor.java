package com.example.guavas.controller;

import com.example.guavas.data.model.MedicalRecord;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

public interface DataProcessor {
    public ArrayList<Entry> processData(ArrayList<MedicalRecord> data);
    public String getAxisLabel(float value, AxisBase axis);
    public float getGranularity();
}
