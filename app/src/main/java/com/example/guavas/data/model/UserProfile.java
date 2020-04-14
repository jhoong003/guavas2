package com.example.guavas.data.model;

/**
 * This class holds the profile of a user.
 * Attributes: firstname, lastname, gender, date of birth (day,month,year), weight, height and email.
 */
public class UserProfile {
    private String firstName;
    private String lastName;
    private String gender;
    private String dobD;
    private String dobM;
    private String dobY;
    private double weight;
    private double height;
    private String email;

    /**
     * Gets the user's first name.
     *
     * @return the user's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the user's first name.
     *
     * @param firstName the user's first name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the user's last name.
     *
     * @return the user's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the user's last name.
     *
     * @param lastName the user's last name.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the user's gender.
     *
     * @return the user's gender.
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the user's gender.
     *
     * @param gender the user's gender.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Gets the user's weight in kilogram.
     *
     * @return the user's weight in kilogram.
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Sets the user's weight in kilogram.
     *
     * @param weight the user's weight in kilogram.
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Gets the user's height in centimeter.
     *
     * @return the user's height in centimeter.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets the user's height in centimeter.
     *
     * @param height the user's height in centimeter.
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Gets the user's email.
     *
     * @return the user's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email.
     *
     * @param email the user's email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the user's day of birth.
     *
     * @return the user's day of birth.
     */
    public String getDobD() {
        return dobD;
    }

    /**
     * Sets the user's day of birth.
     *
     * @param dobD the user's day of birth.
     */
    public void setDobD(String dobD) {
        this.dobD = dobD;
    }

    /**
     * Gets the user's month of birth.
     *
     * @return the user's month of birth.
     */
    public String getDobM() {
        return dobM;
    }

    /**
     * Sets the user's month of birth.
     *
     * @param dobM the user's month of birth.
     */
    public void setDobM(String dobM) {
        this.dobM = dobM;
    }

    /**
     * Gets the user's year of birth.
     *
     * @return the user's year of birth.
     */
    public String getDobY() {
        return dobY;
    }

    /**
     * Sets the user's year of birth.
     *
     * @param dobY the user's year of birth.
     */
    public void setDobY(String dobY) {
        this.dobY = dobY;
    }

}