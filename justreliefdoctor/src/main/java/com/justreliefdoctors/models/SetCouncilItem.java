package com.justreliefdoctors.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kartikeya-pc on 6/15/17.
 */

public class SetCouncilItem {
    @SerializedName("DoctorID")
    @Expose
    private String doctorID;
    @SerializedName("CouncilNumber")
    @Expose
    private String councilNumber;
    @SerializedName("CouncilName")
    @Expose
    private String councilName;
    @SerializedName("CouncilYear")
    @Expose
    private Integer councilYear;

    public SetCouncilItem(String doctorID, String concilNumber, String concilName, String year) {

        this.doctorID=doctorID;
        this.councilNumber =concilNumber;
        this.councilName =concilName;
        councilYear = Integer.parseInt(year);

    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String getCouncilNumber() {
        return councilNumber;
    }

    public void setCouncilNumber(String councilNumber) {
        this.councilNumber = councilNumber;
    }

    public String getCouncilName() {
        return councilName;
    }

    public void setCouncilName(String councilName) {
        this.councilName = councilName;
    }

    public Integer getCouncilYear() {
        return councilYear;
    }

    public void setCouncilYear(Integer councilYear) {
        this.councilYear = councilYear;
    }
}
