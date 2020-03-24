package com.example.guavas.data;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import androidx.annotation.Nullable;

public class GraphTimeWeek extends GraphTime {
    private int weekNumber;

    public GraphTimeWeek(int year, int month, int day){
        super(year, month);
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        weekNumber = calendar.get(Calendar.WEEK_OF_MONTH);
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        GraphTimeWeek graphTimeWeek = (GraphTimeWeek)obj;
        if (super.equals(graphTimeWeek) && graphTimeWeek.getWeekNumber() == getWeekNumber()) return true;
        else return false;
    }

    @Override
    public int hashCode() {
        String result = Integer.toString(weekNumber) + super.hashCode();
        return Integer.parseInt(result);
    }

    @Override
    public int compareTo(GraphTime graphTime) {
        if (super.compareTo(graphTime) == 0)
            if (getWeekNumber() == ((GraphTimeWeek)graphTime).getWeekNumber())
                return 0;
            else if (getWeekNumber() < ((GraphTimeWeek)graphTime).getWeekNumber())
                return -1;
            else
                return 1;
        else return super.compareTo(graphTime);
    }

    @Override
    public long getXCoordinate() {
        Calendar calendar = super.getCalendar();
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        calendar.set(Calendar.WEEK_OF_MONTH, weekNumber);
        return calendar.getTimeInMillis();
    }
}
