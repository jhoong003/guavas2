package com.example.guavas.data.entity;

import java.util.Calendar;

import androidx.annotation.Nullable;

/**
 * This class represents a day.
 */
public class GraphTimeDay extends GraphTime{
    private int day;

    /**
     * The constructor.
     *
     * @param year  the year to represent.
     * @param month the month to represent.
     * @param day the day to represent.
     */
    public GraphTimeDay(int year, int month, int day){
        super(year, month);
        this.day = day;
    }

    /**
     * Gets the day.
     * @return the day.
     */
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
