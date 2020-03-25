package com.example.guavas.controller;

import com.example.guavas.data.GraphData;
import com.example.guavas.data.model.MedicalRecord;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

public class YearlyDataProcessor implements DataProcessor {
    @Override
    public ArrayList<Entry> processData(ArrayList<MedicalRecord> data) {
        ArrayList<Entry> entries = new ArrayList<>();
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        HashMap<Integer, GraphData> dataHashMap = new HashMap<>();

        for (MedicalRecord record : data) {
            calendar.setTimeInMillis(record.getTime());
            int year = calendar.get(Calendar.YEAR);

            if (!dataHashMap.containsKey(year))
                dataHashMap.put(year, new GraphData());
            dataHashMap.get(year).addCount(1);
            dataHashMap.get(year).addTotal(record.getMeasurement());
        }

        TreeMap<Integer, GraphData> sortedData = new TreeMap<>(dataHashMap);

        for (Map.Entry<Integer, GraphData> e: sortedData.entrySet()){
            entries.add(new Entry(e.getKey(), (float) e.getValue().getAverage()));
        }

        return entries;
    }

    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        return Integer.toString((int)value);
    }

    @Override
    public float getGranularity() {
        return 1f;
    }
}
