package com.example.guavas.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class manages the questions and answers in FAQ Page
 * Questions and answers are stored as Hashmaps with questions as key and answers as values
 */
public class FAQDataManager {

    /**
     * Retrieves the FAQ questions and answers.
     *
     * @return the FAQ questions and answers.
     */
    public static HashMap<String, List<String>> getFAQData() {
        final HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> ansq1 = new ArrayList<String>();
        ansq1.add("Meds is a mini doctor that is available for you. Meds provides updated hospital/disease information and offers the service for self-diagnosis through symptoms and relevant medical data. This would be done using our Machine Learning algorithms. Meds also monitors your medical data trends so that you can revise your lifestyle.");
        List<String> ansq2 = new ArrayList<String>();
        ansq2.add("You can find the information by clicking on the magnifying glass (the 3rd) icon at the navigation bar. You may choose to search for hospital or disease information. Simply input the name of the hospital/disease and select it from the list to view more details.");
        List<String> ansq3 = new ArrayList<String>();
        ansq3.add("YES! One of the main functions of Meds is to act as a self-diagnosing tool for you. Two modes of diagnosis are available in Meds: common disease diagnosis and chronic disease common or chronic disease to diagnose yourself.After selecting, Meds will require input of necessary symptoms or medical data for the relevant diagnosis.");
        List<String> ansq4 = new ArrayList<String>();
        ansq4.add("Meds enables you to sign in using either a Singaporean phone number or a valid Google account. Upon signing in, all your inputted information will be saved in our databases. A Meds account allows you to store their medical data as well as self-diagnosis history so as to keep track of your health status. Meds also monitors the trends in your medical data and provides relevant suggestions.");

        expandableListDetail.put("1. What is Meds app?", ansq1);
        expandableListDetail.put("2. Where can I get hospital/disease information?", ansq2);
        expandableListDetail.put("3. Can I diagnose myself using Meds app?", ansq3);
        expandableListDetail.put("4. Will my data be saved?", ansq4);

        return expandableListDetail;
    }


}