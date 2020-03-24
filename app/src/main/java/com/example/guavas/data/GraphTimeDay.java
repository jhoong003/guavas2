package com.example.guavas.data;

import java.util.Calendar;

import androidx.annotation.Nullable;

public class GraphTimeDay extends GraphTime{
    private int day;

    public GraphTimeDay(int year, int month, int day){
        super(year, month);
        this.day = day;
    }

    public int getDay() {
        return day;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        GraphTimeDay graphTimeDay = (GraphTimeDay) obj;
        if (super.equals(graphTimeDay) && graphTimeDay.getDay() == getDay()) return true;
        else return false;

    }

    @Override
    public int hashCode() {
        String result = Integer.toString(day) + super.hashCode();
        return Integer.parseInt(result);
    }

    @Override
    public long getXCoordinate() {
        Calendar calendar = super.getCalendar();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar.getTimeInMillis();
    }

    @Override
    public int compareTo(GraphTime graphTime) {
       if (super.compareTo(graphTime) == 0)
            if (getDay() == ((GraphTimeDay)graphTime).getDay())
                return 0;
            else if (getDay() < ((GraphTimeDay)graphTime).getDay())
                return -1;
            else
                return 1;
       else
           return super.compareTo(graphTime);

    }
}
