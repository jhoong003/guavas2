package com.example.guavas.data.entity;

import java.util.Comparator;

/**
 * This class holds a hospital's information. Information includes the name, address, description, phone number, and image.
 */
public class Hospital {
    private String hospitalName;
    private String hospitalAddress;
    private String hospitalDescription;
    private String hospitalNumber;
    private int imgURL;

    /**
     * The constructor.
     *
     * @param hospitalName        the hospital name.
     * @param hospitalAddress     the address of the hospital.
     * @param hospitalNumber      the phone number of the hospital.
     * @param HospitalDescription the description of the hospital.
     * @param imgURL              the image of the hospital.
     */
    public Hospital(String hospitalName, String hospitalAddress, String hospitalNumber, String HospitalDescription, int imgURL) {
        this.hospitalName = hospitalName;
        this.hospitalAddress = hospitalAddress;
        this.hospitalNumber = hospitalNumber;
        this.hospitalDescription = HospitalDescription;
        this.imgURL = imgURL;
    }

    /**
     * Gets the image.
     *
     * @return the image.
     */
    public int getImgURL() {
        return imgURL;
    }

    /**
     * Gets the name of the hospital.
     *
     * @return the name of the hospital.
     */
    public String getName() {
        return hospitalName;
    }

    /**
     * Sets the name of the hospital.
     *
     * @param hospitalName the name of the hospital.
     */
    public void setName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    /**
     * Gets the address of the hospital.
     *
     * @return the address of the hospital.
     */
    public String getAddress() {
        return hospitalAddress;
    }

    /**
     * Gets the description of the hospital.
     *
     * @return the description of the hospital.
     */
    public String getDescription() {
        return hospitalDescription;
    }

    /**
     * Sets the description of the hospital.
     *
     * @param hospitalDescription the description of the hospital.
     */
    public void setDescription(String hospitalDescription) {
        this.hospitalDescription = hospitalDescription;
    }

    /**
     * Gets the hospital's phone number.
     *
     * @return the hospital's phone number.
     */
    public String getNumber() {
        return hospitalNumber;
    }

    /**
     * Sets the hospital's phone number.
     *
     * @param hospitalNumber the hospital's phone number.
     */
    public void setNumber(String hospitalNumber) {
        this.hospitalNumber = hospitalNumber;
    }

    /**
     * A comparator to compare hospitals.
     */
    public static Comparator<Hospital> nameComparator = new Comparator<Hospital>() {
        public int compare(Hospital h1, Hospital h2) {
            return (int) (h1.getName().compareTo(h2.getName()));
        }
    };
}
