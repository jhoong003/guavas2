package com.example.guavas.controller;

import com.example.guavas.data.GraphData;
import com.example.guavas.data.GraphTimeDay;
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

public class DailyDataProcessor implements DataProcessor {
    @Override
    public ArrayList<Entry> processData(ArrayList<MedicalRecord> data) {

        ArrayList<Entry> entries = new ArrayList<>();
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        HashMap<GraphTimeDay, GraphData> dataHashMap = new HashMap<>();

        for (MedicalRecord record : data){
            calendar.setTimeInMillis(record.getTime());
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            GraphTimeDay graphTimeDay = new GraphTimeDay(year, month, day);
            if (!dataHashMap.containsKey(graphTimeDay)) dataHashMap.put(graphTimeDay, new GraphData());
            dataHashMap.get(graphTimeDay).addCount(1);
            dataHashMap.get(graphTimeDay).addTotal(record.getMeasurement());
        }

        TreeMap<GraphTimeDay, GraphData> sortedData = new TreeMap<>(dataHashMap);

        for (Map.Entry<GraphTimeDay, GraphData> e: sortedData.entrySet()){
            entries.add(new Entry(e.getKey().getXCoordinate(), (float) e.getValue().getAverage()));
        }

        return entries;
    }

    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        long intValue = (long) value;
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTimeInMillis(intValue);
        DateFormat formatter = DateFormat.getDateInstance(DateFormat.DEFAULT);
        return formatter.format(calendar.getTime());
    }

    @Override
    public float getGranularity() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        long millisNow = calendar.getTimeInMillis();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        long millisLater = calendar.getTimeInMillis();
        return millisLater - millisNow;
    }
}
