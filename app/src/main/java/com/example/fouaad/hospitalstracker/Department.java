package com.example.fouaad.hospitalstracker;

import java.io.Serializable;

public class Department implements Serializable {

    private String departmentName;
    private String doctorName;
    private String doctorStartTime;
    private String doctorEndTime;
    private String doctorPriceRange;

    public Department(String departmentName, String doctorName, String doctorStartTime, String doctorEndTime, String doctorPriceRange) {
        this.departmentName = departmentName;
        this.doctorName = doctorName;
        this.doctorStartTime = doctorStartTime;
        this.doctorEndTime = doctorEndTime;
        this.doctorPriceRange = doctorPriceRange;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorStartTime() {
        return doctorStartTime;
    }

    public void setDoctorStartTime(String doctorStartTime) {
        this.doctorStartTime = doctorStartTime;
    }

    public String getDoctorEndTime() {
        return doctorEndTime;
    }

    public void setDoctorEndTime(String doctorEndTime) {
        this.doctorEndTime = doctorEndTime;
    }

    public String getDoctorPriceRange() {
        return doctorPriceRange;
    }

    public void setDoctorPriceRange(String doctorPriceRange) {
        this.doctorPriceRange = doctorPriceRange;
    }
}
