package com.example.guavas.controller;

import com.example.guavas.data.entity.GraphData;
import com.example.guavas.data.entity.GraphTimeDay;
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

/**
 * This class groups the data based on the day. The data is then displayed on the graph.
 */
public class DailyDataProcessor implements DataProcessor {
    /**
     * Processes the data by taking the average of all data in the same day.
     *
     * @param data the raw data to be processed.
     * @return the processed data.
     */
    @Override
    public ArrayList<Entry> processData(ArrayList<MedicalRecord> data) {

        ArrayList<Entry> entries = new ArrayList<>();
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        HashMap<GraphTimeDay, GraphData> dataHashMap = new HashMap<>();

        for (MedicalRecord record : data) {
            calendar.setTimeInMillis(record.getTime());
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            GraphTimeDay graphTimeDay = new GraphTimeDay(year, month, day);
            if (!dataHashMap.containsKey(graphTimeDay))
                dataHashMap.put(graphTimeDay, new GraphData());
            dataHashMap.get(graphTimeDay).addCount(1);
            dataHashMap.get(graphTimeDay).addTotal(record.getMeasurement());
        }

        TreeMap<GraphTimeDay, GraphData> sortedData = new TreeMap<>(dataHashMap);

        for (Map.Entry<GraphTimeDay, GraphData> e : sortedData.entrySet()) {
            entries.add(new Entry(e.getKey().getXCoordinate(), (float) e.getValue().getAverage()));
        }

        return entries;
    }

    /**
     * Creates the corresponding label for the x-axis. For example, the label is 26 Mar 2020.
     *
     * @param value the value of the data.
     * @param axis  the x-axis.
     * @return the label for the corresponding data.
     */
    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        long intValue = (long) value;
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTimeInMillis(intValue);
        DateFormat formatter = DateFormat.getDateInstance(DateFormat.DEFAULT);
        return formatter.format(calendar.getTime());
    }

    /**
     * Returns the granularity between values.
     *
     * @return the granularity between values.
     */
    @Override
    public float getGranularity() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        long millisNow = calendar.getTimeInMillis();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        long millisLater = calendar.getTimeInMillis();
        return millisLater - millisNow;
    }
}
