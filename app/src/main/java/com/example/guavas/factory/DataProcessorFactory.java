package com.example.guavas.factory;

import com.example.guavas.controller.DailyDataProcessor;
import com.example.guavas.controller.DataProcessor;
import com.example.guavas.controller.MonthlyDataProcessor;
import com.example.guavas.controller.WeeklyDataProcessor;
import com.example.guavas.controller.YearlyDataProcessor;

/**
 * This class is a factory class for instantiating DataProcessor.
 */
public class DataProcessorFactory {

    /**
     * Creates a new <code>DataProcessor</code> object depending on the chosen option.
     *
     * @param option the option selected.
     * @return the corresponding <code>DataProcessor</code> object.
     */
    public static DataProcessor getDataProcessor(int option) {
        DataProcessor dataProcessor = null;

        switch (option) {
            case 0:
                dataProcessor = new DailyDataProcessor();
                break;
            case 1:
                dataProcessor = new WeeklyDataProcessor();
                break;
            case 2:
                dataProcessor = new MonthlyDataProcessor();
                break;
            default:
                dataProcessor = new YearlyDataProcessor();
        }

        return dataProcessor;
    }
}
