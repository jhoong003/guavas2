package com.example.guavas.data;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import androidx.annotation.Nullable;

public class GraphTime implements Comparable<GraphTime>{
    private int year;
    private int month;


    public GraphTime(int year, int month){
        this.year = year;
        this.month = month;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        GraphTime graphTime = (GraphTime) obj;
        if (getYear() == graphTime.getYear() && getMonth() == graphTime.getMonth()) return true;
        else return false;
    }

    @Override
    public int hashCode() {
        String result = Integer.toString(month) + year;
        return Integer.parseInt(result);
    }

    public int compareTo(GraphTime graphTime){
        if (graphTime.getYear() == getYear())
            if (graphTime.getMonth() == getMonth())
                return 0;
            else if (getMonth() < graphTime.getMonth())
                return -1;
            else
                return 1;
        else if (getYear() < graphTime.getYear())
            return -1;
        else
            return 1;
    }

    public long getXCoordinate(){
        Calendar calendar = getCalendar();
        return calendar.getTimeInMillis();
    }

    protected Calendar getCalendar(){
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.MINUTE, 1);
        calendar.set(Calendar.SECOND, 1);
        calendar.set(Calendar.MILLISECOND, 1);
        return calendar;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
}
