package com.example.guavas.controller;

import com.example.guavas.data.GraphData;
import com.example.guavas.data.GraphTimeWeek;
import com.example.guavas.data.model.MedicalRecord;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

public class WeeklyDataProcessor implements DataProcessor {
    @Override
    public ArrayList<Entry> processData(ArrayList<MedicalRecord> data) {
        ArrayList<Entry> entries = new ArrayList<>();
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        HashMap<GraphTimeWeek, GraphData> dataHashMap = new HashMap<>();

        for (MedicalRecord record : data){
            calendar.setTimeInMillis(record.getTime());
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            GraphTimeWeek graphTimeWeek = new GraphTimeWeek(year, month, day);

            if (!dataHashMap.containsKey(graphTimeWeek)) dataHashMap.put(graphTimeWeek, new GraphData());
            dataHashMap.get(graphTimeWeek).addCount(1);
            dataHashMap.get(graphTimeWeek).addTotal(record.getMeasurement());
        }

        TreeMap<GraphTimeWeek, GraphData> sortedData = new TreeMap<>(dataHashMap);

        for (Map.Entry<GraphTimeWeek, GraphData> e: sortedData.entrySet()){
            entries.add(new Entry(e.getKey().getXCoordinate(), (float) e.getValue().getAverage()));
        }

        return entries;
    }

    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        long intValue = (long) value;
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTimeInMillis(intValue);
        DateFormat formatter = DateFormat.getDateInstance(DateFormat.MEDIUM);
        return formatter.format(calendar.getTime());
    }

    @Override
    public float getGranularity() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        long millisNow = calendar.getTimeInMillis();
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        long millisLater = calendar.getTimeInMillis();
        return millisLater - millisNow;
    }
}
