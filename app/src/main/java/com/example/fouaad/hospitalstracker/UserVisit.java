package com.example.fouaad.hospitalstracker;

public class UserVisit {

    private String hospital;
    private String userRating;
    private String state;

    public UserVisit(String hospital, String userRating, String state) {
        this.hospital = hospital;
        this.userRating = userRating;
        this.state = state;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
