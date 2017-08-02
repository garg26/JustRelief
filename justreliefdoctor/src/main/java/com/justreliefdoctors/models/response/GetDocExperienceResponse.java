package com.justreliefdoctors.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kartikeya-pc on 6/15/17.
 */

public class GetDocExperienceResponse {
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("From")
    @Expose
    private String from;
    @SerializedName("To")
    @Expose
    private String to;
    @SerializedName("DoctorRole")
    @Expose
    private String doctorRole;
    @SerializedName("CityName")
    @Expose
    private String cityName;
    @SerializedName("ClinicName")
    @Expose
    private String clinicName;

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
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

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }
}
