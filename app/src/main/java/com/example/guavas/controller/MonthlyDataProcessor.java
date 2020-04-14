package com.example.guavas.controller;

import com.example.guavas.data.entity.GraphData;
import com.example.guavas.data.entity.GraphTime;
import com.example.guavas.data.model.MedicalRecord;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

/**
 * This class groups the data based on the month. The data is then displayed on the graph.
 */
public class MonthlyDataProcessor implements DataProcessor{

    /**
     * Processes the data by taking the average of all data in the same month.
     *
     * @param data the raw data to be processed.
     * @return the processed data.
     */
    @Override
    public ArrayList<Entry> processData(ArrayList<MedicalRecord> data) {
        ArrayList<Entry> entries = new ArrayList<>();
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        HashMap<GraphTime, GraphData> dataHashMap = new HashMap<>();

        for (MedicalRecord record : data){
            calendar.setTimeInMillis(record.getTime());
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            GraphTime graphTime = new GraphTime(year, month);

            if (!dataHashMap.containsKey(graphTime)) dataHashMap.put(graphTime, new GraphData());
            dataHashMap.get(graphTime).addCount(1);
            dataHashMap.get(graphTime).addTotal(record.getMeasurement());
        }

        TreeMap<GraphTime, GraphData> sortedData = new TreeMap<>(dataHashMap);

        for (Map.Entry<GraphTime, GraphData> e: sortedData.entrySet()){
            System.out.println(e.getKey().getMonth() + " " + e.getValue().getAverage()); //TODO: remove this debug line
            entries.add(new Entry(e.getKey().getXCoordinate(), (float) e.getValue().getAverage()));
        }

        return entries;
    }

    /**
     * Creates the corresponding label for the x-axis. For example, the label is Mar 2020.
     *
     * @param value the value of the data.
     * @param axis  the x-axis.
     * @return the label for the corresponding data.
     */
    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        SimpleDateFormat format = new SimpleDateFormat("MMM yyyy");
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTimeInMillis((long)value);
        return format.format(calendar.getTime());
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
        calendar.add(Calendar.MONTH, 1);
        long millisLater = calendar.getTimeInMillis();
        return millisLater - millisNow;
    }
}
