package com.example.guavas.data.model;

/**
 * This class holds the user's rating and the user's ID that gives the rating.
 */
public class UserRatingData {

    private String userID;
    private double userRating;

    /**
     * Constructs the object.
     * @param userID the user ID that gives the rating.
     * @param userRating the rating given by the user.
     */
    public UserRatingData(String userID, double userRating) {
        this.userID = userID;
        this.userRating = userRating;
    }

    /**
     * Required empty constructor for firebase.
     */
    public UserRatingData() {

    }

    /**
     * Gets the user ID that gives the rating.
     * @return the user ID that gives the rating.
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Sets the user ID that gives the rating.
     * @param userID the user ID that gives the rating.
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * Gets the rating given by the user.
     * @return the rating given by the user.
     */
    public double getUserRating() {
        return userRating;
    }

    /**
     * Sets the rating given by the user.
     * @param userRating the rating given by the user.
     */
    public void setUserRating(double userRating) {
        this.userRating = userRating;
    }
}