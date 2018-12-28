package com.example.fouaad.hospitalstracker.helper;

public class Configuration {

    public static final String ip="192.168.1.40";
    public static final String LOGIN_URL="http://"+ip+"/hospitalsTracker/login.php";
    public static final String LOGINADMIN_URL="http://"+ip+"/hospitalsTracker/loginAdmin.php";
    public static final String ADDHOSPITAL_URL="http://"+ip+"/hospitalsTracker/addHospital.php";
    public static final String ADDUSER_URL="http://"+ip+"/hospitalsTracker/addUser.php";
    public static final String DEPARTMENTS_URL="http://"+ip+"/hospitalsTracker/getAllDepartments.php";
    public static final String CHECK_HOSPITAL_EXIST_URL="http://"+ip+"/hospitalsTracker/checkHospitalExistance.php";
    public static final String VISIT_HOSPITAL_URL="http://"+ip+"/hospitalsTracker/visitHospital.php";
    public static final String VISITS_URL = "http://"+ip+"/hospitalsTracker/getAllVisits.php";
    public static final String CHECKVISITS_URL = "http://"+ip+"/hospitalsTracker/checkVisits.php";
    public static final String RATE_URL = "http://"+ip+"/hospitalsTracker/rateVisit.php";
    public static final String UPDATEOVERALLRATING_URL = "http://"+ip+"/hospitalsTracker/updateRate.php";
    public static final String FINISHVISIT_URL = "http://"+ip+"/hospitalsTracker/finishVisit.php";

}
