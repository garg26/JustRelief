package com.justreliefdoctors.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kartikeya-pc on 6/14/17.
 */

public class SetDoctorExperienceItem {
    @SerializedName("DoctorID")
    @Expose
    private String doctorID;
    @SerializedName("From")
    @Expose
    private String from;
    @SerializedName("To")
    @Expose
    private String to;
    @SerializedName("DoctorRole")
    @Expose
    private String doctorRole;
    @SerializedName("ClinicName")
    @Expose
    private String clinicName;
    @SerializedName("CityID")
    @Expose
    private Integer cityID;

    public SetDoctorExperienceItem(String doctorID, String from, String to, String doctorRole, String clinicName, String cityID) {
        this.doctorID = doctorID;
        this.from = from;
        this.to = to;
        this.doctorRole = doctorRole;
        this.clinicName = clinicName;
        this.cityID = Integer.parseInt(cityID);
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDoctorRole() {
        return doctorRole;
    }

    public void setDoctorRole(String doctorRole) {
        this.doctorRole = doctorRole;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public Integer getCityID() {
        return cityID;
    }

    public void setCityID(Integer cityID) {
        this.cityID = cityID;
    }

}
