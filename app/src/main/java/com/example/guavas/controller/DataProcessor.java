package com.example.guavas.controller;

import com.example.guavas.data.model.MedicalRecord;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

/**
 * This interface is used to process the data fetched from the database so that it can be inserted to the graph.
 */
public interface DataProcessor {
    public ArrayList<Entry> processData(ArrayList<MedicalRecord> data);
    public String getAxisLabel(float value, AxisBase axis);
    public float getGranularity();
}
