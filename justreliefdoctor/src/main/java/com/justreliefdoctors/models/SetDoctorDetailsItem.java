package com.justreliefdoctors.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kartikeya-pc on 6/12/17.
 */

public class SetDoctorDetailsItem {
    @SerializedName("DoctorID")
    @Expose
    private String doctorID;
    @SerializedName("DoctorName")
    @Expose
    private String doctorName;
    @SerializedName("DoctorGender")
    @Expose
    private String doctorGender;
    @SerializedName("DoctorCity")
    @Expose
    private String doctorCity;
    @SerializedName("DoctorExperience")
    @Expose
    private String doctorExperience;
    @SerializedName("DoctorAbout")
    @Expose
    private String doctorAbout;
    @SerializedName("DoctorPhone")
    @Expose
    private String doctorPhone;
    @SerializedName("DoctorEmail")
    @Expose
    private String doctorEmail;
    @SerializedName("DoctorTitle")
    @Expose
    private String doctorTitle;
    @SerializedName("Extension")
    @Expose
    private String extension;

    public SetDoctorDetailsItem(String doctorID, String doctorName, String doctorCity, String doctorExperience, String doctorAbout, String doctorPhone, String doctorEmail, String doctorTitle,String gender,String extension) {
        this.doctorID=doctorID;
        this.doctorName=doctorName;
        this.doctorExperience=doctorExperience;
        this.doctorCity=doctorCity;
        this.doctorAbout=doctorAbout;
        this.doctorPhone=doctorPhone;
        this.doctorEmail=doctorEmail;
        this.doctorTitle=doctorTitle;
        this.doctorGender=gender;
        this.extension = extension;
    }


    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorGender() {
        return doctorGender;
    }

    public void setDoctorGender(String doctorGender) {
        this.doctorGender = doctorGender;
    }

    public String getDoctorCity() {
        return doctorCity;
    }

    public void setDoctorCity(String doctorCity) {
        this.doctorCity = doctorCity;
    }

    public String getDoctorExperience() {
        return doctorExperience;
    }

    public void setDoctorExperience(String doctorExperience) {
        this.doctorExperience = doctorExperience;
    }

    public String getDoctorAbout() {
        return doctorAbout;
    }

    public void setDoctorAbout(String doctorAbout) {
        this.doctorAbout = doctorAbout;
    }

    public String getDoctorPhone() {
        return doctorPhone;
    }

    public void setDoctorPhone(String doctorPhone) {
        this.doctorPhone = doctorPhone;
    }

    public String getDoctorEmail() {
        return doctorEmail;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }

    public String getDoctorTitle() {
        return doctorTitle;
    }

    public void setDoctorTitle(String doctorTitle) {
        this.doctorTitle = doctorTitle;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }


}
