package com.justreliefdoctors.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kartikeya-pc on 6/12/17.
 */

public class ContactDoctorItem {
    @SerializedName("DoctorID")
    @Expose
    private String doctorID;
    @SerializedName("Qualification")
    @Expose
    private Integer qualification;
    @SerializedName("College")
    @Expose
    private Integer college;
    @SerializedName("Year")
    @Expose
    private Integer year;

    public ContactDoctorItem(String doctorID, Integer qualification, Integer college, Integer year) {
        this.doctorID = doctorID;
        this.qualification=qualification;
        this.college = college;
        this.year = year;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public Integer getQualification() {
        return qualification;
    }

    public void setQualification(Integer qualification) {
        this.qualification = qualification;
    }

    public Integer getCollege() {
        return college;
    }

    public void setCollege(Integer college) {
        this.college = college;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
