package com.example.guavas.data.entity;

/**
 * This class holds the number of data and its total in for the same month.
 */
public class GraphData {
    private long count;
    private double total;

    /**
     * The constructor sets up the class.
     */
    public GraphData() {
        count = 0;
        total = 0;
    }

    /**
     * Increments the count.
     *
     * @param addValue the value to add.
     */
    public void addCount(long addValue) {
        count += addValue;
    }

    /**
     * Adds the total value.
     *
     * @param addValue the value to add.
     */
    public void addTotal(double addValue) {
        total += addValue;
    }

    /**
     * Gets the average of the value.
     *
     * @return the average of the value.
     */
    public double getAverage() {
        return total / count;
    }

}
