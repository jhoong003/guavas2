package com.example.guavas.controller;

import androidx.core.util.Pair;

import java.util.ArrayList;

public class BMICalculator {
    public static double calculate(ArrayList<Pair<Double, Long>> data){
        return data.get(1).first / Math.pow(toMeter(data.get(0).first), 2);
    }

    private static double toMeter(double cm){
        return cm/100;
    }
}
