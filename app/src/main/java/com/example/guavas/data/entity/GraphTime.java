package com.example.guavas.data.entity;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import androidx.annotation.Nullable;

/**
 * This class represents a month and a year.
 */
public class GraphTime implements Comparable<GraphTime> {
    private int year;
    private int month;

    /**
     * The constructor.
     *
     * @param year  the year to represent.
     * @param month the month to represent.
     */
    public GraphTime(int year, int month) {
        this.year = year;
        this.month = month;
    }

    /**
     * Check for equality.
     *
     * @param obj the comparing object.
     * @return <code>true</code> if it is equal.
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        GraphTime graphTime = (GraphTime) obj;
        if (getYear() == graphTime.getYear() && getMonth() == graphTime.getMonth()) return true;
        else return false;
    }

    /**
     * Makes each month unique through the hash code.
     *
     * @return the hash code.
     */
    @Override
    public int hashCode() {
        String result = Integer.toString(month) + year;
        return Integer.parseInt(result);
    }

    /**
     * Compare a month to another.
     *
     * @param graphTime the time to compare.
     * @return 0 if same month.
     */
    public int compareTo(GraphTime graphTime) {
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

    /**
     * Gets the X coordinate.
     *
     * @return the X coordinate.
     */
    public long getXCoordinate() {
        Calendar calendar = getCalendar();
        return calendar.getTimeInMillis();
    }

    /**
     * Gets the calendar set at the first day of the first month.
     *
     * @return the calendar set at the first day of the first month.
     */
    protected Calendar getCalendar() {
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

    /**
     * Gets the month.
     *
     * @return the month.
     */
    public int getMonth() {
        return month;
    }

    /**
     * Gets the year.
     *
     * @return the year.
     */
    public int getYear() {
        return year;
    }
}
