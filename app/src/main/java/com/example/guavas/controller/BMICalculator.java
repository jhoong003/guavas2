package com.example.guavas.controller;

import androidx.core.util.Pair;

import java.util.ArrayList;

/**
 * This class is used to calculate Body Mass Index.
 */
public class BMICalculator {
    /**
     * Calculates the BMI for the data.
     *
     * @param data the data to calculate.
     * @return the BMI value.
     */
    public static double calculate(ArrayList<Pair<Double, Long>> data) {
        return data.get(1).first / Math.pow(toMeter(data.get(0).first), 2);
    }

    /**
     * Converts the value in centimeter to meter.
     *
     * @param cm the value in centimeter.
     * @return the value in meter.
     */
    private static double toMeter(double cm) {
        return cm / 100;
    }
}
