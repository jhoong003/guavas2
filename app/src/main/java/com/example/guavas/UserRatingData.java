package com.example.guavas;

public class UserRatingData {

    private String userID;
    private double userRating;

    public UserRatingData(String userID, double userRating) {
        this.userID = userID;
        this.userRating = userRating;
    }

    public UserRatingData() {

    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public double getUserRating() {
        return userRating;
    }

    public void setUserRating(double userRating) {
        this.userRating = userRating;
    }
}