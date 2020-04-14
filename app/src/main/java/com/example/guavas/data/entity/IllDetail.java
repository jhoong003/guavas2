package com.example.guavas.data.entity;

import java.util.Comparator;

/**
 * This IllDetail class holds the information of the diseases. Information includes the disease name, the description
 * of the disease, and the prevention of the disease.
 */
public class IllDetail {
    private String illName;
    private String description;
    private String prevention;

    /**
     * The constructor for the IllDetail class.
     * @param name the name of the disease.
     * @param Desc the description of the disease.
     * @param prevent the prevention of the disease.
     */
    public IllDetail(String name, String Desc, String prevent) {
        illName = name;
        description = Desc;
        prevention = prevent;
    }

    /**
     * Returns the name of the disease.
     * @return the disease name
     */
    public String getName() {
        return illName;
    }

    /**
     * Returns the description of the disease.
     * @return the disease description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the prevention of the disease.
     * @return the disease prevention
     */
    public String getPrevention() {
        return prevention;
    }

    /**
     * A comparator used for sorting.
     */
    public static Comparator<IllDetail> nameComparator = new Comparator<IllDetail>() {
        public int compare(IllDetail h1, IllDetail h2) {
            return (int) (h1.getName().compareTo(h2.getName()));
        }
    };


}
